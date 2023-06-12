package com.ssafy.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.user.model.dto.UserDto;

public interface UserService {

//	int idCheck(String userId) throws Exception;
//	UserDto loginUser(String userId, String userPwd) throws Exception;
//	UserDto getUserInfo(String userId) throws Exception;
	
	boolean joinUser(UserDto userDto) throws Exception;
	void modifyUserInfo(UserDto userDto) throws Exception;
	boolean deleteUser(String userId) throws Exception;
	String findPwd(String userId) throws Exception;
	
	public UserDto login(UserDto userDto) throws Exception;
	public UserDto userInfo(String userId) throws Exception;
	public void saveRefreshToken(String userId, String refreshToken) throws Exception;
	public Object getRefreshToken(String userId) throws Exception;
	public void deleRefreshToken(String userId) throws Exception;
	
//	/* Admin */
//	List<UserDto> listUser(Map<String, Object> map) throws Exception;
//	UserDto getUser(String userId) throws Exception;
//	void updateUser(UserDto userDto) throws Exception;
//	void deleteUser(String userId) throws Exception;
}
