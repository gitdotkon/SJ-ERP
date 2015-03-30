package com.deere.manufacture.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Utils;
import com.deere.exception.GenericException;
import com.deere.manufacture.service.WorkOrderService;
import com.deere.model.WorkOrder;
/**
 * 工作单相关的业务方法
 * @author zhaohongxing
 *
 */
public class WorkOrderAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2144382099128552441L;
	
	private String dataJson;
	/*
	 * 用于查询
	 */
	private WorkOrder workOrder; 
	/*
	 * 用于页面展现，最终会转换成json格式。
	 */
	private List<WorkOrder> workOrders; 
	
	@Autowired
	private WorkOrderService workOrderService;

	public String getDataJson() {
		return dataJson;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public List<WorkOrder> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(List<WorkOrder> workOrders) {
		this.workOrders = workOrders;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	/**
	 * 显示所有的生产计划单
	 * @return
	 */
	public String listPlanOrder(){
		return "listPlanOrder";
	}
	
	/**
	 * 添加流水记录（工作单）。
	 * @return
	 * @throws GenericException 
	 */
	public String insertWorkOrder() throws GenericException{
		List<WorkOrder> workOrders = Utils.json2Object(dataJson, WorkOrder.class);
		workOrderService.insertWorkOrder(workOrders);
		return "listPlanOrder";
	}
	/**
	 * 查询所有状态为NEW的工作单。
	 * @return
	 * @throws GenericException
	 */
	public String listWorkOrder() throws GenericException{
		workOrders = workOrderService.getAllWorkOrder(workOrder);
		return "listWorkOrder";
	}
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 进入编辑工作单的方法
	 * @return
	 * @throws Exception
	 */
	public String toWorkOrder() throws Exception {
		return "toWorkOrder";
	}

}
