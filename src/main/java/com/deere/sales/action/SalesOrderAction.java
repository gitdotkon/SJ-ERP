package com.deere.sales.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Constants;
import com.deere.common.Utils;
import com.deere.exception.GenericException;
import com.deere.model.SalesOrder;
import com.deere.model.SalesOrderItem;
import com.deere.model.dto.SalesOrderDto;
import com.deere.sales.service.SalesOrderService;

@SuppressWarnings("serial")
public class SalesOrderAction extends BaseAction {
	
	SimpleDateFormat sdf = new SimpleDateFormat(Constants.dfyyMMdd);
	@Autowired
	private SalesOrderService soService;
	
	
	private String orderNum;
	private String deliveryDate;
	private String dueDate;
	private String customer;
	private String sales;
	
	private List<SalesOrderDto> salesOrderItems = new ArrayList<SalesOrderDto>();
	
	
	



	public List<SalesOrderDto> getSalesOrderItems() {
		return salesOrderItems;
	}



	public void setSalesOrderItems(List<SalesOrderDto> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}



	public String getOrderNum() {
		return orderNum;
	}



	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}



	public String getDeliveryDate() {
		return deliveryDate;
	}



	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}



	public String getDueDate() {
		return dueDate;
	}



	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	


	public String getCustomer() {
		return customer;
	}



	public void setCustomer(String customer) {
		this.customer = customer;
	}



	public String getSales() {
		return sales;
	}



	public void setSales(String sales) {
		this.sales = sales;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
		
	}
	
	
	
	public String addOrder() throws GenericException, ParseException{
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setDeliveryDate(sdf.parse(deliveryDate));
		salesOrder.setDueDate(sdf.parse(dueDate));
		salesOrder.setOrderNum(orderNum);
		List<SalesOrderItem> soItemList = Utils.json2Object(this.getJsonData(), SalesOrderItem.class);
		for (SalesOrderItem salesOrderItem : soItemList) {
			salesOrder.addOrderItem(salesOrderItem);
		}
		soService.generateOrder(salesOrder);
		return SUCCESS;
	}
	
	
}
