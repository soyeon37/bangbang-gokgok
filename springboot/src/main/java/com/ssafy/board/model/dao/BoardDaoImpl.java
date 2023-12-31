//package com.ssafy.board.model.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.ssafy.board.model.dto.BoardDto;
//import com.ssafy.util.DBUtil;
//
//public class BoardDaoImpl implements BoardDao{
//	private static BoardDao boardDao;
//	private DBUtil dbUtil;
//	
//	public BoardDaoImpl() {
//		dbUtil = DBUtil.getInstance();
//	}
//	
//	public static BoardDao getBoarDao() {
//		if(boardDao ==null) {
//			boardDao = new BoardDaoImpl();
//			
//		}
//		return boardDao;
//	}
//	
//	@Override
//	public void writeArticle(BoardDto boardDto) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("insert into board (user_id, subject, content) \n");
////			sql.append("insert into board (subject, content) \n");
//			sql.append("values (?, ?, ?)");
////			sql.append("values (?, ?)");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, boardDto.getUserId());
//			pstmt.setString(2, boardDto.getSubject());
//			pstmt.setString(3, boardDto.getContent());
//			pstmt.executeUpdate();
//			
//		}finally {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//
//	@Override
//	public List<BoardDto> listArticle(Map<String, Object> param) throws SQLException {
//		List<BoardDto> list = new ArrayList<BoardDto>();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new  StringBuilder();
//			sql.append("select article_no, user_id, subject, content, hit, register_time \n");
//			sql.append("from board \n");
//			String key = (String)param.get("key");
//			String word = (String)param.get("word");
////			System.out.println("key : " + key + ", word : " + word); // 출력
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
////				System.out.println("word : " + word);
//				pstmt.setString(++idx, word);
//				
//			}
//			pstmt.setInt(++idx, (Integer)param.get("start"));
//			pstmt.setInt(++idx, (Integer)param.get("listsize"));
//			System.out.println(sql.toString());
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				BoardDto boardDto = new BoardDto();
//				boardDto.setArticleNo(rs.getInt("article_no"));
//				boardDto.setUserId(rs.getString("user_id"));
//				boardDto.setSubject(rs.getString("subject"));
//				boardDto.setContent(rs.getString("content"));
//				boardDto.setHit(rs.getInt("hit"));
//				boardDto.setUploadTime(rs.getString("register_time"));
//				list.add(boardDto);
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
//			sql.append("from board \n");
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
//	public BoardDto getArticle(int articleNo) throws SQLException {
//		BoardDto boardDto = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("select article_no, user_id, subject, content, hit, register_time \n");
//			sql.append("from board \n");
//			sql.append("where article_no = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, articleNo);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				boardDto = new BoardDto();
//				boardDto.setArticleNo(rs.getInt("article_no"));
//				boardDto.setUserId(rs.getString("user_id"));
//				boardDto.setSubject(rs.getString("subject"));
//				boardDto.setContent(rs.getString("content"));
//				boardDto.setHit(rs.getInt("hit"));
//				boardDto.setUploadTime(rs.getString("register_time"));
//			}
//		}finally {
//			dbUtil.close(rs, pstmt, conn);
//		}
//		return boardDto;
//	}
//
//	@Override
//	public void updateHit(int articleNo) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("update board \n");
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
//	public void update(BoardDto boardDto) throws SQLException {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dbUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("update board set subject = ?, content = ? where article_no = ? \n");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, boardDto.getSubject());
//			pstmt.setString(2, boardDto.getContent());
//			pstmt.setInt(3, boardDto.getArticleNo());
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
//			sql.append("delete from board where article_no = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, articleNo);
//			pstmt.executeUpdate();
//		}finally  {
//			dbUtil.close(pstmt, conn);
//		}
//	}
//}
