//package com.ssafy.notice.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.ssafy.notice.model.NoticeDto;
//import com.ssafy.util.DBUtil;
//
//public class NoticeDaoImpl implements NoticeMapper{
//	private static NoticeMapper noticeDao;
//	private DBUtil dbUtil;
//	
//	public NoticeDaoImpl() {
//		dbUtil = DBUtil.getInstance();
//	}
//	
//	public static NoticeMapper getNoticeDao() {
//		if(noticeDao ==null) {
//			noticeDao = new NoticeDaoImpl();
//			
//		}
//		return noticeDao;
//	}
//	
//	@Override
//	public void writeArticle(NoticeDto noticeDto) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("insert into notice (user_id, subject, content) \n");
//			sql.append("values (?, ?, ?)");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, noticeDto.getUserId());
//			pstmt.setString(2, noticeDto.getSubject());
//			pstmt.setString(3, noticeDto.getContent());
//			pstmt.executeUpdate();
//			
//		}finally {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
//	@Override
//	public List<NoticeDto> listArticle(Map<String, Object> param) throws SQLException {
//		List<NoticeDto> list = new ArrayList<NoticeDto>();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new  StringBuilder();
//			sql.append("select article_no, user_id, subject, content, hit, register_time \n");
//			sql.append("from notice \n");
//			String key = (String)param.get("key");
//			String word = (String)param.get("word");
//			
//			if (!key.isEmpty() && !word.isEmpty()) {
//				if("subject".equals(key)) {
//					sql.append("where subject like concat('%', ?, '%') \n");
//					
//				}else {
//					sql.append("where ").append(key).append(" = ? \n");
//				}
//			}
//			sql.append("order by article_no desc \n");
//			sql.append("limit ?, ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			int idx = 0;
//			if (!key.isEmpty() && !word.isEmpty()) {
//				pstmt.setString(++idx, word);
//				
//			}
//			pstmt.setInt(++idx, (Integer)param.get("start"));
//			pstmt.setInt(++idx, (Integer)param.get("listsize"));
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				NoticeDto noticeDto = new NoticeDto();
//				noticeDto.setArticleNo(rs.getInt("article_no"));
//				noticeDto.setUserId(rs.getString("user_id"));
//				noticeDto.setSubject(rs.getString("subject"));
//				noticeDto.setContent(rs.getString("content"));
//				noticeDto.setHit(rs.getInt("hit"));
//				noticeDto.setUploadTime(rs.getString("register_time"));
//				list.add(noticeDto);
//			}
//
//		}finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		
//		return list;
//	}
//
//	@Override
//	public int getTotalArticleCount(Map<String, Object> param) throws SQLException {
//		int cnt = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("select count(article_no) \n");
//			sql.append("from notice \n");
//			String key = (String) param.get("key");
//			String word = (String) param.get("word");
//			if(!key.isEmpty() && !word.isEmpty()) {
//				if("subject".equals(key)) {
//					sql.append("where subject like concat('%', ?, '%') \n");
//				} else {
//					sql.append("where ").append(key).append(" = ? \n");
//				}
//			}
//			pstmt = conn.prepareStatement(sql.toString());
//			if(!key.isEmpty() && !word.isEmpty())
//				pstmt.setString(1, word);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				cnt = rs.getInt(1);
//			}
//		}finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		return cnt;
//	}
//
//	@Override
//	public NoticeDto getArticle(int articleNo) throws SQLException {
//		NoticeDto noticeDto = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("select article_no, user_id, subject, content, hit, register_time \n");
//			sql.append("from notice \n");
//			sql.append("where article_no = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, articleNo);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				noticeDto = new NoticeDto();
//				noticeDto.setArticleNo(rs.getInt("article_no"));
//				noticeDto.setUserId(rs.getString("user_id"));
//				noticeDto.setSubject(rs.getString("subject"));
//				noticeDto.setContent(rs.getString("content"));
//				noticeDto.setHit(rs.getInt("hit"));
//				noticeDto.setUploadTime(rs.getString("register_time"));
//			}
//		}finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		return noticeDto;
//	}
//
//	@Override
//	public void updateHit(int articleNo) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("update notice \n");
//			sql.append("set hit = hit + 1 \n");
//			sql.append("where article_no = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, articleNo);
//			pstmt.executeUpdate();
//		} finally {
//			dbUtil.close(pstmt, conn);
//		}			
//	}
//
//	@Override
//	public void update(NoticeDto noticeDto) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("update notice set subject = ?, content = ? where article_no = ? \n");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, noticeDto.getSubject());
//			pstmt.setString(2, noticeDto.getContent());
//			pstmt.setInt(3, noticeDto.getArticleNo());
//			pstmt.executeUpdate();
//		}finally  {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
//	@Override
//	public void delete(int articleNo) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("delete from notice where article_no = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, articleNo);
//			pstmt.executeUpdate();
//		}finally  {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//}
