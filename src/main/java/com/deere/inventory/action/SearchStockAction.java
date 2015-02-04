package com.deere.inventory.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.model.GenericPart;
import com.deere.model.dto.PartDto;
import com.deere.service.BOMService;

public class SearchStockAction extends BaseAction {
	
	@Autowired
	private BOMService bomService;
	
	private List<PartDto> partList = Collections.EMPTY_LIST;
	
	private String partType="";
	
	private String partCode="";
	
	
	


	public List<PartDto> getPartList() {
		return partList;
	}


	public void setPartList(List<PartDto> partList) {
		this.partList = partList;
	}


	public String getPartType() {
		return partType;
	}


	public void setPartType(String partType) {
		this.partType = partType;
	}


	public String getPartCode() {
		return partCode;
	}


	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("search" +this.getPartType());
//		this.setPartList(bomService.getMachine(partType));
		partCode = new String(partCode.getBytes("ISO-8859-1"),"UTF-8");
		this.setPartList(bomService.searchMachine(partCode,partType,25));
		System.out.println(this.getPartList().size());
		return SUCCESS;
	}
}
