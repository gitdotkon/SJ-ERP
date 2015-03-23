package com.deere.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.model.BOMTree;
import com.deere.model.GenericPart;
import com.deere.model.dto.BOMTreeDto;
import com.deere.model.json.BomJsonDto;
import com.deere.service.BOMService;

public class BomAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 895094121888840587L;

	private String parentCode;

	// private GenericPart parent=null;

	private String machineCode;

	private GenericPart parent = null;
	
	private List<BOMTreeDto> bomTreeList = Collections.EMPTY_LIST;

	private List<BomJsonDto> bomJson = Collections.EMPTY_LIST;

	@Autowired
	private BOMService bomService;

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

	/**
	 * BOM展开
	 * @return
	 * @throws Exception
	 */
	public String expandBom() throws Exception {
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
			// partList.add(child);
			// bomDto.setPartList(partList);
			bomDto.setDosage(bom.getDosage());
			bomTreeList.add(bomDto);
		}
		System.out.println("expend" + bomTreeList.size());
		return "bomExpend";
	}

	/**
	 * 显示BOM列表
	 * @return
	 * {@link #machineCode}
	 */
	public String listBom() {
		GenericPart machine = bomService.getPart(machineCode);
		this.setBomJson(bomService.getJson(machine, null, 0, "0"));
		return SUCCESS;

	}

}
