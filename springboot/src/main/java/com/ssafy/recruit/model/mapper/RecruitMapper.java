package com.ssafy.recruit.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.dto.FileInfoDto;
import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.recruit.model.dto.RecruitDto;
import com.ssafy.trip.model.dto.TripDto;

@Mapper
public interface RecruitMapper {
	void writeArticle(RecruitDto recruitDto) throws SQLException;
	void writePlan(Map<String, Object> param) throws SQLException;
	void registerFile(RecruitDto recruitDto) throws Exception;
	List<RecruitDto> listArticle(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	RecruitDto getArticle(int articleNo) throws SQLException;
	CommentDto commentList(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	void update(RecruitDto recruitDto) throws SQLException;
	void delete(int articleNo) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo, int category) throws Exception;
	List<PlanDto> planList(List<Integer> planIds) throws Exception;
	List<Integer> getArticlePlanId(int articleNo) throws Exception;
	List<PlanDto> getPlanList(Map<String, Object> map) throws SQLException;
	List<TripDto> viewPlan(int planId) throws SQLException;
	int registPlan(PlanDto planDto) throws SQLException;
	int registTrip(PlanDto planDto) throws SQLException;
	
	void cancelLike(int articleNo, String userId);
	void cancelBoard(int articleNo);
	boolean likeCheck(int articleNo, String userId);
	void addLike(int articleNo, String userId);
	void boardLike(int articleNo);
	
}
