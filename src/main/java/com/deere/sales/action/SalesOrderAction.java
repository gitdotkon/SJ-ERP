package com.deere.sales.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deere.action.BaseAction;
import com.deere.common.Constants;
import com.deere.common.Utils;
import com.deere.exception.GenericException;
import com.deere.manufacture.service.MPRService;
import com.deere.model.GenericPart;
import com.deere.model.SalesOrder;
import com.deere.model.SalesOrderItem;
import com.deere.model.dto.SalesOrderDto;
import com.deere.sales.service.SalesOrderService;

@SuppressWarnings("serial")
public class SalesOrderAction extends BaseAction {
	
	SimpleDateFormat sdf = new SimpleDateFormat(Constants.dfyyMMdd);

	private String dateType;
	/**
	 * 
	 */
	private String fromDate;
	private String toDate;
	private String planned;

	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 发货日期
	 */
	private String deliveryDate;
	/**
	 * 持续日期??
	 */
	private String dueDate;
	/**
	 * 客户标识
	 */
	private String customer;
	/**
	 * 销售人员
	 */
	private String sales;
	
	private List<SalesOrderDto> salesOrderItems = new ArrayList<SalesOrderDto>();
	@Autowired
	private SalesOrderService soService;

	private List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
	@Autowired
	private MPRService mrpServivce;
	
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
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String orderList() throws Exception {
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
		return "orderList";
	}
	/**
	 * 添加一个订单
	 * @return
	 * @throws GenericException
	 * @throws ParseException
	 */
	public String addOrder() throws GenericException, ParseException{
		SalesOrder salesOrder = new SalesOrder(orderNum);
		salesOrder.setDeliveryDate(sdf.parse(deliveryDate));
		salesOrder.setDueDate(sdf.parse(dueDate));
//		salesOrder.setOrderNum(orderNum);
		List<SalesOrderItem> soItemList = Utils.json2Object(this.getJsonData(), SalesOrderItem.class);
		for (SalesOrderItem salesOrderItem : soItemList) {
			salesOrder.addOrderItem(salesOrderItem);
		}
		soService.generateOrder(salesOrder);
		return SUCCESS;
	}

	/**
	 * 根据参数值来生成查询SQL
	 * @return
	 * @see #fromDate
	 * @see #toDate
	 * @see #orderNum
	 * @see #customer
	 */
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

	/**
	 * 调用MRP运算
	 * 
	 * <p>
	 * 	此过程根据<code>orderNum</code>来把订单拆分到最小构件，放会需求池中，
	 * 	<code>orderNum</code>支持以','分隔的多个订单号。
	 * </p>
	 * @return
	 * @see #orderNum
	 * @see {@link MPRService#runMPR(List)}
	 */
	public String mrpCal() {
		orderNum = orderNum.substring(0, orderNum.length() - 1);
		String[] orders = orderNum.split(",");
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		for (String order : orders) {
			SalesOrder salesOrder = soService.findOrderbyNum(order);
			salesOrders.add(salesOrder);
		}
		mrpServivce.runMPR(salesOrders);
		return SUCCESS;
	}

	/**
	 * 订单查询
	 * 
	 * <p>根据用户输入的过滤条件显示订单。</p>
	 * @return
	 * @throws Exception
	 * @see {@link #genQuery()}
	 */
	public String salesOrderSearch() throws Exception {
		String query = this.genQuery();
		// String query ="";
		this.setSalesOrderList(soService.findUnplannedOrder(query));
		return "salesOrderList";
	}

	/**
	 * 显示订单
	 * @return
	 * @throws Exception
	 * @see {@link #genQuery()}
	 */
	public String listOrder() throws Exception {
		String query = this.genQuery();
		// String query ="";
		this.setSalesOrderList(soService.findUnplannedOrder(query));
		return SUCCESS;
	}
}
