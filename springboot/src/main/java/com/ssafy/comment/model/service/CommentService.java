package com.ssafy.comment.model.service;

import java.util.List;

import com.ssafy.comment.model.dto.CommentDto;

public interface CommentService {
	void insertComment(CommentDto commentDto);
	List<CommentDto> getComments(int articleId, int category);
	void updateComment(CommentDto commentDto);
	void deleteComment(int commentId, int category);
}
