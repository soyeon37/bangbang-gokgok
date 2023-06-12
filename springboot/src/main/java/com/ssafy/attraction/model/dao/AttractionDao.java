package com.ssafy.attraction.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.dto.AttractionDto;

public interface AttractionDao {
	List<AttractionDto> searchAttraction(Map<String, Object> param) throws SQLException;
	int getTotalAttractionCount(Map<String, Object> param) throws SQLException;
}
