package com.deere.sales.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Constants;
import com.deere.manufacture.service.MRPService;
import com.deere.model.SalesOrder;
import com.deere.sales.service.SalesOrderService;
@Deprecated
public class SalesOrderSearchAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1373735730759819424L;

	SimpleDateFormat sdf = new SimpleDateFormat(Constants.dfyyMMdd);

	private List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
	@Autowired
	private SalesOrderService soService;

	@Autowired
	private MRPService mrpServivce;

	private String orderNum;
	private String dateType;
	private String fromDate;
	private String toDate;
	private String customer;
	private String sales;
	private String planned;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
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

	public List<SalesOrder> getSalesOrderList() {
		return salesOrderList;
	}

	public void setSalesOrderList(List<SalesOrder> salesOrderList) {
		this.salesOrderList = salesOrderList;
	}

	public String getPlanned() {
		return planned;
	}

	public void setPlanned(String planned) {
		this.planned = planned;
	}

	public String genQuery() {
		StringBuffer sb = new StringBuffer("and ");
		sb.append(dateType + " between '" + fromDate + "' and '" + toDate
				+ "' ");

		if (!orderNum.equals(""))
			sb.append("and orderNum= '" + orderNum + "'");
		if (!customer.equals(""))
			sb.append("and customer.companyName= '" + customer + "'");

		return sb.toString();
	}

	public String mprCal() {

		orderNum = orderNum.substring(0, orderNum.length() - 1);
		String[] orders = orderNum.split(",");
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		for (String order : orders) {
			SalesOrder salesOrder = soService.findOrderbyNum(order);
			salesOrders.add(salesOrder);

		}

		mrpServivce.runMRP(salesOrders);
		return SUCCESS;

	}

	@Override
	public String execute() throws Exception {
		String query = this.genQuery();
		// String query ="";
		this.setSalesOrderList(soService.findUnplannedOrder(query));
		return SUCCESS;
	}

	// @Override
	public String listOrder() throws Exception {
		String query = this.genQuery();
		// String query ="";
		this.setSalesOrderList(soService.findUnplannedOrder(query));
		return SUCCESS;
	}
}
