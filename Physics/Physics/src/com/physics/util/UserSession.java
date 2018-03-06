package com.physics.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.physics.entity.User;

public class UserSession {

	private HttpSession session;

	public UserSession(HttpSession session){
		this.session = session;
	}
	
	
	public void setCurrentUser(User user){
		session.setAttribute("userSession", user);
		session.setMaxInactiveInterval(60*40);
	}
	
	public User getCurrentUser(){
		User user = (User)session.getAttribute("userSession");
		if(user == null){
			return null;
		}
		return user;
	}
}
