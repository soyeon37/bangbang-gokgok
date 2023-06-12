package com.ssafy.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.notice.model.dto.NoticeDto;
import com.ssafy.notice.model.mapper.NoticeMapper;
import com.ssafy.util.Algorithm;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class NoticeServiceImpl implements NoticeService{
	private NoticeMapper noticeMapper;

	public NoticeServiceImpl(NoticeMapper noticeMapper) {
		super();
		this.noticeMapper = noticeMapper;
	}

	@Override
	public void writeArticle(NoticeDto noticeDto) throws Exception {
		noticeMapper.writeArticle(noticeDto);
	}

	@Override
	public List<NoticeDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		
		List<NoticeDto> list = noticeMapper.listArticle(param);
		
		int option = Integer.parseInt(map.getOrDefault("search-option", "0"));
		switch (option) {
		// 지난글 순 정렬
		case 2:
			return Algorithm.sortBoardByRegistN(list);
		// 인기 글 순 정렬
		case 3:
			return Algorithm.sortBoardHitN(list);
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
		int totalCount = noticeMapper.getTotalArticleCount(param);
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
	public NoticeDto getArticle(int articleNo) throws Exception {
		return noticeMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		noticeMapper.updateHit(articleNo);
	}

	@Override
	public void update(NoticeDto noticeDto) throws Exception {
		noticeMapper.update(noticeDto);
	}

	@Override
	public void delete(int articleNo) throws Exception {
		noticeMapper.delete(articleNo);
	}
}
