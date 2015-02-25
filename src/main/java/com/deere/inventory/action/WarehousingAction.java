package com.deere.inventory.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Constants;
import com.deere.common.Utils;
import com.deere.exception.GenericException;
import com.deere.inventory.service.InventoryService;
import com.deere.model.dto.PartDto;
import com.deere.service.BOMService;

public class WarehousingAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private BOMService bomService;

	@Autowired
	private InventoryService invService;

	private String warehousingDate;
	private String stockJson;

	private String partType = "";

	private String partCode = "";
	
	private List<PartDto> partList = Collections.EMPTY_LIST;

	public void setWarehousingDate(String warehousingDate) {
		this.warehousingDate = warehousingDate;
	}

	public void setStockJson(String stockJson) {
		this.stockJson = stockJson;
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
	
	
	public List<PartDto> getPartList() {
		return partList;
	}


	public void setPartList(List<PartDto> partList) {
		this.partList = partList;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
//		return super.execute();
		return SUCCESS;
	}
	
	public String warehouseSearch() throws Exception {
		System.out.println("search" + this.getPartType());
		// this.setPartList(bomService.getMachine(partType));
		partCode = new String(partCode.getBytes("ISO-8859-1"), "UTF-8");
		this.setPartList(bomService.searchMachine(partCode, partType, 25));
		System.out.println(this.getPartList().size());
		return SUCCESS;
	}

	public String warehouseEntry() throws GenericException, ParseException {
		@SuppressWarnings("unchecked")
		List<PartDto> stockList = Utils.json2Object(stockJson, PartDto.class);
		// Date entryDate= new Date();
		// entryDate.setTime(Long.valueOf(warehousingDate));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.dfyyMMdd);
		invService.warehouseEntry(stockList, sdf.parse(warehousingDate));
		return SUCCESS;
	}

	public String warehouseOut() throws GenericException {
		@SuppressWarnings("unchecked")
		List<PartDto> stockList = Utils.json2Object(stockJson, PartDto.class);
		Date entryDate = new Date();
		// entryDate.setTime(Long.valueOf(warehousingDate));
//		 List<PartDto> partList =invService.warehouseOut(stockList, entryDate);
		return SUCCESS;
	}
}
