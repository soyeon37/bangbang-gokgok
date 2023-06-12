//package com.ssafy.notice.model.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.ssafy.notice.model.dto.NoticeDto;
//import com.ssafy.notice.model.service.NoticeService;
//import com.ssafy.user.model.dto.UserDto;
//import com.ssafy.util.PageNavigation;
//
//
////@RestController
//@Controller
//@RequestMapping("/notice")
//public class NoticeController extends HttpServlet{
//	private final Logger logger = LoggerFactory.getLogger(NoticeController.class);
//
//	private static final long serialVersionUID = 3630025075122122420L;
//
//	private int pgno;
//	private String key;
//	private String word;
//	private String queryString;
//	private int searchOption;
//	
//	private NoticeService noticeService;
//	
//	public NoticeController(NoticeService noticeService) {
//		super();
//		this.noticeService = noticeService;
//	}
//	
//	@GetMapping("/mvlist")
//	public String mvList() {
//		return "notice";
//	}
//	
//	// localhost/notice/list?pgno=1&key=&word=&search-option=1로 테스트 가능
//	// 공지사항에 올라가 있는 모든 게시글들을 리턴함
//	@GetMapping("/list")
//	public ModelAndView list(@RequestParam Map<String, String> map, Model model) throws Exception {
//		logger.debug("list parameter pgno : {}", map.get("pgno"));
//		ModelAndView mav = new ModelAndView();
//		List<NoticeDto> list = noticeService.listArticle(map);
//		PageNavigation pageNavigation = noticeService.makePageNavigation(map);
//		mav.addObject("articles", list);
//		mav.addObject("navigation", pageNavigation);
//		mav.addObject("pgno", map.get("pgno"));
//		mav.addObject("key", map.get("key"));
//		mav.addObject("word", map.get("word"));
//		mav.setViewName("notice/list");
//		return mav;
//	}
//	
//	
//	/**
//	 *subject : hello,
//	 *content : goodman
//	 */
//	
//	@GetMapping("/write")
//	public String write(@RequestParam Map<String, String> map, Model model) {
//		logger.debug("write call parameter {}", map);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "notice/write";
//	}
//	// 프론트단에서 뭘로 넘겨주는지 몰라서 일단 임시
//	@PostMapping("/write")
//	public String write(NoticeDto noticeDto, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
//		logger.debug("write noticeDto : {}", noticeDto);
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		noticeDto.setUserId(userDto.getUserId());
//		noticeService.writeArticle(noticeDto);
//		redirectAttributes.addAttribute("pgno", "1");
//		redirectAttributes.addAttribute("key", "");
//		redirectAttributes.addAttribute("word", "");
//		return "redirect:/notice/list";
//	}
//	
//	// localhost/notice/view?articleno=1&pgno=1&key=&word=&search-option=1
//	@GetMapping("/view")
//	public String view(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
//			throws Exception {
//		logger.debug("view articleNo : {}", articleNo);
//		NoticeDto noticeDto = noticeService.getArticle(articleNo);
//		noticeService.updateHit(articleNo);
//		model.addAttribute("article", noticeDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "notice/view";
//	}
//	
//	@GetMapping("/modify")
//	public String modify(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model) throws Exception {
//		logger.debug("modify articleNo : {}", articleNo);
//		NoticeDto noticeDto = noticeService.getArticle(articleNo);
//		model.addAttribute("article", noticeDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "notice/modify";
//	}
//	
//	@PostMapping("/modify")
//	public String modify(@RequestParam("articleno") int articleNo, NoticeDto noticeDto, @RequestParam Map<String, String> map, RedirectAttributes redirectAttributes) throws Exception {
//		logger.debug("modify NoticeDto : {}", noticeDto);
//		noticeDto.setNoticeId(articleNo);
//		System.out.println(noticeDto.getContent());
//		noticeService.update(noticeDto);
//		redirectAttributes.addAttribute("pgno", map.getOrDefault("pgno","1"));
//		redirectAttributes.addAttribute("key", map.getOrDefault("key", ""));
//		redirectAttributes.addAttribute("word", map.getOrDefault("word", ""));
//		return "redirect:/notice/list";
//	}
//	
//	// localhost/notice/delete?articleno=1&pgno=1&key=&word= 로 테스트는 가능하나 권장하지 않음
//	@GetMapping("/delete")
//	public String delete(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, RedirectAttributes redirectAttributes) throws Exception {
//		logger.debug("delete articleNo : {}", articleNo);
//		noticeService.delete(articleNo);
//		redirectAttributes.addAttribute("pgno", map.getOrDefault("pgno","1"));
//		redirectAttributes.addAttribute("key", map.getOrDefault("key", ""));
//		redirectAttributes.addAttribute("word", map.getOrDefault("word", ""));
//		return "redirect:/notice/list";
//	}
//}
