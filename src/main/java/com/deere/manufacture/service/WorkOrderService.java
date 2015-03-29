package com.deere.manufacture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.model.WorkOrder;

/**
 * 
 * @author zhaohongxing
 *
 */
@Service
public class WorkOrderService {
	
	@Autowired
	private GenericDao<WorkOrder> workOrderDao;
	/**
	 * 查询出所有的生产计划工作单
	 * @return
	 */
	public List listPlanOrder(){
		return null;
	}
	/**
	 * 添加水流单
	 * @param workOrders
	 * @return
	 */
	public void insertWorkOrder(List<WorkOrder> workOrders){
		for(WorkOrder workOrder :workOrders){
			workOrderDao.merge(workOrder);
		}
	}

}
