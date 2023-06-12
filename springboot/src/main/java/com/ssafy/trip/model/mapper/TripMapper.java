//package com.ssafy.trip.model.mapper;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.ibatis.annotations.Mapper;
//
//import com.ssafy.trip.model.dto.TripDto;
//
////@Mapper
//public interface TripMapper {
//	int registTrip(TripDto tripDto) throws SQLException;
//	int registTripUpdate(TripDto tripDto) throws SQLException;
//	
//	List<TripDto> tripList(String userId) throws SQLException;
//	
//	int deleteTrip(HashMap<String, Object> map) throws SQLException;
//	int deleteTripUpdateTrip(HashMap<String, Object> map) throws SQLException;
//	int deleteTripUpdateMembers(HashMap<String, Object> map) throws SQLException;
//	int deleteTripSelect(HashMap<String, Object> map) throws SQLException;
//	
//	
//	
////	void modifyTripMemo(int tripId, String memo) throws SQLException;
//	int modifyTripNo(HashMap<String, Object> map) throws SQLException;
//	void modifyTripNoUpdate(HashMap<String, Object> map) throws SQLException;
//	void modifyTripNoUpdateTrip(HashMap<String, Object> map) throws SQLException;
//}
