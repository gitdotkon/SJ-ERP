package com.deere.sales.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.model.GenericPart;
import com.deere.model.SalesOrderItem;
import com.deere.model.dto.SalesOrderDto;
import com.deere.sales.service.SalesOrderService;
@Deprecated
public class SalesOrderListAction extends BaseAction {
	@Autowired
	private SalesOrderService soService;
	
	private List<SalesOrderDto> salesOrderItems = new ArrayList<SalesOrderDto>();

	/**
	 * 订单号
	 */
	private String orderNum;
	

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}


	public List<SalesOrderDto> getSalesOrderItems() {
		return salesOrderItems;
	}

	public void setSalesOrderItems(List<SalesOrderDto> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}

	@Override
	public String execute() throws Exception {
		List<SalesOrderItem> soItems = soService.findOrderItembyNum(orderNum);
		for (SalesOrderItem salesOrderItem : soItems) {
			SalesOrderDto sales = new SalesOrderDto();
//			BeanUtils.copyProperties(salesOrderItem, sales);
			GenericPart part = salesOrderItem.getPart();
			sales.setPartCode(part.getPartCode());
			sales.setPartName(part.getPartName());
			sales.setPartType(part.getPartType());
			sales.setItemNum(salesOrderItem.getItemNum());
			sales.setPrice(salesOrderItem.getPrice());
			sales.setQuantity(salesOrderItem.getQuantity());
			this.getSalesOrderItems().add(sales);

		}
		return SUCCESS;
	}
}
