package com.ssafy.plan.model.dto;



import java.util.List;

import com.ssafy.trip.model.dto.TripDto;

public class PlanDto {
	//plans
	private int planId; // 계획 id
	private String planName; // 계획 이름
	private String userId; // 계획을 가진 유저
	private int friendListId; // ?? 여행계획 친구 추가, 친구 추가 목록
	private String startDate; // 계획 시작 날짜
	private String endDate; // 계획 마감 날짜
	private List<TripDto> trips; // 계획에 속한 여행지 목록
	private int isOpen; // 여행을 공개 하냐 마냐
	private String content; // 메모 내용
	
	
	
	public PlanDto() {
	}


	public int getPlanId() {
		return planId;
	}


	public void setPlanId(int planId) {
		this.planId = planId;
	}


	public String getPlanName() {
		return planName;
	}


	public void setPlanName(String planName) {
		this.planName = planName;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getFriendListId() {
		return friendListId;
	}


	public void setFriendListId(int friendListId) {
		this.friendListId = friendListId;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public List<TripDto> getTrips() {
		return trips;
	}


	public void setTrips(List<TripDto> trips) {
		this.trips = trips;
	}

	

	public int getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}


	@Override
	public String toString() {
		return "PlanDto [planId=" + planId + ", planName=" + planName + ", userId=" + userId
				+ ", friendListId=" + friendListId + ", startDate=" + startDate + ", endDate=" + endDate + ", trips="
				+ trips + ", isOpen=" + isOpen + ", content=" + content + "]";
	}


	


	
}
