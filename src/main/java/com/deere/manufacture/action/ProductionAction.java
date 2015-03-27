package com.deere.manufacture.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Utils;
import com.deere.dao.GenericDao;
import com.deere.manufacture.service.MRPService;
import com.deere.manufacture.service.ProductionService;
import com.deere.model.ProductionDetail;
import com.deere.model.Inventory;
import com.deere.model.MRPModel;
import com.deere.model.dto.PartDto;
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
	private MRPService mprService;
	
	@Autowired
	private GenericDao<Inventory> inventoryDao;

	private String dataJson;
	private String partCode;
	/**
	 * 流水单
	 */
	private ProductionDetail aStatement;

	private List<ProductionDto> productionList = new ArrayList<ProductionDto>();
	private List<String> orderNumList = Collections.EMPTY_LIST;

	private List<SalesOrderDto> orderList = new ArrayList<SalesOrderDto>();

	private String selectedOrder;
	
	public ProductionDetail getAStatement() {
		return aStatement;
	}

	public void setAStatement(ProductionDetail statement) {
		aStatement = statement;
	}

	public String getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(String selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public List<String> getOrderNumList() {
		return orderNumList;
	}

	public void setOrderNumList(List<String> orderNumList) {
		this.orderNumList = orderNumList;
	}

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

	public String listOrderNum() throws Exception {
		this.setOrderNumList(mprService.getUnplannedOrder());
		return SUCCESS;
	}
	/*
	 * 
	 * List unplanned production requirements from mrp<mrpModel> pool
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		// TODO Auto-generated method stub

		System.out.println(selectedOrder);
		this.setProductionList(proService.productionPlan(selectedOrder));

		// this.setProductionList(proService.genProOrder(partList));
		return SUCCESS;
	}

	public String insertProOrder() throws Exception {

		String a = this.getOrder();
		System.out.println(a);
		List<ProductionDto> proList = Utils.json2Object(dataJson, ProductionDto.class);
		proService.generatePlan(proList, order);
		return SUCCESS;
	}
	
	public String calMRP() throws Exception {

		String a = this.getOrder();
		System.out.println(a);
		List<PartDto> dtoList = Utils.json2Object(dataJson, PartDto.class);
		Map<String, Integer> mrpList = mprService.getMRP(dtoList);
		Iterator<String> it = mrpList.keySet().iterator();
		while (it.hasNext()) {
			ProductionDto proDto = new ProductionDto();
			String partCode = it.next();
			proDto.setPartCode(partCode);
			proDto.setRequiredQty(mrpList.get(partCode));
			Inventory inv =inventoryDao.findById(proDto.getPartCode());
			if(inv==null) 
				proDto.setInventoryQty(0);
			else 
				proDto.setInventoryQty(inv.getQuantity());
			
			Integer recommandedQty =  proDto.getRequiredQty()-proDto.getInventoryQty();
			if(recommandedQty<0)
				recommandedQty=0;
			proDto.setRecommandedQty(recommandedQty);
			proDto.setActualQty(recommandedQty);
			productionList.add(proDto);
		}
		
		
		
		return "listMRP";
	}
	

	public String listOrder() throws Exception {
		System.out.println("test");
		List<MRPModel> mprList = mprService.listOrderforPart(this.getPartCode());
		for (MRPModel mprModel : mprList) {
			SalesOrderDto soDto = new SalesOrderDto();
			soDto.setSalesOrder(mprModel.getSalesOrder().getOrderNum());
			soDto.setQuantity(mprModel.getRequiredQty());
			soDto.setPartCode(mprModel.getPart().toString());
			soDto.setDeliveryDate(mprModel.getDeliveryDate());
			soDto.setDueDate(mprModel.getDueDate());
			this.getOrderList().add(soDto);
		}
		return "listOrder";
	}
	/**
	 * 保存流水单据
	 * @return
	 */
	public String addAccountStatement(){
		proService.addAccountStatement(aStatement);
		return "";
	}
}
