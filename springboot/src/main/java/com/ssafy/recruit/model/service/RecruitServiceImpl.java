package com.ssafy.recruit.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.model.dto.FileInfoDto;
import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.plan.model.dto.PlanParameterDto;
import com.ssafy.plan.model.mapper.PlanMapper;
import com.ssafy.recruit.model.dto.RecruitDto;
import com.ssafy.recruit.model.mapper.RecruitMapper;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.util.Algorithm;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class RecruitServiceImpl implements RecruitService {
	private RecruitMapper recruitMapper;
	@Autowired
	private SqlSession sqlSession;
	
	public RecruitServiceImpl(RecruitMapper recruitMapper) {
		super();
		this.recruitMapper = recruitMapper;
	}

	@Override
	public void writeArticle(RecruitDto recruitDto) throws Exception {
		sqlSession.getMapper(RecruitMapper.class).writeArticle(recruitDto);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleId", recruitDto.getArticleId());
		List<Integer> planIdList = recruitDto.getPlanIds();
		for (int i = 0; i < planIdList.size(); i++) {
			map.put("planId", planIdList.get(i));
			sqlSession.getMapper(RecruitMapper.class).writePlan(map);
			map.remove("planId");
		}
		
		List<FileInfoDto> fileInfos = recruitDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			sqlSession.getMapper(RecruitMapper.class).registerFile(recruitDto);
		}
	}

	@Override
	public List<RecruitDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		
		List<RecruitDto> list = recruitMapper.listArticle(param);
		int option = Integer.parseInt(map.getOrDefault("search-option", "1"));
		switch (option) {
		// 지난글 순 정렬
		case 2:
			return Algorithm.sortBoardByRegistR(list);
		// 인기 글 순 정렬
		case 3:
			return Algorithm.sortBoardHitR(list);
		default:
			return list;
		}
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
		param.put("key", key.isEmpty() ? "" : key);
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int totalCount = recruitMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public RecruitDto getArticle(int articleNo) throws Exception {
		
		// recruit_plan_list테이블에서 해당하는 plan_id 가져오기
		RecruitDto res = new RecruitDto();
		System.out.println(articleNo);
		List<Integer> planIds = recruitMapper.getArticlePlanId(articleNo);
		System.out.println(planIds);
		List<PlanDto> planlist = new ArrayList<PlanDto>();
		planlist = sqlSession.getMapper(RecruitMapper.class).planList(planIds);
//		for (int i = 0; i < list.size(); i++) {
//			planlist.add(sqlSession.getMapper(RecruitMapper.class).planList(list.get(i)));
//		}
		System.out.println(planlist);
		res = sqlSession.getMapper(RecruitMapper.class).getArticle(articleNo);
		res.setPlans(planlist);
		res.setPlanIds(planIds);
		res.setArticleId(articleNo);
		return res;
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		recruitMapper.updateHit(articleNo);
		
	}

	@Override
	public void update(RecruitDto recruitDto) throws Exception {
		recruitMapper.update(recruitDto);
		
	}

	@Override
	public void delete(int articleNo, String path) throws Exception {
		List<FileInfoDto> fileList = recruitMapper.fileInfoList(articleNo, 3);
//		어차피 xml단에서 무조건 category 1만 줄거니까 상관 없음
		recruitMapper.deleteFile(articleNo);
		recruitMapper.delete(articleNo);
		for(FileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}

	@Override
	public List<PlanDto> planList(Map<String, String> map) throws Exception {
		// TODO 일정 리스트 가져오기
		Map<String, Object> param = new HashMap<>();
		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("userId", map.get("userId"));
		List<PlanDto> list = sqlSession.getMapper(recruitMapper.getClass()).getPlanList(param);
		int option = Integer.parseInt(map.getOrDefault("search-option", "1"));
		switch (option) {
//		// 지난글 순 정렬
//		case 2:
//			return Algorithm.sortBoardByRegistP(list);
//		// 인기 글 순 정렬
//		case 3:
//			return Algorithm.sortBoardHitP(list);
		default:
			return list;
		}
	}

	@Override
	public List<TripDto> viewPlan(int planId) throws Exception {
		// TODO 여행 정보 가져오기
		return recruitMapper.viewPlan(planId);
	}

	@Override
	public boolean registPlan(PlanDto planDto, String userId) throws Exception {
		// TODO 타 여행 정보 추가하기
		planDto.setUserId(userId);
		planDto.setTrips(recruitMapper.viewPlan(planDto.getPlanId()));
		planDto.setPlanId(recruitMapper.registPlan(planDto) + 1);
		return recruitMapper.registTrip(planDto) == 1;
	}
	
	@Override
	public boolean likeCheck(int articleNo, String userId) {
		return recruitMapper.likeCheck(articleNo, userId);
		
	}

	@Override
	@Transactional
	public void addLike(int articleNo, String userId) {
		recruitMapper.addLike(articleNo, userId);
		recruitMapper.boardLike(articleNo);
	}
	
	@Override
	@Transactional
	public void cancelLike(int articleNo, String userId) {
		recruitMapper.cancelLike(articleNo, userId);
		recruitMapper.cancelBoard(articleNo);
	}

	

}
