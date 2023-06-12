package com.ssafy.board.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.controller.BoardController;
import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.FileInfoDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.util.Algorithm;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService{
	private BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		boardMapper.writeArticle(boardDto);
		List<FileInfoDto> fileInfos = boardDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			boardMapper.registerFile(boardDto);
		}
	}

	@Override
	public List<BoardDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		
		
		List<BoardDto> list = boardMapper.listArticle(param);
		int option = Integer.parseInt(map.getOrDefault("search-option", "1"));
		switch (option) {
		// 지난글 순 정렬
		case 2:
			return Algorithm.sortBoardByRegist(list);
		// 인기 글 순 정렬
		case 3:
			return Algorithm.sortBoardHit(list);
		default:
			return list;
		}
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
		param.put("key", key.isEmpty() ? "" : key);
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void update(BoardDto boardDto) throws Exception {
		boardMapper.update(boardDto);
	}

	@Override
	@Transactional
	public void delete(int articleNo, String path) throws Exception {
		List<FileInfoDto> fileList = boardMapper.fileInfoList(articleNo, 1);
//		어차피 xml단에서 무조건 category 1만 줄거니까 상관 없음
		boardMapper.deleteFile(articleNo);
		boardMapper.delete(articleNo);
		for(FileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}

	@Override
	public boolean likeCheck(int articleNo, String userId) {
		return boardMapper.likeCheck(articleNo, userId);
		
	}

	@Override
	@Transactional
	public void addLike(int articleNo, String userId) {
		boardMapper.addLike(articleNo, userId);
		boardMapper.boardLike(articleNo);
	}
	
	@Override
	@Transactional
	public void cancelLike(int articleNo, String userId) {
		boardMapper.cancelLike(articleNo, userId);
		boardMapper.cancelBoard(articleNo);
	}

	@Override
	public List<BoardDto> topList() throws Exception {
		List<BoardDto> list = boardMapper.topList();
		return list;
	}

}
