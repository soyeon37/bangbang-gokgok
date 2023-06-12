package com.ssafy.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user.model.dto.UserDto;

@Mapper
public interface UserMapper {
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
//	int idCheck(String userId) throws SQLException;
	int joinUser(UserDto userDto) throws SQLException;
//	UserDto loginUser(String userId) throws SQLException;
//	UserDto getUserInfo(String userId) throws SQLException;
	void modifyUserInfo(UserDto userDto) throws SQLException;
	void deleteNotice(String userId) throws SQLException;
	void deleteBoard(String userId) throws SQLException;
	int deleteUser(String userId) throws SQLException;
	String findPwd(String userId) throws Exception;
	
//	/* Admin */
//	List<MemberDto> listMember(Map<String, Object> map) throws SQLException;
//	MemberDto getMember(String userId) throws SQLException;
//	void updateMember(MemberDto memberDto) throws SQLException;
//	void deleteMember(String userId) throws SQLException;
}
