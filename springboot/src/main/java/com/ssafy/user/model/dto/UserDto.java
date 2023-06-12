package com.ssafy.user.model.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {

	private String userId;
	private String userOriginId;
	private String userName;
	private String userPwd;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private List<Integer> planIdList;
	private int firendId;
	private String type;
	private Boolean isAdmin;

	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public List<Integer> getPlanIdList() {
		return planIdList;
	}

	public void setPlanIdList(List<Integer> planIdList) {
		this.planIdList = planIdList;
	}

	public int getFirendId() {
		return firendId;
	}

	public void setFirendId(int firendId) {
		this.firendId = firendId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	
	public String getUserOriginId() {
		return userOriginId;
	}

	public void setUserOriginId(String userOriginId) {
		this.userOriginId = userOriginId;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userOriginId=" + userOriginId + ", userName=" + userName + ", userPwd="
				+ userPwd + ", emailId=" + emailId + ", emailDomain=" + emailDomain + ", joinDate=" + joinDate
				+ ", planIdList=" + planIdList + ", firendId=" + firendId + ", type=" + type + ", isAdmin=" + isAdmin
				+ "]";
	}

	
	
	
}
