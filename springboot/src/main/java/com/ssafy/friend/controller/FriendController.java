package com.ssafy.friend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.controller.AttractionController;
import com.ssafy.friend.model.dto.FriendDto;
import com.ssafy.friend.model.service.FriendService;
import com.ssafy.user.model.dto.UserDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/friend")
public class FriendController {
	private final Logger logger = LoggerFactory.getLogger(AttractionController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private FriendService friendService;
	
	public FriendController(FriendService friendService) {
		super();
		this.friendService = friendService;
	}
	
	@ApiOperation(value = "요청받은 친구추가 목록", notes = "특정 유저가 로그인 할떄 요청받은 친구 추가 요청을 조회한다", response = List.class)
	@GetMapping("/{userId}")
	public ResponseEntity<?> list(@PathVariable("userId") String userId) {
		logger.debug("view userId : {}", userId);
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		Map<String, Object> resultMap = new HashMap<>();
		try {
		List<FriendDto> list = friendService.getFriendList(userId);
		logger.info("friendList:"+list);
//			로그인 사용자 정보.
		resultMap.put("friendinfo", list);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;
	} catch (Exception e) {
		logger.error("정보조회 실패 : {}", e);
		resultMap.put("message", e.getMessage());
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "로그인한 유저의 등록된 친구 목록", notes = "특정 유저의 모든 친구를 조회한다", response = List.class)
	@GetMapping("/nowfriends/{userId}")
	public ResponseEntity<?> nowFriends(@PathVariable("userId") String userId) {
		logger.debug("view userId : {}", userId);
		List<FriendDto> list = friendService.nowFriends(userId);
		return new ResponseEntity<List<FriendDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "친구추가 요청 전송", notes = "다른 유저에게 친구추가 요청을 전송한다", response = String.class)
	@PostMapping("/add")
	public ResponseEntity<?> write(@RequestBody Map<String, String> map) throws Exception {
		String followerId = map.get("followerId");
		String userId = map.get("userId");
		logger.debug("addFreind 요청이 조회되었습니다: {}, {}", followerId, userId);
		friendService.addFriend(followerId, userId);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "친구추가 요청 수락", notes = "수정할 댓글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/accept")
	public ResponseEntity<String> modify(@RequestBody Map<String, String> map) throws Exception {
		logger.debug("친구 추가 요청을 수락합니다");
		String followerId = map.get("followerId");
		String userId = map.get("userId");
		friendService.acceptFriend(followerId, userId);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "친구추가 요청 거절", notes = "친구 테이블의 요소를 삭제한다", response = String.class)
	@DeleteMapping("/decline/{followerId}/{userId}")
	public ResponseEntity<String> delete(@PathVariable("followerId") String followerId, @PathVariable("userId") String userId) throws Exception {
		logger.debug("친구 추가 요청을 거절합니다");
		friendService.declineFriend(followerId, userId);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
}
