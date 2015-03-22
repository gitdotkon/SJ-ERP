package com.deere.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "t_user")
public class User extends GenericModel {
	
	private String userName;
	private String password;
	@Id
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String userName) {
		super();
		this.userName = userName;
	}
	public User(){}
	
	
}
