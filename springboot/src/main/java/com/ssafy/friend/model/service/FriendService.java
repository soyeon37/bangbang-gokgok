package com.ssafy.friend.model.service;

import java.util.List;

import com.ssafy.friend.model.dto.FriendDto;

public interface FriendService {
	List<FriendDto> getFriendList(String userId);
	List<FriendDto> nowFriends(String userId);
	void addFriend(String followerId, String userId);
	void acceptFriend(String followerId, String userId);
	void declineFriend(String followerId, String userId);
}
