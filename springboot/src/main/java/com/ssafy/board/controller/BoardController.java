package com.ssafy.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.FileInfoDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.user.model.dto.UserDto;
import com.ssafy.util.PageNavigation;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/board")
public class BoardController extends HttpServlet{
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.imgPath}")
    private String imageUploadPath;

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	@ApiOperation(value = "게시판 인기글", notes = "인기 글의 정보를 반환한다", response = Map.class)
	@ResponseBody
	@GetMapping("/top")
	public ResponseEntity<Map<String, Object>> topList() throws Exception {
		logger.info("topList - 호출 : ");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
		List<BoardDto> boards = boardService.topList();
		logger.info("BoardDto : " + boards);
		resultMap.put("boards", boards);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("정보조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보와 검색 키워드를 반환한다", response = Map.class)
	@ResponseBody
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("list parameter pgno : {}", map.get("pgno"));
		List<BoardDto> list = boardService.listArticle(map);
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("articles", list);
		output.put("pageNavigation", pageNavigation);
		output.put("pgno", map.get("pgno"));
		output.put("key", map.get("key"));
		output.put("word", map.get("word"));
		return new ResponseEntity<Map<String,Object>>(output, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "좋아요 여부 조회", notes = "특정 사용자가 좋아요를 눌렀는지 아닌지를 판단한다", response = String.class)
	@ResponseBody
	@GetMapping("/likes/{articleno}")
//	{articleno: 게시글번호, userId : 유저아이디, 카테고리는 여기서 1로 고정 }
	public ResponseEntity<String> likeCheck(@PathVariable("articleno") int articleNo, @RequestParam String userId)
			throws Exception {
		logger.debug("likes articleNo : {}", articleNo);
		logger.debug("likes userId: {}", userId);
		boolean isContain = boardService.likeCheck(articleNo, userId);
		logger.debug("likes isContain: {}", isContain);
		if (isContain) {
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("false", HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "좋아요 추가", notes = "특정 사용자의 좋아요 요청을 받아 DB에 반영한다", response = String.class)
	@ResponseBody
	@PostMapping("/likes")
	public ResponseEntity<String> addLike(@RequestBody Map<String, Object> params) throws Exception {
		int articleNo = (Integer) params.get("articleNo");
	    String userId = (String) params.get("userId");
		logger.debug("likes articleNo : {}", articleNo);
		logger.debug("likes userId: {}", userId);
		boardService.addLike(articleNo, userId);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요 취소", notes = "좋아요 테이블에서 좋아요를 삭제하고 선택된 게시글의 좋아요 수를 하나 줄인다", response = String.class)
	@ResponseBody
	@DeleteMapping("/likes/{articleNo}")
	public ResponseEntity<String> cancelLike(@PathVariable("articleNo") int articleNo, @RequestParam String userId) throws Exception {
		logger.debug("cancel articleNo : {}", articleNo);
		logger.debug("cancel userId : {}", userId);
		boardService.cancelLike(articleNo, userId);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 이미지 업로드", notes = "Editor에서 사용될 이미지를 업로드한다.", response = String.class)
	@ResponseBody
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws Exception {
	    logger.debug("upload image : {}", file.getOriginalFilename());
	    String today = new SimpleDateFormat("yyMMdd").format(new Date());
	    String saveFolder = imageUploadPath + File.separator + "images" + File.separator + today;
	    File folder = new File(saveFolder);
	    if (!folder.exists())
	        folder.mkdirs();

	    String originalFileName = file.getOriginalFilename();
	    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
	    file.transferTo(new File(folder, saveFileName));

	    String imageURL = "/file/download/" + today + "/" + URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20") + "/" + saveFileName;

	    return new ResponseEntity<String>(imageURL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@ResponseBody
	@PostMapping("/write")
	public ResponseEntity<?> write(BoardDto boardDto, @RequestParam(value="upfile", required = false) MultipartFile[] files, HttpSession session) throws Exception {
		logger.debug("write boardDto : {}", boardDto);
		
		if (boardDto.getUserId() == null || boardDto.getUserId().isEmpty()) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		
		if (files != null) {
			logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		}
		if (files != null && files.length > 0 && !files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			boardDto.setFileInfos(fileInfos);
		}
		
		boardService.writeArticle(boardDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보와 검색 조건을 반환한다.", response = Map.class)
	@ResponseBody
	@GetMapping("/{articleno}")
	public ResponseEntity<Map<String, Object>> view(@PathVariable("articleno") int articleNo, @RequestParam Map<String, String> map)
			throws Exception {
		logger.debug("view articleNo : {}", articleNo);
		BoardDto boardDto = boardService.getArticle(articleNo);
		logger.debug("view boardDto : {}", boardDto);
		logger.debug("view commentDto : {}", boardDto.getComments());
		boardService.updateHit(articleNo);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("article", boardDto);
		output.put("pgno", map.get("pgno"));
		output.put("key", map.get("key"));
		output.put("word", map.get("word"));
		return new ResponseEntity<Map<String,Object>>(output, HttpStatus.OK);
	}
	
	// RequestParam 추가
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@ResponseBody
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody BoardDto boardDto) throws Exception {
		logger.debug("modify boardDto : {}", boardDto);
		boardService.update(boardDto);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. DB삭제 성공시 'success' 문자열 반환.", response = String.class)
	@ResponseBody
	@DeleteMapping("/delete/{articleno}")
	public ResponseEntity<String> delete(@PathVariable("articleno") int articleNo, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("delete articleNo : {}", articleNo);
		boardService.delete(articleNo, uploadPath);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}
}
