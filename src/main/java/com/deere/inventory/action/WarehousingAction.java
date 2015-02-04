package com.deere.inventory.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Constants;
import com.deere.common.Utils;
import com.deere.exception.GenericException;
import com.deere.inventory.service.InventoryService;
import com.deere.model.dto.PartDto;

public class WarehousingAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private InventoryService invService;
	
	private String warehousingDate;
	private String stockJson;

	
	
	public void setWarehousingDate(String warehousingDate) {
		this.warehousingDate = warehousingDate;
	}

	public void setStockJson(String stockJson) {
		this.stockJson = stockJson;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	public String warehouseEntry() throws GenericException, ParseException {
		@SuppressWarnings("unchecked")
		List<PartDto> stockList = Utils.json2Object(stockJson, PartDto.class);
//		Date entryDate= new Date();
//		entryDate.setTime(Long.valueOf(warehousingDate));
		SimpleDateFormat sdf =   new SimpleDateFormat(Constants.dfyyMMdd);
		invService.warehouseEntry(stockList,sdf.parse(warehousingDate));
		return SUCCESS;
	}
	
	public String warehouseOut() throws GenericException {
		@SuppressWarnings("unchecked")
		List<PartDto> stockList = Utils.json2Object(stockJson, PartDto.class);
		Date entryDate= new Date();
//		entryDate.setTime(Long.valueOf(warehousingDate));
		invService.warehouseEntry(stockList,entryDate);
		return SUCCESS;
	}
}
