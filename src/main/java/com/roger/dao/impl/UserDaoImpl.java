package com.roger.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.roger.dao.UserDao;
import com.roger.entity.User;
@Component
public class UserDaoImpl implements UserDao {

	@Resource
	SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public User getUserByName(String name) {
		return (User) sqlSession.selectOne("user.getUserByName", name);
	}
}
