package com.ssafy.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.plan.model.controller.PlanController;
import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.plan.model.dto.PlanParameterDto;
import com.ssafy.plan.model.mapper.PlanMapper;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.util.Algorithm;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class PlanServiceImpl implements PlanService {
	private PlanMapper planMapper;
	@Autowired
	private SqlSession sqlSession;
	private final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);
	@Override
	public boolean registTrip(HashMap<String, Object> map) throws Exception {
		// TODO 일정 등록하기
		return sqlSession.getMapper(PlanMapper.class).registTrip(map) == 1;
	}

	@Override
	public PageNavigation makePageNavigation(PlanParameterDto planParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(planParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(PlanMapper.class).getTotalCount(planParameterDto);// 총글갯수 269
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / planParameterDto.getSpp() + 1;// 27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = planParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < planParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public List<PlanDto> planList(Map<String, String> map) throws Exception {
		// TODO 일정 리스트 가져오기
		Map<String, Object> param = new HashMap<>();
		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("userId", map.get("userId"));
		List<PlanDto> list = sqlSession.getMapper(PlanMapper.class).planList(param);
		int option = Integer.parseInt(map.getOrDefault("search-option", "1"));
		switch (option) {
//		// 지난글 순 정렬
//		case 2:
//			return Algorithm.sortBoardByRegistP(list);
//		// 인기 글 순 정렬
//		case 3:
//			return Algorithm.sortBoardHitP(list);
		default:
			return list;
		}
	}

	@Override
	public List<TripDto> viewPlan(int planId) throws Exception {
		return sqlSession.getMapper(PlanMapper.class).viewPlan(planId);
	}

	@Override
	public boolean deletePlan(int planId) throws Exception {
		sqlSession.getMapper(PlanMapper.class).deleteMemo(planId);
		return sqlSession.getMapper(PlanMapper.class).deletePlan(planId) == 1;
	}

	@Override
	public boolean modifyTripNo(String userId, int planId, int oldNo, int newNo, int tripId) throws Exception {
		// TODO 일정 수정하기
		int gap = newNo - oldNo;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("gap", gap);
		map.put("planId", planId);
		map.put("tripId", tripId);

		int planNo = sqlSession.getMapper(PlanMapper.class).modifyTripNo(map);
		map.put("planNo", planNo);
		sqlSession.getMapper(PlanMapper.class).modifyTripNoUpdate(map);
		return sqlSession.getMapper(PlanMapper.class).modifyTripNoUpdateTrip(map) == 1;
	}

	@Override
	public boolean modifyPlan(int planId, HashMap<String, Object> map) throws Exception {
		map.put("planId", planId);
		sqlSession.getMapper(PlanMapper.class).modifyMemo(map);
		return sqlSession.getMapper(PlanMapper.class).modifyPlan(map) == 1;
	}

	@Override
	public int getTripCnt(String userId, int planId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("planId", planId);
		return sqlSession.getMapper(PlanMapper.class).getTripCnt(map);
	}

	@Override
	public boolean deleteTrip(int tripId, int tripNo, int planId, String userId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tripId", tripId);
		map.put("tripNo", tripNo);
		map.put("planId", planId);
		map.put("userId", userId);

		sqlSession.getMapper(PlanMapper.class).deleteTrip(map);
		sqlSession.getMapper(PlanMapper.class).deleteTripUpdatePlan(map);
		return sqlSession.getMapper(PlanMapper.class).deleteTripUpdateUsers(map) == 1;
	}

	@Override
	public List<PlanDto> topList() throws Exception {
		List<PlanDto> list = sqlSession.getMapper(PlanMapper.class).topList();
		return list;
	}

	@Transactional
	@Override
	public boolean modifyTrip(List<TripDto> tripList, int planId) throws Exception {
		
		// trip 테이블 삭제
		sqlSession.getMapper(PlanMapper.class).modifyTripDelete(planId);
		// tripNo 등록
		boolean res = true;
		for (int i = 0; i < tripList.size(); i++) {
			tripList.get(i).setTripNo(i+1);
			tripList.get(i).setPlanId(planId);
//			logger.debug("tripList " + i + "의 planId " + tripList.get(i).getPlanId());
			if(sqlSession.getMapper(PlanMapper.class).modifyTripInsert(tripList.get(i)) != 1)
				res = false;
		}
		// 등록 
		return res;
	}

//	@Override
//	public int deletePlan(int planId, int planNo, String userId) throws Exception {
//		// TODO 일정 삭제하기
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("planId", planId);
//		map.put("planNo", planNo);
//		map.put("userId", userId);
//
//		int res = planMapper.deletePlan(map);
//		planMapper.deletePlanUpdatePlan(map);
//		planMapper.deletePlanUpdateUsers(map);
//		planMapper.deletePlanSelect(map);
//		return res;
//	}
//
//	@Override
//	public void modifyPlanNo(String userId, int oldNo, int newNo, int planId) throws Exception {
//		// TODO 일정 수정하기
//		int gap = newNo - oldNo;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("gap", gap);
//		map.put("planId", planId);
//
//		int planNo = planMapper.modifyPlanNo(map);
//		map.put("planNo", planNo);
//		planMapper.modifyPlanNoUpdate(map);
//		planMapper.modifyPlanNoUpdatePlan(map);
//	}

}
