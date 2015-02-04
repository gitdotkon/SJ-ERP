package com.deere.action;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.deere.common.ExcelHandler;
import com.deere.model.exdto.ExInventory;
import com.opensymphony.xwork2.ActionSupport;

public class DataImportAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private String fileFileName;
	List<ExInventory> invList =  Collections.EMPTY_LIST;
	Map<String, Object> session;
	
	
	
	public List<ExInventory> getInvList() {
		return invList;
	}

	public void setInvList(List<ExInventory> invList) {
		this.invList = invList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String preview() throws Exception{
//		StringBuffer errMessage = new StringBuffer();
		if ((fileFileName == null) || "".equals(fileFileName)) {
			addActionError("上传的文件不能为空!");
			return INPUT;
		}
		// Get suffix of file
		String pattern = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length());
		ExcelHandler<ExInventory> invExHand = new ExcelHandler<ExInventory>(ExInventory.class);
		invList = (List<ExInventory>) invExHand.importExcel(file, pattern);
		return SUCCESS;
		
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session=session;
		
	}

	
}
