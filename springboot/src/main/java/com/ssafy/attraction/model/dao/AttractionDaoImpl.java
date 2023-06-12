package com.ssafy.attraction.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.dto.AttractionDto;
import com.ssafy.util.DBUtil;

public class AttractionDaoImpl implements AttractionDao {
	private static AttractionDao attractionDao = new AttractionDaoImpl();

	private DBUtil dbUtil;

	private AttractionDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}

	public static AttractionDao getAttractionDao() {
		return attractionDao;
	}

	@Override
	public List<AttractionDto> searchAttraction(Map<String, Object> param) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(
					"select content_id, content_type_id, title, addr1, addr2, zipcode, tel, first_image, latitude, longitude \n");
			sql.append("from attraction_info \n");
			int areaCode = (Integer)param.get("areaCode");
			int sigunguCode = (Integer)param.get("sigunguCode");
			int contentTypeId = (Integer)param.get("contentTypeId");
			String keyword = (String) param.get("keyword");
			sql.append("where sido_code = ? ");
			if(sigunguCode > 0)
				sql.append("and gugun_code = ? ");
			if(contentTypeId > 0)
				sql.append("and content_type_id = ? ");
			if(keyword!=null)
				sql.append("and title like concat('%',?,'%')");
			sql.append("\n");
			sql.append("limit ?, ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 1;
			pstmt.setInt(idx++, areaCode);
			if(sigunguCode > 0)
				pstmt.setInt(idx++, sigunguCode);
			if(contentTypeId > 0)
				pstmt.setInt(idx++, contentTypeId);
			if(keyword!=null)
				pstmt.setString(idx++, keyword);
			pstmt.setInt(idx++, (Integer)param.get("start"));
			pstmt.setInt(idx++, (Integer)param.get("listsize"));
			rs = pstmt.executeQuery();
			List<AttractionDto> list = new ArrayList<AttractionDto>();
			while (rs.next()) {
				AttractionDto attractionDto = new AttractionDto();
				attractionDto.setContentId(rs.getInt("content_id"));
				attractionDto.setContentTypeId(rs.getInt("content_type_id"));
				attractionDto.setTitle(rs.getString("title"));
				attractionDto.setAddr1(rs.getString("addr1"));
				attractionDto.setAddr1(rs.getString("addr2"));
				attractionDto.setZipcode(rs.getString("zipcode"));
				attractionDto.setTel(rs.getString("tel"));
				attractionDto.setFirstImage(rs.getString("first_image"));
				attractionDto.setLatitude(rs.getDouble("latitude"));
				attractionDto.setLongitude(rs.getInt("longitude"));
				list.add(attractionDto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
	}

	@Override
	public int getTotalAttractionCount(Map<String, Object> param) throws SQLException {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select count(content_id) \n");
			sql.append("from attraction_info \n");
			int areaCode = (Integer)param.get("areaCode");
			int sigunguCode = (Integer)param.get("sigunguCode");
			int contentTypeId = (Integer)param.get("contentTypeId");
			String keyword = (String) param.get("keyword");
			sql.append("where sido_code = ? ");
			if(sigunguCode > 0)
				sql.append("and gugun_code = ? ");
			if(contentTypeId > 0)
				sql.append("and content_type_id = ? ");
			if(keyword!=null)
				sql.append("and title like concat('%',?,'%')");
			sql.append("\n");
			pstmt = conn.prepareStatement(sql.toString());
			
			int idx = 1;
			pstmt.setInt(idx++, areaCode);
			if(sigunguCode > 0)
				pstmt.setInt(idx++, sigunguCode);
			if(contentTypeId > 0)
				pstmt.setInt(idx++, contentTypeId);
			if(keyword!=null)
				pstmt.setString(idx++, keyword);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		}finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return cnt;
	}

}
