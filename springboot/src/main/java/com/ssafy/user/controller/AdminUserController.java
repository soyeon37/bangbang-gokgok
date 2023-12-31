//package com.ssafy.user.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.member.model.service.MemberService;
//import com.ssafy.user.model.MemberDto;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@RestController
////@Controller
//@RequestMapping("/admin")
////@CrossOrigin("*") // 모든 메소드에 대해서 접근 허용
//
//@Api(tags = { "회원관리" })
//public class AdminUserController {
//
//	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
//
//	private MemberService memberService;
//
//	@Autowired
//	public AdminUserController(MemberService memberService) {
//		this.memberService = memberService;
//	}
//
//////	@ResponseBody
////	@RequestMapping(value = "/user", method = RequestMethod.GET, headers = { "Content-type=application/json" })
////	public List<MemberDto> userList() throws Exception {
////		List<MemberDto> list = memberService.listMember(null);
////		logger.debug("회원목록 : {}", list);
////		return list;
//////        return memberService.listMember();
////	}
////
//////	@ResponseBody
////	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = { "Content-type=application/json" })
////	public List<MemberDto> userRegister(@RequestBody MemberDto memberDto) throws Exception {
////		memberService.joinMember(memberDto);
////		return memberService.listMember(null);
////	}
////
//////	@ResponseBody
////	@RequestMapping(value = "/user/{userid}", method = RequestMethod.GET, headers = { "Content-type=application/json" })
////	public MemberDto userInfo(@PathVariable("userid") String userid) throws Exception {
////		return memberService.getMember(userid);
////	}
////
//////	@ResponseBody
////	@RequestMapping(value = "/user", method = RequestMethod.PUT, headers = { "Content-type=application/json" })
////	public List<MemberDto> userModify(@RequestBody MemberDto memberDto) throws Exception {
////		memberService.updateMember(memberDto);
////		return memberService.listMember(null);
////	}
////
//////	@ResponseBody
////	@RequestMapping(value = "/user/{userid}", method = RequestMethod.DELETE, headers = {
////			"Content-type=application/json" })
////	public List<MemberDto> userDelete(@PathVariable("userid") String userid) throws Exception {
////		memberService.deleteMember(userid);
////		return memberService.listMember(null);
////	}
//	@ApiOperation(value = "회원목록", notes = "회원의 <b>전체 목록</b>을 리턴합니다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK"), @ApiResponse(code = 500, message = "서버 에러") })
//	@GetMapping(value = "/user")
//	public ResponseEntity<?> userList() {
//		logger.debug("userList call");
//		try {
//			List<MemberDto> list = memberService.listMember(null);
//			if (list != null && !list.isEmpty()) {
//				return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
////				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//
//	}
//
//	@ApiOperation(value = "회원 정보", notes = "회원의 한명의 <b>정보</b>를 리턴합니다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "회원정보 OK"), @ApiResponse(code = 500, message = "서버 에러") })
//	@ApiImplicitParams({ @ApiImplicitParam(name = "userid", value = "검색 아이디", required = true, dataType = "String", paramType = "path") })
//	@GetMapping(value = "/user/{userid}")
//	public ResponseEntity<?> userInfo(@PathVariable("userid") String userId) {
//		logger.debug("userInfo userid : {}", userId);
//		try {
//			MemberDto memberDto = memberService.getMember(userId);
//			if (memberDto != null)
//				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
//			else
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//
//	@PostMapping(value = "/user")
//	public ResponseEntity<?> userRegister(@RequestBody MemberDto memberDto) {
//		logger.debug("userRegister memberDto : {}", memberDto);
//		try {
//			memberService.joinMember(memberDto);
//			List<MemberDto> list = memberService.listMember(null);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
////			return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//
//	}
//
//	@PutMapping(value = "/user")
//	public ResponseEntity<?> userModify(@RequestBody MemberDto memberDto) {
//		logger.debug("userModify memberDto : {}", memberDto);
//		try {
//			memberService.updateMember(memberDto);
//			List<MemberDto> list = memberService.listMember(null);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//
//	@DeleteMapping(value = "/user/{userid}")
//	public ResponseEntity<?> userDelete(@PathVariable("userid") String userId) {
//		logger.debug("userDelete userid : {}", userId);
//		try {
//			memberService.deleteMember(userId);
//			List<MemberDto> list = memberService.listMember(null);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//
//	private ResponseEntity<String> exceptionHandling(Exception e) {
//		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//}
