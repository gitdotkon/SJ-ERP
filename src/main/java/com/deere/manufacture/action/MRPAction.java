package com.deere.manufacture.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.manufacture.service.MRPService;
import com.deere.model.SalesOrder;
import com.deere.sales.service.SalesOrderService;

public class MRPAction extends BaseAction {
	@Autowired
	private MRPService mrpService;
	@Autowired
	private SalesOrderService soService;
	
	private String orderNum;
	
	
	public String getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}


	public String mrpCal() {
		orderNum = orderNum.substring(0, orderNum.length() - 1);
		String[] orders = orderNum.split(",");
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		for (String order : orders) {
			SalesOrder salesOrder = soService.findOrderbyNum(order);
			salesOrders.add(salesOrder);
		}
		mrpService.runMRP(salesOrders);
		return SUCCESS;
	}
}
