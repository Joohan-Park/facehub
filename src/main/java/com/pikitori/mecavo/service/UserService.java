package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.UserDao;
import com.pikitori.web.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public List<UserVo> getAllUsers() {
		List<UserVo> list = userDao.getList();
		return list;
	}

	public boolean checkEmail(String user_id) {
		// TODO Auto-generated method stub
		return (userDao.getEmail(user_id) != null);
	}

	public boolean checkNickname(String user_name) {
		return (userDao.getNickname(user_name) != null);
	}

	public UserVo login(UserVo user) {
		return userDao.Login(user);
	}

	public boolean addUser(UserVo user) {
		return userDao.addUser(user);
	}

	public UserVo addF_User(UserVo user) {
		if(userDao.isExistF_User(user) == null){
			userDao.addF_User(user);
		}
		return userDao.isExistF_User(user);
	}
}
