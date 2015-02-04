package com.deere.action;


import org.springframework.beans.factory.annotation.Autowired;

import com.deere.model.User;
import com.deere.service.UserService;

@SuppressWarnings("serial")
public class LoginAction extends BaseAction{
	private User user;
	
	@Autowired
	private UserService userService;
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}


	public String login() throws Exception{
		
		if(userService.verifyUser(user)){
			session.put("user", user.getUserName());
			return SUCCESS;
		}
		else return "login";
		
		
	}

}
