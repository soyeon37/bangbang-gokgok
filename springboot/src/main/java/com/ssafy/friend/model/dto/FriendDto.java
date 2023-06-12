package com.ssafy.friend.model.dto;

public class FriendDto {
	private int friendId;
	private String userId;
	private String followerId;
	private int status;
	
	public FriendDto() {
		// TODO Auto-generated constructor stub
	}

	public FriendDto(int friendId, String userId, String followerId, int status) {
		super();
		this.friendId = friendId;
		this.userId = userId;
		this.followerId = followerId;
		this.status = status;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFollowerId() {
		return followerId;
	}

	public void setFollowerId(String followerId) {
		this.followerId = followerId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "friendDto [friendId=" + friendId + ", userId=" + userId + ", followerId=" + followerId + ", status="
				+ status + "]";
	}
}
