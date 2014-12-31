package com.roger.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.roger.dao.UserDao;
import com.roger.entity.User;
import com.roger.service.UserService;
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		return userDao.getUserByName(name);
	}

}
