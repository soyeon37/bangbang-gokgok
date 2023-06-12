package com.ssafy.user.model.service;


import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ssafy.user.model.dto.UserDto;
import com.ssafy.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	@Autowired
	private SqlSession sqlSession;
	
//	public UserServiceImpl(UserMapper userMapper) {
//		super();
//		this.userMapper = userMapper;
//	}
//
//	@Override
//	public int idCheck(String userId) throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//
//	@Override
//	public UserDto loginUser(String userId, String userPwd) throws Exception {
//		UserDto memberDto = userMapper.loginUser(userId);
//		if (memberDto == null)
//			return null;
//		if (BCrypt.checkpw(userPwd, memberDto.getUserPwd()))
//			return memberDto;
//		else
//			return null;
//	}
//
//	@Override
//	public UserDto getUserInfo(String userId) throws Exception {
//		// TODO 회원정보 조회
//		return userMapper.getUserInfo(userId);
//	}
	
	@Override
	public boolean joinUser(UserDto userDto) throws Exception {
		// TODO 회원가입
		String password = userDto.getUserPwd();
		System.out.println(userDto);
		// 디비에 저장할 비밀번호 암호화
//		String encrypted = BCrypt.hashpw(password, BCrypt.gensalt());
//		userDto.setUserPwd(encrypted);
		System.out.println(userDto.getUserId());
		return sqlSession.getMapper(UserMapper.class).joinUser(userDto) == 1;
	}

	@Override
	public void modifyUserInfo(UserDto userDto) throws Exception {
		// TODO 회원정보 수정
		sqlSession.getMapper(UserMapper.class).modifyUserInfo(userDto);
	}

	@Override
	public boolean deleteUser(String userId) throws Exception {
		// TODO 회원정보 삭제
		return sqlSession.getMapper(UserMapper.class).deleteUser(userId) == 1;
	}

	@Override
	public String findPwd(String userId) throws Exception {
		// TODO 비밀번호 찾기
		return userMapper.findPwd(userId);
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if (userDto.getUserId() == null || userDto.getUserPwd() == null)
			return null;
		return sqlSession.getMapper(UserMapper.class).login(userDto);
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
	}
//	/* ADMIN */
//	@Override
//	public List<MemberDto> listMember(Map<String, Object> map) throws Exception {
//		return memberMapper.listMember(map);
//	}
//
//	@Override
//	public MemberDto getMember(String userId) throws Exception {
//		return memberMapper.getMember(userId);
//	}
//
//	@Override
//	public void updateMember(MemberDto memberDto) throws Exception {
//		memberMapper.updateMember(memberDto);
//		
//	}
//
//	@Override
//	public void deleteMember(String userId) throws Exception {
//		memberMapper.deleteMember(userId);	
//		
//	}

}
