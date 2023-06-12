package com.ssafy.plan.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.plan.model.dto.PlanDto;
import com.ssafy.plan.model.dto.PlanParameterDto;
import com.ssafy.plan.model.service.PlanService;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/plan")
@Api("여행계획 컨트롤러 API V1")
public class PlanController {
	private final Logger logger = LoggerFactory.getLogger(PlanController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private PlanService planService;

	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	
	// 여행 계획
	@ApiOperation(value = "여행계획 내용보기", notes = "여행계획의 정보를 반환한다.", response = List.class)
	@GetMapping("/tripList/{planId}")
	public ResponseEntity<Map<String, Object>> viewPlan(@PathVariable("planId") @ApiParam(value = "얻어올 여행계획 번호.", required = true) int planId) throws Exception {
		logger.info("viewPlan - 호출 : " + planId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
		List<TripDto> trips = planService.viewPlan(planId);
		logger.info("TripDto : " + trips);
		resultMap.put("trips", trips);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;
	} catch (Exception e) {
		logger.error("정보조회 실패 : {}", e);
		resultMap.put("message", e.getMessage());
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "여행계획 목록", notes = "모든 여행계획의 목록을 반환한다.", response = List.class)
	@GetMapping("/planlist/{userId}")
	public ResponseEntity<Map<String, Object>> planList(@PathVariable("userId") @ApiParam(value = "얻어올 사용자 아이디.", required = true) String userId, @RequestParam Map<String, String> map) throws Exception {
		logger.info("listPlan - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		map.put("userId", userId);
		try {
		List<PlanDto> plans = planService.planList(map);
		resultMap.put("plans", plans);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;
	} catch (Exception e) {
		logger.error("정보조회 실패 : {}", e);
		resultMap.put("message", e.getMessage());
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "여행계획 삭제", notes = "번호에 해당하는 여행계획을 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable("planId") @ApiParam(value = "삭제할 여행계획의 번호.", required = true) int planId) throws Exception {
		logger.info("deletePlan - 호출");
		if (planService.deletePlan(planId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "여행계획 추가", notes = "여행계획을 추가한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> addPlan(@ApiParam(value = "추가할 여행계획 정보", required = true) @RequestBody Map<String, Object> plan, HttpSession session) throws Exception {
		logger.info("registPlan - 호출");
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
//		String userId = userDto.getUserId();
//		map.put("planId", plan.get("planId"));
		map.put("planName", plan.get("planName"));
		map.put("userId", plan.get("userId"));
		map.put("startDate", plan.get("startDate"));
		map.put("endDate", plan.get("endDate"));
		map.put("content", plan.get("content"));
//		map.put("memoId", plan.get("memoId"));
//		map.put("friends", plan.get("friends"));
		
		logger.info("서비스 진입 직전");
		if (planService.registTrip(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		logger.info("서비스 진입 직후");
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 여행 계획 수정
	@ApiOperation(value = "여행계획 수정", notes = "수정할 여행계획 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/{planId}")
	public ResponseEntity<String> modifyPlan(@PathVariable("planId") @ApiParam(value = "변경할 여행 계획", required = true)int planId, @ApiParam(value = "수정 내용", required = true) HashMap<String, Object> map) throws Exception {
		logger.info("modifyPlan - 호출");
		if (planService.modifyPlan(planId, map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 여행 계획에 친구 등록
	
	
//	// 공유한 타 여행지 등록
//	@ApiOperation(value = "여행계획 등록", notes = "수정할 여행계획 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PostMapping("/{userId}")
//	public ResponseEntity<String> modifyPlan(@PathVariable("planId") @ApiParam(value = "변경할 여행 계획", required = true)int planId, @ApiParam(value = "수정 내용", required = true) HashMap<String, Object> map) throws Exception {
//		logger.info("modifyPlan - 호출");
//		if (planService.modifyPlan(planId, map)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
	
	
	
	@ApiOperation(value = "여행지 수정", notes = "여행지를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modifyTrip/{planId}")
	public ResponseEntity<String> modifyTrip(@PathVariable("planId") int planId, @ApiParam(value = "수정할 여행지 정보들", required = true) @RequestBody List<TripDto> tripList) throws Exception {
		logger.info("modifyTrip - 호출"+tripList);
		logger.info("modifyTrip - 호출"+planId);
		System.out.println(planId);
		if (planService.modifyTrip(tripList, planId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	
	
	
	@ApiOperation(value = "여행지 순서 변경", notes = "여행지 순서를 변경한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/{planId}/{oldIdx}/{newIdx}/{tripId}")
	public ResponseEntity<String> modifyNo(@PathVariable("tripId") String tripId,@PathVariable("oldIdx") String oldIdx, @PathVariable("newIdx") String newIdx,
			@PathVariable("palnId") String palnId, HttpSession session){
		logger.info("modifyNo - 호출");
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		try {
			if (planService.modifyTripNo(userDto.getUserId(),Integer.parseInt(palnId), Integer.parseInt(oldIdx), Integer.parseInt(newIdx),
					 Integer.parseInt(tripId))) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "여행지 등록", notes = "여행지를 등록한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/{planId}")
	public ResponseEntity<String> registTrip(@PathVariable("planId") @ApiParam(value = "여행지를 등록할 여행 계획", required = true)int planId, @ApiParam(value = "추가할 여행지", required = true) String contentid, HttpSession session) throws Exception {
		logger.info("registTrip - 호출");
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String userId = userDto.getUserId();
		map.put("tripNo", planService.getTripCnt(userId, planId) + 1);
		map.put("userId", userId);
		map.put("planId", planId);
		map.put("contentid", contentid);
		if (planService.registTrip(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	
	// 여행지 삭제
	@ApiOperation(value = "여행지 삭제", notes = "해당하는 여행지를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{planId}/{tripId}/{tripNo}")
	public ResponseEntity<String> deleteTrip(@PathVariable("tripId") @ApiParam(value = "삭제할 여행지 인덱스.", required = true) int tripId, @PathVariable("tripNo") @ApiParam(value = "삭제할 여행지 순번.", required = true) int tripNo, @PathVariable("planId") @ApiParam(value = "삭제할 여행지 순번.", required = true) int planId,HttpSession session) throws Exception {
		logger.info("deleteTrip - 호출");
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		if (planService.deleteTrip(tripId, tripNo, planId, userDto.getUserId())) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "최근 여행 계획", notes = "최근 여행계획의 정보를 반환한다", response = Map.class)
	@ResponseBody
	@GetMapping("/top")
	public ResponseEntity<Map<String, Object>> topList() throws Exception {
		logger.info("topList - 호출 : ");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
		List<PlanDto> plans = planService.topList();
		logger.info("PlanDto : " + plans);
		resultMap.put("plans", plans);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;
	} catch (Exception e) {
		logger.error("정보조회 실패 : {}", e);
		resultMap.put("message", e.getMessage());
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
//	@GetMapping("/delete/{planId}/{planNo}")
//	@ResponseBody
//	private ModelAndView deleteTrip(@PathVariable("planId") String planId, @PathVariable("planNo") String planNo,
//			HttpSession session) {
//		// TODO 일정 삭제하기
//		ModelAndView mav = new ModelAndView();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		try {
//			int tripCnt = planService.deletePlan(Integer.parseInt(planId), Integer.parseInt(planNo),
//					userDto.getUserId());
////			if (tripCnt >= 0) {
////				userDto.setTripCnt(tripCnt);
////				session.setAttribute("userinfo", userDto);
////			}
//			return planList(session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
	
	
	
//	@PostMapping("/registPlan")
//	private ModelAndView registTrip(String contentid, HttpSession session) {
//		// TODO 일정 등록하기
//		ModelAndView mav = new ModelAndView();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		try {
//			System.out.println(userDto.getEmailDomain());
//			PlanDto planDto = new PlanDto();
//			planDto.setPlanNo(userDto.getTripCnt() + 1);
//			planDto.setUserId(userDto.getUserId());
//			planDto.setContentId(Integer.parseInt(contentid));
////			tripDto.setMemo(request.getParameter("memo"));
//			int tripCnt = planService.registPlan(planDto);
//
////			if (tripCnt >= 0) {
////				userDto.setTripCnt(tripCnt);
////				session.setAttribute("userinfo", userDto);
////			}
//			return planList(session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
	
//	@GetMapping("/modifyNo/{oldIdx}/{newIdx}/{palnId}")
//	@ResponseBody
//	private ModelAndView modifyNo(@PathVariable("oldIdx") String oldIdx, @PathVariable("newIdx") String newIdx,
//			@PathVariable("palnId") String palnId, HttpSession session) {
//		// TODO 일정 순서 변경하기
//		ModelAndView mav = new ModelAndView();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		try {
//			planService.modifyPlanNo(userDto.getUserId(), Integer.parseInt(oldIdx), Integer.parseInt(newIdx),
//					Integer.parseInt(palnId));
//			return planList(session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
	
//	// 추가해야 함
//	@PostMapping("/modifyMemo")
//	private String modifyMemo(HttpServletRequest request, HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//
//	/*
//	 * tripcnt 수정 해야 함!!!
//	 */
//	
//

	
	
	

	
	
//	@PostMapping("/planList")
//	private ModelAndView planList(HttpSession session) {
//		// TODO 일정 리스트 가져오기
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		ModelAndView mav = new ModelAndView();
//		try {
//			List<PlanDto> list = planService.planList(userDto.getUserId());
//			mav.addObject("planList", list);
//			mav.setViewName("/plan/index");
//			return mav;
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
}
