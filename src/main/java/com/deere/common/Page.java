package com.deere.common;

import java.io.Serializable;

/**
 * 
 * @ClassName: Page
 * @Description: TODO(Here in one sentence description of the role of this method)
 * @author honglin ren renhonglin@johndeere.com
 * @date 2014-11-12 下午5:30:51
 * 
 */
@SuppressWarnings("serial")
public class Page implements Serializable {

	private int pageNo;
	private int pageSize;

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
