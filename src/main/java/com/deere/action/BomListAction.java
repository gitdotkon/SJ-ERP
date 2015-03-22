package com.deere.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.model.GenericPart;
import com.deere.model.json.BomJsonDto;
import com.deere.service.BOMService;
@Deprecated
public class BomListAction extends BaseAction{

	/**
	 * 
	 */
	
	@Autowired
	private BOMService bomService;
	
	
//	private GenericPart parent=null;
	
	private String machineCode;
	
	
	
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public List<BomJsonDto> getBomJson() {
		return bomJson;
	}
	public void setBomJson(List<BomJsonDto> bomJson) {
		this.bomJson = bomJson;
	}
	private List<BomJsonDto> bomJson= Collections.EMPTY_LIST;
	
	
	private static final long serialVersionUID = 1L;
	@Override
	public String execute(){
		GenericPart machine = bomService.getPart(machineCode);
		this.setBomJson(bomService.getJson(machine, null, 0, "0"));
		return SUCCESS;
		
	}
	
}
