package com.ssafy.friend.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.friend.model.dto.FriendDto;
import com.ssafy.friend.model.mapper.FriendMapper;

@Service
public class FriendServiceImpl implements FriendService {
	
	private FriendMapper friendMapper;
	
	public FriendServiceImpl(FriendMapper friendMapper) {
		super();
		this.friendMapper = friendMapper;
	}

	@Override
	public List<FriendDto> getFriendList(String userId) {
		return friendMapper.getFriendList(userId);
	}
	
	@Override
	public List<FriendDto> nowFriends(String userId) {
		return friendMapper.nowFriends(userId);
	}

	@Override
	public void addFriend(String followerId, String userId) {
		friendMapper.addFriend(followerId, userId);
	}

	@Override
	public void acceptFriend(String followerId, String userId) {
		friendMapper.acceptFriend(followerId, userId);
	}

	@Override
	public void declineFriend(String followerId, String userId) {
		friendMapper.declineFriend(followerId, userId);
	}
}
