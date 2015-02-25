package com.deere.inventory.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.inventory.service.InventoryService;
import com.deere.model.dto.PartDto;
import com.deere.service.BOMService;

public class InventoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	private InventoryService invService;
	
	 
	@Autowired 
	private BOMService bomService; 


	private String partCode;
	private String partType;

	private  List<PartDto> partList = Collections.EMPTY_LIST;
	
	
	
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



	



	public List<PartDto> getPartList() {
		return partList;
	}



	public void setPartList(List<PartDto> partList) {
		this.partList = partList;
	}



	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// return super.execute();
		partCode = new String(partCode.getBytes("ISO-8859-1"),"UTF-8"); 
		this.setPartList(bomService.searchMachine(partCode,partType,25)); 

		return SUCCESS;
		
	}
	
	public String searchParts() throws Exception{
		this.setPartList(invService.serachInventory(partCode));

		return SUCCESS;
	}

}
