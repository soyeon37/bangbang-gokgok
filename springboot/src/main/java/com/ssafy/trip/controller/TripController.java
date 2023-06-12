//package com.ssafy.trip.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.ssafy.trip.model.dto.TripDto;
//import com.ssafy.trip.model.service.TripService;
//import com.ssafy.user.model.dto.UserDto;
//
//
//@Controller
//@RequestMapping("/trip")
//public class TripController  {
//	private final Logger logger = LoggerFactory.getLogger(TripController.class);
//
//	private TripService tripService;
//	
//	@GetMapping("/index")
//	public ModelAndView index(HttpSession session) {
//		return tripList(session);
//	}
//	
//
////	protected void doGet(HttpServletRequest request, HttpServletResponse response)
////			throws ServletException, IOException {
////		String action = request.getParameter("action");
////
////		String path = "";
////		if ("mvlist".equals(action)) {
////			path = tripList(request, response);
////			forward(request, response, path);
////		} else if ("regist".equals(action)) {
////			path = registTrip(request, response);
////			redirect(request, response, path);
////		} else if ("delete".equals(action)) {
////			path = deleteTrip(request, response);
////			forward(request, response, path);
////		} else if ("modifyNo".equals(action)) {
////			path = modifyNo(request, response);
////			forward(request, response, path);
////		} else if ("modifyMemo".equals(action)) {
////			path = modifyMemo(request, response);
////			redirect(request, response, path);
////		}
////	}
//
////	protected void doPost(HttpServletRequest request, HttpServletResponse response)
////			throws ServletException, IOException {
////		request.setCharacterEncoding("utf-8");
////		doGet(request, response);
////	}
////
////	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
////			throws ServletException, IOException {
////		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
////		dispatcher.forward(request, response);
////	}
////
////	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
////		response.sendRedirect(request.getContextPath() + path);
////	}
//
//	public TripController(TripService tripService) {
//		super();
//		this.tripService = tripService;
//	}
//
//	
//	// 추가해야 함
//	@PostMapping("/modifyMemo")
//	private String modifyMemo(HttpServletRequest request, HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//	@GetMapping("/modifyNo/{oldIdx}/{newIdx}/{tripId}")
//	@ResponseBody
//	private ModelAndView modifyNo(@PathVariable("oldIdx") String oldIdx, @PathVariable("newIdx") String newIdx, @PathVariable("tripId") String tripId, HttpSession session) {
//		// TODO 일정 순서 변경하기
//		ModelAndView mav = new ModelAndView();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		try {
//			tripService.modifyTripNo(userDto.getUserId(), Integer.parseInt(oldIdx), Integer.parseInt(newIdx), Integer.parseInt(tripId));
//			return tripList(session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
//
//	/*
//	 * tripcnt 수정 해야 함!!!
//	 */
//	@GetMapping("/delete/{tripId}/{tripNo}")
//	@ResponseBody
//	private ModelAndView deleteTrip(@PathVariable("tripId") String tripId, @PathVariable("tripNo") String tripNo, HttpSession session) {
//		// TODO 일정 삭제하기
//		ModelAndView mav = new ModelAndView();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		try {
//			int tripCnt = tripService.deleteTrip(Integer.parseInt(tripId), Integer.parseInt(tripNo), userDto.getUserId());
//			if (tripCnt >= 0) {
//				userDto.setTripCnt(tripCnt);
//				session.setAttribute("userinfo", userDto);
//			}
//			return tripList(session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
//
//	@PostMapping("/registTrip")
//	private ModelAndView registTrip(String contentid, HttpSession session) {
//		// TODO 일정 등록하기
//		ModelAndView mav = new ModelAndView();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		try {
//			System.out.println(userDto.getEmailDomain());
//			TripDto tripDto = new TripDto();
//			tripDto.setTripNo(userDto.getTripCnt() + 1);
//			tripDto.setUserId(userDto.getUserId());
//			tripDto.setContentId(Integer.parseInt(contentid));
////			tripDto.setMemo(request.getParameter("memo"));
//			int tripCnt = tripService.registTrip(tripDto);
//
//			if (tripCnt >= 0) {
//				userDto.setTripCnt(tripCnt);
//				session.setAttribute("userinfo", userDto);
//			}
//			return tripList(session);
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
//
//	@PostMapping("/tripList")
//	private ModelAndView tripList(HttpSession session) {
//		// TODO 일정 리스트 가져오기
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
//		ModelAndView mav = new ModelAndView();
//		try {
//			List<TripDto> list = tripService.tripList(userDto.getUserId());
//			mav.addObject("tripList", list);
//			mav.setViewName("/trip/index");
//			return mav;
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "오류가 발생했습니다.");
//			mav.setViewName("/error/error");
//			return mav;
//		}
//	}
//
//}
