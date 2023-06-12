package com.ssafy.friend.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.friend.model.dto.FriendDto;

@Mapper
public interface FriendMapper {
	List<FriendDto> getFriendList(String userId);
	List<FriendDto> nowFriends(String userId);
	void addFriend(String followerId, String userId);
	void acceptFriend(String followerId, String userId);
	void declineFriend(String followerId, String userId);

}
