package com.ssafy.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.model.dto.AttractionDto;

@Mapper
public interface AttractionMapper {
	List<AttractionDto> searchAttraction(Map<String, Object> param) throws SQLException;
	int getTotalAttractionCount(Map<String, Object> param) throws SQLException;
	List<AttractionDto> topList() throws SQLException;
}
