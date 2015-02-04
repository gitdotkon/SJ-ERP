package com.deere.intercept;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.deere.common.HttpContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionCheckInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private String sessionAttribute;
	private String reloginResult;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> session = ctx.getSession();
		if (session.containsKey(sessionAttribute)) {
			// save http and response object to HttpContext
			HttpContext.setRequest(ServletActionContext.getRequest());
			HttpContext.setResponse(ServletActionContext.getResponse());

			invocation.addPreResultListener(new RequestListener());

			return invocation.invoke();
		}
		System.out.println("Session失效!");

		// session invalid
		ctx.put("tip", "您还没有登陆,请登陆系统后重试 !");
		invocation.getInvocationContext().getValueStack().set("methodName", invocation.getProxy().getActionName());
		return reloginResult;
	}

	/**
	 * @param sessionAttribute the sessionAttribute to set
	 */
	public void setSessionAttribute(String sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}

	/**
	 * @param reloginResult the reloginResult to set
	 */
	public void setReloginResult(String reloginResult) {
		this.reloginResult = reloginResult;
	}

}
