package com.deere.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: HttpContext
 * @Description: Share http information between action layer and service layer
 * @author Gavin Li ligavin@johndeere.com
 * @date Oct 14, 2014 3:07:09 PM
 * 
 */
public class HttpContext {
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	/**
	 * @return the request
	 */
	public static HttpServletRequest getRequest() {
		return requestLocal.get();
	}

	/**
	 * @param request the request to set
	 */
	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	/**
	 * @return the response
	 */
	public static HttpServletResponse getResponse() {
		return responseLocal.get();
	}

	/**
	 * @param response the response to set
	 */
	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	/**
	 * @return the session
	 */
	public static HttpSession getSession() {
		if (requestLocal.get() == null) {
			return null;
		}
		return requestLocal.get().getSession();
	}

}
