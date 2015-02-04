package com.deere.inventory.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.inventory.service.InventoryService;
import com.deere.model.dto.PartDto;

public class InventoryAction extends BaseAction {
	@Autowired
	private InventoryService invService;

	private String partCode;

	private  List<PartDto> invList = Collections.EMPTY_LIST;
	
	
	
	public String getPartCode() {
		return partCode;
	}



	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}




	public List<PartDto> getInvList() {
		return invList;
	}



	public void setInvList(List<PartDto> invList) {
		this.invList = invList;
	}



	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// return super.execute();
		
		this.setInvList(invService.serachInventory(partCode));

		return SUCCESS;
	}

}
