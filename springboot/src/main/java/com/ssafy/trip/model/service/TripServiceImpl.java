//package com.ssafy.trip.model.service;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.ssafy.trip.model.dto.TripDto;
//import com.ssafy.trip.model.mapper.TripMapper;
//
////@Service
//public class TripServiceImpl implements TripService {
//	private TripMapper tripMapper;
//
//	
//	public TripServiceImpl(TripMapper tripMapper) {
//		super();
//		this.tripMapper = tripMapper;
//	}
//
//	@Override
//	public int registTrip(TripDto tripDto) throws Exception {
//		// TODO 일정 등록하기
//		int res = tripMapper.registTrip(tripDto);
//		tripMapper.registTripUpdate(tripDto);
//		return res;
//	}
//
//	@Override
//	public List<TripDto> tripList(String userId) throws Exception {
//		// TODO 일정 리스트 가져오기
//		return tripMapper.tripList(userId);
//	}
//
//	@Override
//	public int deleteTrip(int tripId, int tripNo, String userId) throws Exception {
//		// TODO 일정 삭제하기
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("tripId", tripId);
//		map.put("tripNo", tripNo);
//		map.put("userId", userId);
//		
//		int res = tripMapper.deleteTrip(map);
//		tripMapper.deleteTripUpdateTrip(map);
//		tripMapper.deleteTripUpdateMembers(map);
//		tripMapper.deleteTripSelect(map);
//		return res;
//	}
//
////	@Override
////	public void modifyTripMemo(int tripId, String memo) throws Exception {
////		
////	}
//
//	@Override
//	public void modifyTripNo(String userId, int oldNo, int newNo, int tripId) throws Exception {
//		// TODO Auto-generated method stub
//		int gap = newNo - oldNo;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("gap", gap);
//		map.put("tripId", tripId);
//		
//		int tripno = tripMapper.modifyTripNo(map);
//		map.put("tripNo", tripno);
//		tripMapper.modifyTripNoUpdate(map);
//		tripMapper.modifyTripNoUpdateTrip(map);
//		
//	}
//
//}
