package com.ssafy.user.model.mapper;
//package com.ssafy.member.model.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import com.ssafy.member.model.MemberDto;
//import com.ssafy.util.DBUtil;
//
//public class MemberDaoImpl implements MemberDao {
//	
//	private static MemberDao memberDao = new MemberDaoImpl();
//	private DBUtil dbUtil;
//	
//	private MemberDaoImpl() {
//		dbUtil = DBUtil.getInstance();
//	}
//	
//	public static MemberDao getMemberDao() {
//		return memberDao;
//	}
//
//	@Override
//	public int idCheck(String userId) throws SQLException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int joinMember(MemberDto memberDto) throws SQLException {
//		// TODO 회원가입
//		int cnt = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder("insert into members (user_id, user_name, user_password, email_id, email_domain) \n");
//			sql.append("value (?,?,?,?,?) ");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, memberDto.getUserId());
//			pstmt.setString(2, memberDto.getUserName());
//			pstmt.setString(3, memberDto.getUserPwd());
//			pstmt.setString(4, memberDto.getEmailId());
//			pstmt.setString(5, memberDto.getEmailDomain());
//			cnt = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			dbUtil.close(pstmt, conn);
//		}
//		return cnt;
//	}
//
//	@Override
//	public MemberDto loginMember(String userId) throws SQLException {
//		MemberDto memberDto = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder loginMember = new StringBuilder();
//			loginMember.append("select user_id, user_password, user_name, email_id, email_domain, join_date, trip_cnt \n");
//			loginMember.append("from members \n");
//			loginMember.append("where user_id = ?\n");
//			pstmt = conn.prepareStatement(loginMember.toString());
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				memberDto = new MemberDto();
//				memberDto.setUserId(rs.getString("user_id"));
//				memberDto.setUserPwd(rs.getString("user_password"));
//				memberDto.setUserName(rs.getString("user_name"));
//				memberDto.setEmailId(rs.getString("email_id"));
//				memberDto.setEmailDomain(rs.getString("email_domain"));
//				memberDto.setJoinDate(rs.getString("join_date"));
//				memberDto.setTripCnt(rs.getInt("trip_cnt"));
//			}
//		} finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		return memberDto;
//	}
//
//	@Override
//	public MemberDto getUserInfo(String userId) throws SQLException {
//		// TODO 회원 정보 조회
//		MemberDto memberDto = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder member = new StringBuilder();
//			member.append("select user_id, user_name, email_id, email_domain, join_date, trip_cnt \n");
//			member.append("from members \n");
//			member.append("where user_id = ? \n");
//			pstmt = conn.prepareStatement(member.toString());
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				memberDto = new MemberDto();
//				memberDto.setUserId(rs.getString("user_id"));
//				memberDto.setUserName(rs.getString("user_name"));
//				memberDto.setEmailId(rs.getString("email_id"));
//				memberDto.setEmailDomain(rs.getString("email_domain"));
//				memberDto.setJoinDate(rs.getString("join_date"));
//				memberDto.setTripCnt(rs.getInt("trip_cnt"));
//			}
//		} finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		return memberDto;
//	}
//
//	@Override
//	public void modifyUserInfo(MemberDto memberDto) throws SQLException {
//		// TODO 회원정보 수정
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder modifyMember = new StringBuilder();
//			modifyMember.append("update members \n");
//			modifyMember.append("set user_name = ?, email_id = ?, email_domain = ? \n");
//			modifyMember.append("where user_id = ? \n");
//			pstmt = conn.prepareStatement(modifyMember.toString());
//			pstmt.setString(1, memberDto.getUserName());
//			pstmt.setString(2, memberDto.getEmailId());
//			pstmt.setString(3, memberDto.getEmailDomain());
//			pstmt.setString(4, memberDto.getUserId());
//			pstmt.executeUpdate();
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
//	@Override
//	public void deleteUser(String userId) throws SQLException {
//		// TODO 회원정보 삭제
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder modifyMember = new StringBuilder();
//			modifyMember.append("delete from members \n");
//			modifyMember.append("where user_id = ? \n");
//			pstmt = conn.prepareStatement(modifyMember.toString());
//			pstmt.setString(1, userId);
//			pstmt.executeUpdate();
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
//	@Override
//	public String findPwd(String userId) throws Exception {
//		// TODO 비밀번호 찾기
//		String pwd = "";
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder member = new StringBuilder();
//			member.append("select user_password \n");
//			member.append("from members \n");
//			member.append("where user_id = ? \n");
//			pstmt = conn.prepareStatement(member.toString());
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				pwd = rs.getString("user_password");
//			}
//		} finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		return pwd;
//	}
//
//}
