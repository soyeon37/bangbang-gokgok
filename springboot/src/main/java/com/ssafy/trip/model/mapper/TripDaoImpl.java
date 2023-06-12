//package com.ssafy.trip.model.mapper;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//
//import com.ssafy.trip.model.dto.TripDto;
//import com.ssafy.util.DBUtil;
//
//public class TripDaoImpl implements TripMapper {
//	private static TripMapper tripDao = new TripDaoImpl();
//	private DBUtil dbUtil;
//
//	private TripDaoImpl() {
//		dbUtil = DBUtil.getInstance();
//	}
//
//	public static TripMapper getTripDao() {
//		return tripDao;
//	}
//
//	@Override
//	public int registTrip(TripDto tripDto) throws SQLException {
//		// TODO 일정 추가하기
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder("insert trip (trip_no, user_id, content_id, memo) \n");
//			sql.append("value (?,?,?,?); \n");
//
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, tripDto.getTripNo());
//			pstmt.setString(2, tripDto.getUserId());
//			pstmt.setInt(3, tripDto.getContentId());
//			pstmt.setString(4, tripDto.getMemo());
//			pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//
//		// 일정 개수 업데이트
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder("update members \n");
//			sql.append("set trip_cnt = ? \n");
//			sql.append("where user_id = ?;");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, tripDto.getTripNo());
//			pstmt.setString(2, tripDto.getUserId());
//			pstmt.executeUpdate();
//			return tripDto.getTripNo();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//
//	}
//
//	@Override
//	public List<TripDto> tripList(String userId) throws SQLException {
//		// TODO 일정 리스트 가져오기
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder("select * \n");
//			sql.append("from trip, attraction_info \n");
//			sql.append("where trip.user_id = ? and trip.content_id=attraction_info.content_id \n");
//			sql.append("order by trip_no asc \n");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();
//
//			List<TripDto> list = new ArrayList<TripDto>();
//			while (rs.next()) {
//				TripDto tripDto = new TripDto();
//				tripDto.setTripId(rs.getInt("trip_id"));
//				tripDto.setTripNo(rs.getInt("trip_no"));
//				tripDto.setUserId(rs.getString("user_id"));
//				tripDto.setContentId(rs.getInt("content_id"));
//				tripDto.setContentTypeId(rs.getInt("content_type_id"));
//				tripDto.setTitle(rs.getString("title"));
//				tripDto.setFirstImage(rs.getString("first_image"));
//				tripDto.setAddr1(rs.getString("addr1"));
//				tripDto.setAddr2(rs.getString("addr2"));
//				tripDto.setZipcode(rs.getString("zipcode"));
//				tripDto.setTel(rs.getString("tel"));
//				tripDto.setLatitude(rs.getDouble("latitude"));
//				tripDto.setLongitude(rs.getDouble("longitude"));
//				tripDto.setMemo(rs.getString("memo"));
//
//				list.add(tripDto);
//			}
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//	}
//
//	@Override
//	public int deleteTrip(int tripId, int tripNo, String userId) throws SQLException {
//		// TODO 일정 삭제하기
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder("delete from trip \n");
//			sql.append("where trip_id = ?; \n");
//
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, tripId);
//			pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//
//		// 일정 순서 업데이트
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder("update trip \n");
//			sql.append("set trip_no = trip_no - 1 \n");
//			sql.append("where user_id = ? and trip_no > ?;");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, userId);
//			pstmt.setInt(2, tripNo);
//			pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//
//		try {
//			conn = dbUtil.getConnection();
//			// 일정 개수 업데이트
//			StringBuilder sql = new StringBuilder("update members \n");
//			sql.append("set trip_cnt = trip_cnt - 1 \n");
//			sql.append("where user_id = ?;");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, userId);
//			pstmt.executeUpdate();
//
//			// 일정 개수 가져오기
//			sql = new StringBuilder("select trip_cnt \n");
//			sql.append("from members \n");
//			sql.append("where user_id = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, userId);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				return rs.getInt("trip_cnt");
//			}
//			return -1;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
////	@Override
////	public void modifyTripMemo(int tripId, String memo) throws SQLException {
////		// TODO 일정 메모 수정하기
////
////	}
//
//	@Override
//	public void modifyTripNo(String userId, int gap, int tripId) throws SQLException {
//		// TODO 일정 순서 변경하기
//		int tripNo = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			// 1. 타겟 일정 순서 가져오기
//			StringBuilder sql = new StringBuilder("select trip_no \n");
//			sql.append("from trip \n");
//			sql.append("where trip_id = ?;");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, tripId);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				tripNo = rs.getInt("trip_no");
//			}
//			
//			System.out.println(gap+" "+tripId+" "+tripNo);
//
//			// 2. 나머지 일정 순서 재배정
//			sql = new StringBuilder("update trip \n");
//			// 뒤로 갔을 때 -> 앞으로 밀리기
//			if (gap > 0) {
//				sql.append("set trip_no = trip_no - 1 \n");
//				sql.append("where user_id = ? and trip_no > ? and trip_no <= ?;");
//			}
//			// 앞으로 갔을 때 -> 뒤로 밀리기
//			else {
//				sql.append("set trip_no = trip_no + 1 \n");
//				sql.append("where user_id = ? and trip_no >= ? and trip_no < ?;");
//			}
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, userId);
//			if (gap > 0) {
//				pstmt.setInt(2, tripNo);
//				pstmt.setInt(3, tripNo + gap);
//			} else {
//				pstmt.setInt(2, tripNo + gap);
//				pstmt.setInt(3, tripNo);
//			}
//			pstmt.executeUpdate();
//			System.out.println("hi");
//			
//			// 3. 타겟 일정 순서 변경
//			sql = new StringBuilder("update trip \n");
//			sql.append("set trip_no = ? \n");
//			sql.append("where trip_id = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, tripNo+gap);
//			pstmt.setInt(2, tripId);
//			pstmt.executeUpdate();
//			System.out.println("hi");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
//	@Override
//	public int deleteTrip(HashMap<String, Object> map) throws SQLException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void modifyTripNo(HashMap<String, Object> map) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
