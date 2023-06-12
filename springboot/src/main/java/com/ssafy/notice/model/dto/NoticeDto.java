package com.ssafy.notice.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "NoticeDto : 공지사항정보", description = "공지사항의 상세 정보를 나타낸다.")
public class NoticeDto {
	@ApiModelProperty(value = "공지사항 번호")
	private int noticeId;
	@ApiModelProperty(value = "공지사항 작성 아이디")
	private String userId;
	@ApiModelProperty(value = "공지사항 제목")
	private String title;
	@ApiModelProperty(value = "공지사항 내용")
	private String content;
	@ApiModelProperty(value = "공지사항 조회수")
	private int hit;
	@ApiModelProperty(value = "공지사항 등록 일자")
	private String registerTime;
	@ApiModelProperty(value = "게시판 카테고리")
	private int category;
	@ApiModelProperty(value = "공지사항 좋아요 개수")
	private int likes;
	
	public NoticeDto() {
		// TODO Auto-generated constructor stub
	}

	public NoticeDto(int noticeId, String userId, String title, String content, int hit, String registerTime,
			int category, int likes) {
		super();
		this.noticeId = noticeId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.registerTime = registerTime;
		this.category = category;
		this.likes = likes;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "NoticeDto [noticeId=" + noticeId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", hit=" + hit + ", registerTime=" + registerTime + ", category=" + category + ", likes=" + likes
				+ "]";
	}
}
