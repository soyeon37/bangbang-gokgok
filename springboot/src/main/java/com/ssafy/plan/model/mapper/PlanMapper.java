package com.ssafy.plan.model.mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.plan.model.dto.PlanParameterDto;
import com.ssafy.trip.model.dto.TripDto;

@Mapper
public interface PlanMapper {
	int registTrip(HashMap<String, Object> map) throws SQLException; // 플랜에 여행지 추가
	List<PlanDto> planList(Map<String, Object> param) throws SQLException; // 모든 여행 계획을 조회 -> userId 구현 -> planid로 trip id 쭉 볼수있으니까 
	int deletePlan(int planId) throws SQLException; // 여행을 제거함
	void deleteMemo(int planId) throws SQLException; // 여행 메모를 제거함
	List<TripDto> viewPlan(int planId) throws SQLException; // 계획 내의 모든 여행지를 조회함 -> planid주면 모든 tripid보면서 여행지 싹 다 조회
	int getTotalCount(PlanParameterDto planParameterDto) throws SQLException; // 한 계획 내에 여행지의 개수
	int modifyMemo(HashMap<String, Object> map) throws SQLException; // 메모 수정
	int modifyPlan(HashMap<String, Object> map) throws SQLException; // 여행계획 수정
	int getTripCnt(HashMap<String, Object> map) throws SQLException; // 계획내 여행지 개수를 셈
//	int registPlanUpdate(PlanDto planDto) throws SQLException;

	
	int deleteTrip(HashMap<String, Object> map) throws SQLException; // 여행지를 제거함
	int deleteTripUpdatePlan(HashMap<String, Object> map) throws SQLException; // 여행지를 제거하고 계획을 업데이트함?
	int deleteTripUpdateUsers(HashMap<String, Object> map) throws SQLException; // 여행지를 제거하고 유저를 업데이트함??

//	void modifyTripMemo(int tripId, String memo) throws SQLException;
	int modifyTripNo(HashMap<String, Object> map) throws SQLException; // 여행지 순서 수정?
	void modifyTripNoUpdate(HashMap<String, Object> map) throws SQLException; // 여행지 순서 업뎃 뭐지
	int modifyTripNoUpdateTrip(HashMap<String, Object> map) throws SQLException; // ??
	
	void modifyTripDelete(int planId) throws SQLException;
	int modifyTripInsert(TripDto tripDto) throws SQLException;
	
	List<PlanDto> topList() throws SQLException;
}

/*
 구상
 
 */
