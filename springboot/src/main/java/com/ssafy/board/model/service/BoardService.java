package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.util.PageNavigation;

public interface BoardService {
	void writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void update(BoardDto boardDto) throws Exception;
	void delete(int articleNo, String path)  throws Exception;
	boolean likeCheck(int articleNo, String userId);
	void addLike(int articleNo, String userId);
	List<BoardDto> topList() throws Exception;
	void cancelLike(int articleNo, String userId);
}
