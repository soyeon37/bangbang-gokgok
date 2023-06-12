package com.ssafy.recruit.model.dto;

import java.util.List;

import com.ssafy.board.model.dto.FileInfoDto;
import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.plan.model.dto.PlanDto;

public class RecruitDto {
	private int articleId;
	private String userId;
	private String title;
	private String content;
	private int hit;
	private String registerTime;
	private int planId;
	private int likes;
	private String type; 
	private List<FileInfoDto> fileInfos;
	private List<PlanDto> plans;
	private List<Integer> planIds;
	private List<CommentDto> comments;
	
	
	public RecruitDto() {
		// TODO Auto-generated constructor stub
	}

	public RecruitDto(int articleId, String userId, String title, String content, int hit, String registerTime,
			int planId, int likes, String type, List<FileInfoDto> fileInfos, List<PlanDto> plans, List<Integer> planIds,
			List<CommentDto> comments) {
		super();
		this.articleId = articleId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.registerTime = registerTime;
		this.planId = planId;
		this.likes = likes;
		this.type = type;
		this.fileInfos = fileInfos;
		this.plans = plans;
		this.planIds = planIds;
		this.comments = comments;
	}






	public String getType() {
		return type;
	}






	public void setType(String type) {
		this.type = type;
	}






	public int getArticleId() {
		return articleId;
	}


	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getHit() {
		return hit;
	}


	public void setHit(int hit) {
		this.hit = hit;
	}


	public String getRegisterTime() {
		return registerTime;
	}


	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}


	public int getPlanId() {
		return planId;
	}


	public void setPlanId(int planId) {
		this.planId = planId;
	}


	public int getLikes() {
		return likes;
	}


	public void setLikes(int likes) {
		this.likes = likes;
	}


	public List<FileInfoDto> getFileInfos() {
		return fileInfos;
	}


	public void setFileInfos(List<FileInfoDto> fileInfos) {
		this.fileInfos = fileInfos;
	}


	public List<PlanDto> getPlans() {
		return plans;
	}


	public void setPlans(List<PlanDto> plans) {
		this.plans = plans;
	}


	public List<CommentDto> getComments() {
		return comments;
	}


	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}


	public List<Integer> getPlanIds() {
		return planIds;
	}


	public void setPlanIds(List<Integer> planIds) {
		this.planIds = planIds;
	}






	@Override
	public String toString() {
		return "RecruitDto [articleId=" + articleId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", hit=" + hit + ", registerTime=" + registerTime + ", planId=" + planId + ", likes=" + likes
				+ ", type=" + type + ", fileInfos=" + fileInfos + ", plans=" + plans + ", planIds=" + planIds
				+ ", comments=" + comments + "]";
	}

	


	
}
