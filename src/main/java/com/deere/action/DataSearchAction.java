package com.deere.action;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.deere.model.GenericPart;
import com.deere.service.BOMService;
import com.opensymphony.xwork2.ActionSupport;

public class DataSearchAction extends BaseAction {
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BOMService bomService;
	
	private List<GenericPart> partList = Collections.EMPTY_LIST;
	
	private String partType="";
	
	private String partCode="";
	
	
	
	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public List<GenericPart> getPartList() {
		return partList;
	}

	public void setPartList(List<GenericPart> partList) {
		this.partList = partList;
	}

	@Override
	public String execute() throws Exception {
		
		System.out.println("search" +this.getPartType());
//		this.setPartList(bomService.getMachine(partType));
		this.setPartList(bomService.searchMachine(partCode,partType));
		System.out.println(this.getPartList().size());
		return SUCCESS;
	}
	
	
	
	

}
