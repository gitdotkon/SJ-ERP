package com.deere.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.model.BOMTree;
import com.deere.model.GenericPart;
import com.deere.model.dto.BOMTreeDto;
import com.deere.service.BOMService;

public class BomExpendAction extends BaseAction {
	@Autowired
	private BOMService bomService;
	
	private GenericPart parent=null;
	
	private String parentCode;
	
	private List<BOMTreeDto> bomTreeList= Collections.EMPTY_LIST;
	
	
	public List<BOMTreeDto> getBomTreeList() {
		return bomTreeList;
	}


	public void setBomTreeList(List<BOMTreeDto> bomTreeList) {
		this.bomTreeList = bomTreeList;
	}


	public String getParentCode() {
		return parentCode;
	}


	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	@Override
	public String execute() throws Exception {
		parent = bomService.getPart(this.getParentCode());
		bomTreeList = new ArrayList<BOMTreeDto>();
		System.out.println(parent.getPartCode());
		List<BOMTree> bomList = bomService.getBOM(parent);
		System.out.println(bomList.size());
		for (BOMTree bom : bomList) {
			BOMTreeDto bomDto = new BOMTreeDto();
			GenericPart child = bom.getChild();
			bomDto.setPartCode(child.getPartCode());
			bomDto.setPartName(child.getPartName());
			bomDto.setPartType(child.getPartType());
//			partList.add(child);
//			bomDto.setPartList(partList);
			bomDto.setDosage(bom.getDosage());
			bomTreeList.add(bomDto);
		}
		System.out.println("expend"+bomTreeList.size());
		return SUCCESS;
	}
}
