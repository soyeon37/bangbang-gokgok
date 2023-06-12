package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.FileInfoDto;
import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.plan.model.dto.PlanDto;

@Mapper
public interface BoardMapper {
	void writeArticle(BoardDto boardDto) throws SQLException;
	void registerFile(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	CommentDto commentList(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	void update(BoardDto boardDto) throws SQLException;
	void delete(int articleNo) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo, int category) throws Exception;
	List<PlanDto> planList(int articleNo, int category) throws Exception;
	boolean likeCheck(int articleNo, String userId);
	void addLike(int articleNo, String userId);
	void boardLike(int articleNo);
	List<BoardDto> topList() throws SQLException;
	void cancelLike(int articleNo, String userId);
	void cancelBoard(int articleNo);
}
