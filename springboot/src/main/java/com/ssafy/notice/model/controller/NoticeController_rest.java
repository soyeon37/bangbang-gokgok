package com.ssafy.notice.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ssafy.user.model.dto.UserDto;
import com.ssafy.notice.model.dto.NoticeDto;
import com.ssafy.notice.model.service.NoticeService;
import com.ssafy.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/notice")
@Api("공지사항 컨트롤러  API")
public class NoticeController_rest extends HttpServlet{
	private final Logger logger = LoggerFactory.getLogger(NoticeController_rest.class);
	private static final long serialVersionUID = 3630025075122122420L;
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private NoticeService noticeService;
	
	public NoticeController_rest(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}
	
	@GetMapping("/mvlist")
	public String mvList() {
		return "notice";
	}
	
	// localhost/notice/list?pgno=1&key=&word=&search-option=1로 테스트 가능
	// 공지사항에 올라가 있는 모든 게시글들을 리턴함
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보와 검색 키워드를 반환한다", response = Map.class)
	@ResponseBody
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("list parameter pgno : {}", map.get("pgno"));
		List<NoticeDto> list = noticeService.listArticle(map);
//		input map으로 따로 안뺴도 된다고?
		PageNavigation pageNavigation = noticeService.makePageNavigation(map);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("articles", list);
		output.put("pageNavigation", pageNavigation);
		output.put("pgno", map.get("pgno"));
		output.put("key", map.get("key"));
		output.put("word", map.get("word"));
		return new ResponseEntity<Map<String,Object>>(output, HttpStatus.OK);
	}
	
	
	/**
	 *subject : hello,
	 *content : goodman
	 */
	// 프론트단에서 뭘로 넘겨주는지 몰라서 일단 임시
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@ResponseBody
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody NoticeDto noticeDto, HttpSession session) throws Exception {
		logger.debug("write noticeDto : {}", noticeDto);
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		
		if (userDto == null) {
//			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
			noticeDto.setUserId("ssafy");
		} else {
			noticeDto.setUserId(userDto.getUserId());
		}
		
		noticeService.writeArticle(noticeDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// localhost/notice/{aricleno}?pgno=1&key=&word=&search-option=1 으로 테스트 가능
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보와 검색 조건을 반환한다.", response = Map.class)
	@ResponseBody
	@GetMapping("/{articleno}")
	public ResponseEntity<Map<String, Object>> view(@PathVariable("articleno") int articleNo, @RequestParam Map<String, String> map)
			throws Exception {
		logger.debug("view articleNo : {}", articleNo);
		NoticeDto noticeDto = noticeService.getArticle(articleNo);
		noticeService.updateHit(articleNo);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("article", noticeDto);
		output.put("pgno", map.get("pgno"));
		output.put("key", map.get("key"));
		output.put("word", map.get("word"));
		return new ResponseEntity<Map<String,Object>>(output, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@ResponseBody
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody NoticeDto noticeDto) throws Exception {
//	public ResponseEntity<String> modify(@RequestParam("articleno") int articleNo, @RequestBody NoticeDto noticeDto, @RequestParam Map<String, String> map) throws Exception {
//	public ResponseEntity<Map<String, Object>> modify(@RequestParam("articleno") int articleNo, NoticeDto noticeDto, @RequestParam Map<String, String> map) throws Exception {
		logger.debug("modify NoticeDto : {}", noticeDto);
		noticeService.update(noticeDto);
//		Map<String, Object> output = new HashMap<String, Object>();
//		output.put("pgno", map.get("pgno"));
//		output.put("key", map.get("key"));
//		output.put("word", map.get("word"));
//		return new ResponseEntity<Map<String,Object>>(output, HttpStatus.OK);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}
	
	// localhost/notice/delete?articleno=1&pgno=1&key=&word= 로 테스트는 가능하나 권장하지 않음
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. DB삭제 성공시 'success' 문자열 반환.", response = String.class)
	@ResponseBody
	@DeleteMapping("/delete/{articleno}")
//	public ResponseEntity<Map<String, Object>> delete(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map) throws Exception {
	public ResponseEntity<String> delete(@PathVariable("articleno") int articleNo, @RequestParam Map<String, String> map) throws Exception {
		logger.debug("delete articleNo : {}", articleNo);
		noticeService.delete(articleNo);
//		Map<String, Object> output = new HashMap<String, Object>();
//		output.put("pgno", map.get("pgno"));
//		output.put("key", map.get("key"));
//		output.put("word", map.get("word"));
//		return new ResponseEntity<Map<String,Object>>(output, HttpStatus.OK);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}
}
