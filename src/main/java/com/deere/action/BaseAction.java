package com.deere.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware {
	
	/**
	 * 
	 */
	Map<String, Object> session;
	private String jsonData;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	

}
