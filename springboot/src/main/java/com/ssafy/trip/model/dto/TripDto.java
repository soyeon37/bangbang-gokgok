package com.ssafy.trip.model.dto;


import java.util.List;

import com.ssafy.attraction.model.dto.AttractionDto;

public class TripDto extends AttractionDto {
	private int tripId; // 여행 인덱스
	private int planId; // 여행계획 인덱스
	private int tripNo; // 여행 순서
	private int contentId; // 여행 내용
	
	public TripDto() {}

	public TripDto(int tripId, int planId, int tripNo, int contentId) {
		super();
		this.tripId = tripId;
		this.planId = planId;
		this.tripNo = tripNo;
		this.contentId = contentId;
	}



	



	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getTripNo() {
		return tripNo;
	}

	public void setTripNo(int tripNo) {
		this.tripNo = tripNo;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	

}
