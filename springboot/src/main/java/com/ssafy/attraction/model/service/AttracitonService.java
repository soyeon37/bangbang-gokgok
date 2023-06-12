package com.ssafy.attraction.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.dto.AttractionDto;
import com.ssafy.util.PageNavigation;

public interface AttracitonService {
	List<AttractionDto> searchAttraction(Map<String, Object> map) throws Exception;
//	List<AttractionDto> searchAttraction(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, Object> map) throws Exception;
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	List<AttractionDto> topList() throws Exception;
}
