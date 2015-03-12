package com.deere.manufacture.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Utils;
import com.deere.manufacture.service.MPRService;
import com.deere.manufacture.service.ProductionService;
import com.deere.model.MPRModel;
import com.deere.model.dto.ProductionDto;
import com.deere.model.dto.SalesOrderDto;

public class ProductionAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProductionService proService;
	
	@Autowired
	private MPRService mprService;
	
	private String dataJson;
	private String partCode;
	
	private List<ProductionDto> productionList = Collections.EMPTY_LIST;
	
	private List<SalesOrderDto> orderList = new ArrayList<SalesOrderDto>();
	
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

	

	

	public List<SalesOrderDto> getOrderList() {
		return orderList;
	}


	public void setOrderList(List<SalesOrderDto> orderList) {
		this.orderList = orderList;
	}

	
	public String getPartCode() {
		return partCode;
	}


	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}


	@Override
	public String execute() throws Exception {
		
		// TODO Auto-generated method stub
		this.setProductionList(proService.productionPlan());
		
//		this.setProductionList(proService.genProOrder(partList));
		return SUCCESS;
	}
	public String insertProOrder() throws Exception{
		
		String a= this.getOrder();
		System.out.println(a);
		List<ProductionDto> proList = Utils.json2Object(dataJson, ProductionDto.class);
		proService.generatePlan(proList, order);
		return SUCCESS;
	}
	
	public String listOrder() throws Exception{
		System.out.println("test");
		List<MPRModel> mprList= mprService.listOrderforPart(this.getPartCode());
		for (MPRModel mprModel : mprList) {
			SalesOrderDto soDto= new SalesOrderDto();
			soDto.setSalesOrder(mprModel.getSalesOrder().getOrderNum());
			soDto.setQuantity(mprModel.getRequiredQty());
			soDto.setPartCode(mprModel.getPart().toString());
			soDto.setDeliveryDate(mprModel.getDeliveryDate());
			soDto.setDueDate(mprModel.getDueDate());
			this.getOrderList().add(soDto);
		}
		return SUCCESS;
	}
}
