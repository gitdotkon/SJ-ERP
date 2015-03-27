package com.deere.manufacture.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.logicalcobwebs.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.base.util.StringUtil;
import com.deere.dao.GenericDao;
import com.deere.model.BOMTree;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.MRPModel;
import com.deere.model.SalesOrder;
import com.deere.model.SalesOrderItem;
import com.deere.model.dto.PartDto;
import com.deere.model.enums.MRPType;
import com.deere.sales.service.SalesOrderService;

@Service
public class MRPService {

	@Autowired
	private GenericDao<BOMTree> bomTreeDao;

	@Autowired
	private SalesOrderService SOService;

	@Autowired
	private GenericDao<Inventory> inventoryDao;

	@Autowired
	private GenericDao<GenericPart> partDao;


	@Autowired
	private GenericDao<MRPModel> MRPDao;
	
	@Autowired
	private GenericDao<SalesOrder> SODao;

	public Map<String, Integer> getMRPPart(GenericPart parent, Integer dosage) {
		// Map<GenericPart,Integer> partList = new
		// HashMap<GenericPart,Integer>();
		Map<String, Integer> mrpList = getMRP(parent, dosage);

		return mrpList;
	}

	public Map<String, Integer> getMRP(GenericPart parent, Integer dosage) {
		return getMRP(parent.getPartCode(), dosage);

	}

	public Map<String, Integer> getMRP(String parent, Integer dosage) {
		Map<String, Integer> mrpList = new HashMap<String, Integer>();
		mrpList.put(parent, dosage);
		// Map<GenericPart,Integer> bomChild= getBomChild(parent);
		// Iterator<GenericPart> it = bomChild.keySet().iterator();
		// bomChild.entrySet()

		String query = "from BOMTree where parent='" + parent + "'";
		List<BOMTree> machineList = bomTreeDao.query(query);
		for (BOMTree bomTree : machineList) {
			GenericPart child = bomTree.getChild();
			Map<String, Integer> childMap = getMRP(child, bomTree.getDosage());
			merge(mrpList, childMap, dosage);
		}
		return mrpList;

	}

	public Map<String, Integer> getMRP(List<PartDto> dtoList) {
		Map<String, Integer> mrpList = new HashMap<String, Integer>();
		for (PartDto partDto : dtoList) {
			Map<String, Integer> eachMap = this.getMRP(partDto.getPartCode(),
					partDto.getQuantity());
			this.merge(mrpList, eachMap, 1);
		}

		return mrpList;
	}

	public void merge(Map<String, Integer> parentMap,
			Map<String, Integer> childMap, Integer dosage) {
		Iterator<String> it = childMap.keySet().iterator();
		while (it.hasNext()) {
			String partCode = it.next();
			Integer quantity = dosage * childMap.get(partCode);
			if (parentMap.containsKey(partCode)) {
				quantity += parentMap.get(partCode);
			}
			parentMap.put(partCode, quantity);
		}
	}
	
	public Map<String, Integer> getMRPforOrder(List<SalesOrderItem> SOItemList) {
		Map<String, Integer> mrpList = new HashMap<String, Integer>();

		for (SalesOrderItem salesOrderItem : SOItemList) {
			Map<String, Integer> eachMap = this.getMRP(
					salesOrderItem.getPart(), salesOrderItem.getQuantity());
			this.merge(mrpList, eachMap, 1);
		}

		return mrpList;
	}

	public void runMRP(List<SalesOrder> salesOrderList) {
		for (SalesOrder salesOrder : salesOrderList) {
			List<SalesOrderItem> SOItemList = SOService
					.findOrderItembyNum(salesOrder.getOrderNum());
			Map<String, Integer> mrpList = getMRPforOrder(SOItemList);
			Iterator<String> it = mrpList.keySet().iterator();
			while (it.hasNext()) {
				String partCode = it.next();
				Integer requiredQty = mrpList.get(partCode);
				MRPModel mrpModel = new MRPModel();
				// PartDto partDto = invService.getStockbyPartcode(partCode);
				GenericPart part = partDao.findById(partCode);
				// TODO add MPR Type for Parts
				if (part.getPartType().equals("外购"))
					mrpModel.setMprType(MRPType.parchase);
				else
					mrpModel.setMprType(MRPType.self);

				mrpModel.setPart(part);
				mrpModel.setSalesOrder(salesOrder);
				mrpModel.setRequiredQty(requiredQty);
				mrpModel.setDueDate(salesOrder.getDueDate());
				// TODO recommanded quantity = required-inv
				mrpModel.setDeliveryDate(salesOrder.getDeliveryDate());
				MRPDao.merge(mrpModel);
				
				
			}
			salesOrder.setPlanned(true);
			SODao.merge(salesOrder);
		}
	}
	
	public List<MRPModel> listOrderforPart(String partCode){
		
		String query="from MRPModel where partCode='"+partCode+"'";
		return MRPDao.query(query);
//		return null;
	}
	
	public List<String> getUnplannedOrder(){
//		String query="from MPRModel where partCode='"+partCode+"'";
		String query ="select distinct new com.deere.model.SalesOrder(salesOrder.orderNum)"
				+ " from MRPModel where mprType=0";
		List<SalesOrder> soList= SODao.query(query);
		List<String> ordrNumList= new ArrayList<String>();
		for (SalesOrder salesOrder : soList) {
			ordrNumList.add(salesOrder.getOrderNum());
		}
		return ordrNumList;
	}
	
public List<MRPModel> listRequirement(String partCode,String orders){
	
		
		String query="from MRPModel where partCode='"+partCode;
		if (orders!=null){
			String orderSql= StringUtil.getStringSql(orders);
			query+="' and salesOrder in " + orderSql;
		}
		return MRPDao.query(query);
//		return null;
	}
	
}
