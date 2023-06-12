package com.ssafy.comment.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
	void insertComment(CommentDto commentDto);
	List<CommentDto> getComments(int articleId, int category);
	void updateComment(CommentDto commentDto);
	void deleteComment(int commentId, int category);
	void updateGroupNo(int commentId);
}
