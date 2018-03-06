package com.physics.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import com.physics.dao.base.UserDao;
import com.physics.entity.User;
import com.physics.service.base.UserService;
import com.physics.vm.PageVM;

public class UserServiceImpl implements UserService{

	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	public void saveUser(User user) {
		userDao.saveOrUpdate(user);
	}

	
	public User getUser(String id) {
		return userDao.get(id);
	}

	
	public User getUserByLoginId(String loginId) {
		User user = userDao.getByLoginId(loginId);
		if (user != null) {
			return user;
		}
		return null;
	}

	
	public PageVM<User> getAllPageVMUser(int pageIndex, int pageSize, boolean asc, String sidx) {
		String hql = "from User where state <> ? order by userType,loginId asc";
		List<User> users = userDao.getUsersByHql(hql, new Object[]{"0"});
		PageVM<User> pageVM = new PageVM<User>(pageIndex, users.size(), pageSize, users);
		return pageVM;
	}

	
	public PageVM<User> getTypeUsers(String userType) {
		String hql = "";
		List<User> users = new ArrayList<User>();
		if (userType.equals("0")) {
			hql = "from User where state <> ?  order by userType,loginId asc";
			users = userDao.getUsersByHql(hql, new Object[]{"0"});
		} else {
			hql = "from User where state <> ? and userType = ? order by loginId asc";
			users = userDao.getUsersByHql(hql, new Object[]{"0",userType});
		}
		
		PageVM<User> pageVM = new PageVM<User>(0, users.size(), 120, users);
		return pageVM;
	}

	
	public void deleteUser(String id) {
		userDao.deleteByKey(id);
	}

}
