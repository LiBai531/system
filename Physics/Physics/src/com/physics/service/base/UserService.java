package com.physics.service.base;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;

import com.physics.entity.User;
import com.physics.vm.PageVM;

public interface UserService {

	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void saveUser(User user);
	
	/**
	 * 通过id获得用户
	 * @param id
	 * @return
	 */
	public abstract User getUser(String id);
	
	/**
	 * 通过登陆名获得用户
	 * @param loginId
	 * @return
	 */
	public abstract User getUserByLoginId(String loginId);
	
	public abstract PageVM<User> getAllPageVMUser(int pageIndex, int pageSize, boolean asc, String sidx);
	
	/**
	 * 通过用户类型获得用户
	 * @param userType
	 * @return
	 */
	public abstract PageVM<User> getTypeUsers(String userType);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void deleteUser(String id);
	
	
	
}
