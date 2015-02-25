package com.deere.manufacture.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Utils;
import com.deere.manufacture.service.ProductionService;
import com.deere.model.dto.PartDto;
import com.deere.model.dto.ProductionDto;

public class ProductionAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProductionService proService;
	
	private String dataJson;
	
	private List<ProductionDto> productionList = Collections.EMPTY_LIST;
	
	private String order;
	
	
	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public String getDataJson() {
		return dataJson;
	}


	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	
	
	
	public List<ProductionDto> getProductionList() {
		return productionList;
	}


	public void setProductionList(List<ProductionDto> productionList) {
		this.productionList = productionList;
	}



	

	@Override
	public String execute() throws Exception {
		
		// TODO Auto-generated method stub
		List<PartDto> partList = Utils.json2Object(dataJson, PartDto.class);
		
		this.setProductionList(proService.genProOrder(partList));
		return SUCCESS;
	}
	public String insertProOrder() throws Exception{
		
		String a= this.getOrder();
		System.out.println(a);
		List<ProductionDto> proList = Utils.json2Object(dataJson, ProductionDto.class);
		proService.generatePlan(proList, order);
		return SUCCESS;
	}
}
