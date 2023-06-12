package com.ssafy.recruit.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.plan.model.dto.PlanParameterDto;
import com.ssafy.recruit.model.dto.RecruitDto;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.util.PageNavigation;

public interface RecruitService {
	void writeArticle(RecruitDto recruitDto) throws Exception;
	List<RecruitDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	RecruitDto getArticle(int articleNo) throws Exception;
	List<PlanDto> planList(Map<String, String> map) throws Exception;
	List<TripDto> viewPlan(int planId) throws Exception;
	boolean registPlan(PlanDto planDto, String userId) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void update(RecruitDto recruitDto) throws Exception;
	void delete(int articleNo, String path)  throws Exception;
	boolean likeCheck(int articleNo, String userId);
	void addLike(int articleNo, String userId);
	void cancelLike(int articleNo, String userId);
}
