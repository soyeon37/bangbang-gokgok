package com.ssafy.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.comment.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentMapper commentMapper;
	
	public CommentServiceImpl(CommentMapper commentMapper) {
		super();
		this.commentMapper = commentMapper;
	}

	@Transactional
	@Override
	public void insertComment(CommentDto commentDto) {
		commentMapper.insertComment(commentDto);
		if (commentDto.getDepth() == 0) {
			commentMapper.updateGroupNo(commentDto.getCommentId());
		}
	}

	@Override
	public List<CommentDto> getComments(int articleId, int category) {
		List<CommentDto> list = commentMapper.getComments(articleId, category);
		return list;
	}

	@Override
	public void updateComment(CommentDto commentDto) {
		commentMapper.updateComment(commentDto);
	}

	@Override
	public void deleteComment(int commentId, int category) {
		commentMapper.deleteComment(commentId, category);
	}
}
