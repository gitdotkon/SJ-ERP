package com.deere.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware {
	
	/**
	 * 
	 */
	Map<String, Object> session;
	
	protected String jsonData;
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	
	

}
