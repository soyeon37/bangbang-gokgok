package com.ssafy.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.plan.model.dto.PlanParameterDto;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.util.PageNavigation;


public interface PlanService {
	int getTripCnt(String userId, int planId) throws Exception;
	boolean registTrip(HashMap<String, Object> map) throws Exception;
	List<PlanDto> planList(Map<String, String> map) throws Exception;
	List<TripDto> viewPlan(int planId) throws Exception;
	public PageNavigation makePageNavigation(PlanParameterDto boardParameterDto) throws Exception;
	boolean deletePlan(int planId) throws Exception;
	boolean deleteTrip(int tripId, int tripNo, int planId, String userId) throws Exception;
	boolean modifyPlan(int planId, HashMap<String, Object> map) throws Exception;
	boolean modifyTripNo(String userId, int planId, int oldNo, int newNo, int tripId) throws Exception;
	boolean modifyTrip(List<TripDto> tripList, int planId) throws Exception;
	List<PlanDto> topList() throws Exception;
}
