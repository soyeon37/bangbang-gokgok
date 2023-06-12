package com.ssafy.user.controller;
//package com.ssafy.user.controller;
//
//import java.util.Map;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.user.model.dto.UserDto;
//import com.ssafy.user.model.service.UserService;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//
//@RestController
//@RequestMapping(value = { "/user", "/" })
//public class UserController {
//	private final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//	private UserService userService;
//
//	public UserController(UserService userService) {
//		super();
//		this.userService = userService;
//	}
//
////	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		String action = request.getParameter("action");
////		
////		String path = "";
////		// 회원가입
////		if("mvjoin".equals(action)) {
////			path = "/user/join.jsp";
////			redirect(request, response, path);
////		} else if("join".equals(action)) {
////			path = join(request, response);
////			redirect(request, response, path);
////		} 
////		// 로그인, 로그아웃
////		else if("mvlogin".equals(action)) {
////			path = "/user/login.jsp";
////			redirect(request, response, path);
////		} else if("login".equals(action)) {
////			path = login(request, response);
////			forward(request, response, path);
////		} else if("logout".equals(action)) {
////			path = logout(request, response);
////			redirect(request, response, path);
////		} 
////		// 비밀번호 찾기
////		else if("mvfindpw".equals(action)) {
////			path = "/user/findpw.jsp";
////			forward(request, response, path);
////		} else if("findpw".equals(action)) {
////			path = findpw(request, response);
////			forward(request, response, path);
////		} else if("findpwres".equals(action)) {
////			path = logout(request, response);
////			redirect(request, response, path);
////		} 
////		// 회원정보 조회, 수정, 탈퇴
////		else if("mvmypage".equals(action)) {
////			path = "/user/mypage.jsp";
////			forward(request, response, path);
////		} else if("modify".equals(action)) {
////			path = modify(request, response);
////			forward(request, response, path);
////		} else if("delete".equals(action)) {
////			path = delete(request, response);
////			redirect(request, response, path);
////		} 
////		else {
////			redirect(request, response, path);
////		}
////	}
//
////	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		request.setCharacterEncoding("utf-8");
////		doGet(request, response);
////	}
////	
////	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
////			throws ServletException, IOException {
////		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
////		dispatcher.forward(request, response);
////	}
////
////	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
////		response.sendRedirect(request.getContextPath() + path);
////	}
//
//	@GetMapping("/{userid}")
//	@ResponseBody
//	public String idCheck(@PathVariable("userid") String userId) throws Exception {
//		logger.debug("idCheck userid : {}", userId);
//		int cnt = userService.idCheck(userId);
//		return cnt + "";
//	}
//
//	@GetMapping("/join")
//	public String join() {
//		return "user/join";
//	}
//
//	@GetMapping("/mypage")
//	public String mypage() {
//		return "user/mypage";
//	}
//
////	@PostMapping("/join")
////	public String join(UserDto userDto, Model model) {
////		// TODO : 이름, 아이디, 비밀번호, 이메일등 회원정보를 받아 UserDto로 setting
////		// TODO : 위의 데이터를 이용하여 service의 joinUser() 호출
////		// TODO : 정상 등록 후 로그인 페이지로 이동.
////		logger.debug("userDto info : {}", userDto);
////		try {
////			userService.joinUser(userDto);
////			return "redirect:/user/login";
////		} catch (Exception e) {
////			e.printStackTrace();
////			model.addAttribute("msg", "회원 가입 중 문제 발생!!!");
////			return "error/error";
////		}
////	}
//	
//	@PostMapping("/join")
//	public ResponseEntity<String> join(@RequestBody UserDto userDto) {
//	    try {
//	        userService.joinUser(userDto);
//	        return ResponseEntity.ok().build();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 문제 발생!!!");
//	    }
//	}
//
////	@GetMapping("/login")
////	public String login() {
////		return "user/login";
////	}
//
////	@PostMapping("/login")
////	public String login(@RequestParam Map<String, String> map,
////			@RequestParam(name = "saveid", required = false) String saveid, Model model, HttpSession session,
////			HttpServletResponse response) {
////		logger.debug("login map : {}", map);
////		try {
////			UserDto userDto = userService.loginUser(map.get("userid"), map.get("userpwd"));
////			if (userDto != null) {
////				session.setAttribute("userinfo", userDto);
////
////				Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
////				cookie.setPath("/");
////				if ("ok".equals(saveid)) {
////					cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
////				} else {
////					cookie.setMaxAge(0);
////				}
////				response.addCookie(cookie);
////				return "redirect:/";
////			} else {
////				model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요.");
////				return "user/login";
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////			model.addAttribute("msg", "로그인 중 문제가 발생했습니다.");
////			return "error/error";
////		}
////	}
//	
//	// 일단 session 써서 하는데 시간 남으면 JWT 등으로 전환해서
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody Map<String, String> map,
//	        @RequestParam(name = "saveid", required = false) String saveid, HttpSession session,
//	        HttpServletResponse response) {
//	    try {
//	        UserDto userDto = userService.loginUser(map.get("userid"), map.get("userpwd"));
//	        
//	        // 로그인 성공했으면 session에 설정
//	        if (userDto != null) {
//	            session.setAttribute("userinfo", userDto);
//
//	            Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
//	            cookie.setPath("/");
//	            if ("ok".equals(saveid)) {
//	                cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
//	            } else {
//	                cookie.setMaxAge(0);
//	            }
//	            response.addCookie(cookie);
//	            return ResponseEntity.ok().build();
//	        } else {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호 확인 후 다시 로그인하세요.");
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 문제가 발생했습니다.");
//	    }
//	}
//
////	@GetMapping("/logout")
////	public String logout(HttpSession session) {
////		session.invalidate();
////		return "redirect:/";
////	}
//	
////	@PostMapping("/logout")
//	@GetMapping("/logout") // post가 권장됨, client단 완성되면 적절히 수정할 것
//	public ResponseEntity<String> logout(HttpSession session) {
//	    session.invalidate();
//	    return ResponseEntity.ok().build();
//	}
//
////	@GetMapping("/delete")
////	private String delete(HttpSession session, HttpServletResponse response) {
////		// TODO 회원정보 삭제
////		UserDto userDto = (UserDto) session.getAttribute("userinfo");
////		try {
////			userService.deleteUser(userDto.getUserId());
////			// 쿠키 삭제
////			Cookie myCookie = new Cookie("userInfo", null);
////			myCookie.setMaxAge(0);
////			myCookie.setPath("/");
////			response.addCookie(myCookie);
////			// 세션 삭제
////			return logout(session);
////		} catch (Exception e) {
////			e.printStackTrace();
////			return "";
////		}
////	}
//	
//	@DeleteMapping("/delete")
//	public ResponseEntity<String> delete(HttpSession session, HttpServletResponse response) {
//	    UserDto userDto = (UserDto) session.getAttribute("userinfo");
//	    try {
//	        userService.deleteUser(userDto.getUserId());
//	        // 쿠키 삭제
//	        Cookie myCookie = new Cookie("userInfo", null);
//	        myCookie.setMaxAge(0);
//	        myCookie.setPath("/");
//	        response.addCookie(myCookie);
//	        
//	        // 세션 삭제
//	        session.invalidate();
//	        return ResponseEntity.ok().build();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
//	    }
//	}
//
////	@PostMapping("/modify")
////	private String modify(@RequestParam Map<String, String> map, HttpSession session, HttpServletResponse response) {
////		// TODO 회원정보 수정
////		UserDto userDto = (UserDto) session.getAttribute("userinfo");
////		try {
////			userDto.setEmailId(map.get("emailId"));
////			userDto.setEmailDomain(map.get("emailDomain"));
//////			memberDto.setEmailId(request.getParameter("emailid"));
//////			memberDto.setEmailDomain(request.getParameter("emaildomain"));
////			userService.modifyUserInfo(userDto);
////			return "/user/mypage";
////		} catch (Exception e) {
////			e.printStackTrace();
////			return "";
////		}
////	}
//	
//	// HttpServletResponse가 있어야 할 이유가 없는듯?
//	@PutMapping("/modify")
//	public ResponseEntity<String> modify(@RequestBody Map<String, String> map, HttpSession session) {
//	    UserDto userDto = (UserDto) session.getAttribute("userinfo");
//	    try {
//	        userDto.setEmailId(map.get("emailId"));
//	        userDto.setEmailDomain(map.get("emailDomain"));
//	        userService.modifyUserInfo(userDto);
//	        return ResponseEntity.ok().build();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
//	    }
//	}
//
////	@GetMapping("/findpw")
////	private String findpw(HttpServletRequest request, HttpServletResponse response) {
////		// TODO 비밀번호 찾기
////		String userId = request.getParameter("userid");
////		try {
////			String pwd = userService.findPwd(userId);
////			request.setAttribute("pwd", pwd);
////			return "/user/findpwResult.jsp";
////		} catch (Exception e) {
////			e.printStackTrace();
////			return "";
////		}
////	}
//	
//	// 이건 findPwd 내부 구현을 봐야 할듯
//	@PostMapping("/findpw")
//	public ResponseEntity<String> findpw(@RequestParam("userid") String userId) {
//	    try {
//	        String pwd = userService.findPwd(userId);
//	        return ResponseEntity.ok(pwd);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
//	    }
//	}
//
//	@GetMapping("/list")
//	public String list() {
//		return "redirect:/assets/list.html";
//	}
//}
