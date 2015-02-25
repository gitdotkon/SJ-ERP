package com.deere.sales.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.model.SalesOrder;
import com.deere.model.SalesOrderItem;

@Service
public class SalesOrderService {
	@Autowired
	private GenericDao<SalesOrder> SODao;
	@Autowired
	private GenericDao<SalesOrderItem> SOItemDao;

	public void generateOrder(SalesOrder SO) {
		
		
		SODao.save(SO);
		Set<SalesOrderItem> orderItems = SO.getOrderItems();
		for (SalesOrderItem salesOrderItem : orderItems) {
			SOItemDao.merge(salesOrderItem);
		}
		
	}

	public void updateOrder(SalesOrder SO) {

		SODao.update(SO);
		
	}
	
	public List<SalesOrder> findUnplannedOrder(String criteria){
		String query ="from SalesOrder where planned=false "+criteria;
		return SODao.query(query);
	}
	
	public List<SalesOrderItem> findOrderItembyNum(String orderNum){
		String query ="from SalesOrderItem where salesOrder.orderNum='"+orderNum+"'";
		return SOItemDao.query(query);
	}
	
	public SalesOrder findOrderbyNum(String orderNum){
		return SODao.findById(orderNum);
	}

}
