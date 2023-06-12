package com.ssafy.attraction.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.attraction.model.dto.AttractionDto;
import com.ssafy.attraction.model.mapper.AttractionMapper;
import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.util.Algorithm;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class AttractionServiceImpl implements AttracitonService {
	private AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
//	public List<AttractionDto> searchAttraction(Map<String, String> map) throws Exception {
	public List<AttractionDto> searchAttraction(Map<String, Object> map) throws Exception {
		Map<String, Object> param = new HashMap<>();
		int areaCode = Integer.parseInt((String) map.get("areaCode"));
		int sigunguCode = Integer.parseInt((String) map.get("sigunguCode"));
//		int contentTypeId = Integer.parseInt(map.get("contentTypeId"));
		List<Integer> contentTypeIds = (List<Integer>) map.get("contentTypeIds");
		String keyword = (String) map.get("keyword");
		String searchInResult = (String) map.get("searchInResult");
		param.put("areaCode", areaCode);
		param.put("sigunguCode", sigunguCode);
//		param.put("contentTypeId", contentTypeId);
		param.put("contentTypeIds", contentTypeIds);
		param.put("keyword", keyword.isEmpty() ? "" : keyword);

		int pageNo = Integer.parseInt((String) map.get("pageNo"));
		int start = pageNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		if (searchInResult.equals(""))
//			attractionMapper도 갈아엎어야함 후
			return attractionMapper.searchAttraction(param);
//		KMP로 찾은 데이터 내보내기
		return Algorithm.filterByKeyword(attractionMapper.searchAttraction(param), searchInResult);
	}

	@Override
//	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
	public PageNavigation makePageNavigation(Map<String, Object> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt((String) map.get("pageNo"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		int areaCode = Integer.parseInt((String) map.get("areaCode"));
		int sigunguCode = Integer.parseInt((String) map.get("sigunguCode"));
//		int contentTypeId = Integer.parseInt(map.get("contentTypeId"));
		List<Integer> contentTypeIds = (List<Integer>) map.get("contentTypeIds");
		String keyword = (String) map.get("keyword");
		param.put("areaCode", areaCode);
		param.put("sigunguCode", sigunguCode);
		param.put("contentTypeIds", contentTypeIds);
		param.put("keyword", keyword.isEmpty() ? "" : keyword);
		int totalCount = attractionMapper.getTotalAttractionCount(param);
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
	public List<AttractionDto> topList() throws Exception {
		List<AttractionDto> list = attractionMapper.topList();
		return list;
	}

}
