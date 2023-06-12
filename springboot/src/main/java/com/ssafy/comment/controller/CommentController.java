package com.ssafy.comment.controller;

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

import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.comment.model.service.CommentService;
import com.ssafy.user.model.dto.UserDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/comment")
public class CommentController {
	private final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@ApiOperation(value = "게시판 댓글목록", notes = "특정 게시글의 댓글을 반환한다", response = Map.class)
	@GetMapping("/{articleid}/{category}")
	public ResponseEntity<?> list(@PathVariable("articleid") int articleId, @PathVariable("category") int category) {
		logger.debug("view articleId : {}", articleId);
		logger.debug("view category : {}", category);
		List<CommentDto> list = commentService.getComments(articleId, category);
		return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); 
	}
	
	@ApiOperation(value = "게시판 댓글작성", notes = "새로운 댓글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody CommentDto commentDto) throws Exception {
		logger.debug("write commentDto : {}", commentDto);
		
		// Check if userId is provided
		if (commentDto.getUserId() == null || commentDto.getUserId().isEmpty()) {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		
		
		commentService.insertComment(commentDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 댓글수정", notes = "수정할 댓글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody CommentDto commentDto) throws Exception {
		logger.debug("modify commentDto : {}", commentDto);
		commentService.updateComment(commentDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 댓글 삭제", notes = "댓글번호에 해당하는 댓글의 정보를 삭제한다. DB삭제 성공시 'success' 문자열 반환.", response = String.class)
	@DeleteMapping("/delete/{commentid}/{category}")
	public ResponseEntity<String> delete(@PathVariable("commentid") int commentId, @PathVariable("category") int category) throws Exception {
		logger.debug("delete commentId : {}", commentId);
		commentService.deleteComment(commentId, category);
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}
}
