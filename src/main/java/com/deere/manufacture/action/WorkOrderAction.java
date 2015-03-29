package com.deere.manufacture.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Utils;
import com.deere.exception.GenericException;
import com.deere.manufacture.service.WorkOrderService;
import com.deere.model.WorkOrder;

public class WorkOrderAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2144382099128552441L;
	
	private String dataJson;
	
	@Autowired
	private WorkOrderService workOrderService;
	
	/**
	 * 显示所有的生产计划
	 * @return
	 */
	public String listPlanOrder(){
		return "listPlanOrder";
	}
	
	/**
	 * 添加流水
	 * @return
	 * @throws GenericException 
	 */
	public String insertWorkOrder() throws GenericException{
		List<WorkOrder> workOrders = Utils.json2Object(dataJson, WorkOrder.class);
		workOrderService.insertWorkOrder(workOrders);
		return "listPlanOrder";
	}
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String getDataJson() {
		return dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

}
