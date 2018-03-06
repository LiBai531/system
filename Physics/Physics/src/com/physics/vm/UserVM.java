package com.physics.vm;

import com.physics.entity.User;

public class UserVM {

	public User user;
	public String userType;
	public String userState;
	
	public UserVM(User user){
		setUser(user);
		setUserType(user.getUserType());
		setUserState(user.getState());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		if (userType.equals("1")) {
			this.userType = "学生";
		} else if(userType.equals("2")){
			this.userType = "教师";
		}else{
			this.userType = "管理员";
		}
		
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		if (userState.equals("1")) {
			this.userState = "初始用户";
		} else if(userState.equals("2")){
			this.userState = "正常用户";
		}else{
			this.userState = "不可用";
		}
		
	}
	
	
}
