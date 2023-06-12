package com.ssafy.attraction.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.attraction.model.dto.AttractionDto;
import com.ssafy.attraction.model.service.AttracitonService;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.ParameterCheck;

import io.swagger.annotations.ApiOperation;

@RestController
public class AttractionController {
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = LoggerFactory.getLogger(AttractionController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private AttracitonService attractionService;

	public AttractionController(AttracitonService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	@GetMapping("/attraction")
	private ResponseEntity<Object> search(@RequestParam Map<String, String> map, @RequestParam(value = "contentTypeId", required = false) List<Integer> contentTypeIdList) {
		logger.debug("Spring에서 Attraction 감지");
		logger.debug("아니왜 안돼는거야 {} {}",map, contentTypeIdList);
		
		int pageNo = ParameterCheck.notNumberToOne(map.get("pageNo"));
		int areaCode = ParameterCheck.notNumberToZero(map.get("areaCode"));
		int sigunguCode = ParameterCheck.notNumberToZero(map.get("sigunguCode"));
//		int contentTypeId = ParameterCheck.notNumberToZero(map.get("contentTypeId"));
		String keyword = ParameterCheck.nullToBlank(map.get("keyword"));
		String searchInResult = ParameterCheck.nullToBlank(map.get("searchInResult"));
		
		List<Integer> contentTypeIds = contentTypeIdList != null ? contentTypeIdList : new ArrayList<>();
		
//		Map<String, String> inputMap = new HashMap<String, String>();
		Map<String, Object> inputMap = new HashMap<String, Object>();
		
		inputMap.put("pageNo", pageNo + "");
		inputMap.put("areaCode", areaCode + "");
		inputMap.put("sigunguCode", sigunguCode + "");
//		inputMap.put("contentTypeId", contentTypeId + "");
		inputMap.put("contentTypeIds", contentTypeIds);
		inputMap.put("keyword", keyword);
		inputMap.put("searchInResult", searchInResult);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			PageNavigation pageNavigation = attractionService.makePageNavigation(inputMap);
			result.put("pageNo", pageNo + "");
			result.put("areaCode", areaCode + "");
			result.put("sigunguCode", sigunguCode+ "");
//			result.put("contentTypeId", contentTypeId + "");
			result.put("contentTypeIds", contentTypeIds); // "contentTypeId" 대신 "contentTypeIds"를 key로 사용하고, 이에 List<Integer>를 value로 사용합니다.
			result.put("keyword", keyword);
			result.put("searchInResult", searchInResult);
			result.put("navigation", pageNavigation);
			List<AttractionDto> list = attractionService.searchAttraction(inputMap);
			result.put("attractionList", list);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error("Error occurred while searching attractions", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@ApiOperation(value = "인기 여행지", notes = "인기 여행지의 정보를 반환한다", response = Map.class)
	@ResponseBody
	@GetMapping("/attraction/top")
	public ResponseEntity<Map<String, Object>> topList() throws Exception {
		logger.info("topList - 호출 : ");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
		List<AttractionDto> trips = attractionService.topList();
		logger.info("AttractionDto : " + trips);
		resultMap.put("trips", trips);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;
	} catch (Exception e) {
		logger.error("정보조회 실패 : {}", e);
		resultMap.put("message", e.getMessage());
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
