package com.deere.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.model.User;
import com.deere.service.UserService;

@SuppressWarnings("serial")
public class LoginAction extends BaseAction {

	private User user;
	@Autowired
	private UserService userService;
	
	private static final String LOGIN = "login";

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String login() throws Exception {
		if (null != user && userService.verifyUser(user)) {
			session.put("user", user.getUserName());
			return SUCCESS;
		} else
			return LOGIN;
	}
}
