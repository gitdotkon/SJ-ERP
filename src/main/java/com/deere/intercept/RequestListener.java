package com.deere.intercept;

import com.deere.common.HttpContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

public class RequestListener implements PreResultListener {

	@Override
	public void beforeResult(ActionInvocation invocation, String resultCode) {
		HttpContext.setRequest(null);
		HttpContext.setResponse(null);
		System.out.println("Destory ThreadLocal object!");
	}

}
