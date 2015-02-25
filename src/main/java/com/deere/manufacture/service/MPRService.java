package com.deere.manufacture.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.logicalcobwebs.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.model.BOMTree;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.MPRModel;
import com.deere.model.SalesOrder;
import com.deere.model.SalesOrderItem;
import com.deere.model.dto.PartDto;
import com.deere.model.enums.MPRType;
import com.deere.sales.service.SalesOrderService;

@Service
public class MPRService {

	@Autowired
	private GenericDao<BOMTree> bomTreeDao;

	@Autowired
	private SalesOrderService SOService;

	@Autowired
	private GenericDao<Inventory> inventoryDao;

	@Autowired
	private GenericDao<GenericPart> partDao;

	@Autowired
	private GenericDao<MPRModel> MPRDao;
	
	@Autowired
	private GenericDao<SalesOrder> SODao;

	public Map<String, Integer> getMPRPart(GenericPart parent, Integer dosage) {
		// Map<GenericPart,Integer> partList = new
		// HashMap<GenericPart,Integer>();
		Map<String, Integer> mprList = getMPR(parent, dosage);

		return mprList;
	}

	public Map<String, Integer> getMPR(GenericPart parent, Integer dosage) {
		return getMPR(parent.getPartCode(), dosage);

	}

	public Map<String, Integer> getMPR(String parent, Integer dosage) {
		Map<String, Integer> mprList = new HashMap<String, Integer>();
		mprList.put(parent, dosage);
		// Map<GenericPart,Integer> bomChild= getBomChild(parent);
		// Iterator<GenericPart> it = bomChild.keySet().iterator();
		// bomChild.entrySet()

		String query = "from BOMTree where parent='" + parent + "'";
		List<BOMTree> machineList = bomTreeDao.query(query);
		for (BOMTree bomTree : machineList) {
			GenericPart child = bomTree.getChild();
			Map<String, Integer> childMap = getMPR(child, bomTree.getDosage());
			merge(mprList, childMap, dosage);
		}
		return mprList;

	}

	public Map<String, Integer> getMPR(List<PartDto> dtoList) {
		Map<String, Integer> mprList = new HashMap<String, Integer>();
		for (PartDto partDto : dtoList) {
			Map<String, Integer> eachMap = this.getMPR(partDto.getPartCode(),
					partDto.getQuantity());
			this.merge(mprList, eachMap, 1);
		}

		return mprList;
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

	public Map<String, Integer> getMPRforOrder(List<SalesOrderItem> SOItemList) {
		Map<String, Integer> mprList = new HashMap<String, Integer>();

		for (SalesOrderItem salesOrderItem : SOItemList) {
			Map<String, Integer> eachMap = this.getMPR(
					salesOrderItem.getPart(), salesOrderItem.getQuantity());
			this.merge(mprList, eachMap, 1);
		}

		return mprList;
	}

	public void runMPR(List<SalesOrder> salesOrderList) {
		for (SalesOrder salesOrder : salesOrderList) {
			List<SalesOrderItem> SOItemList = SOService
					.findOrderItembyNum(salesOrder.getOrderNum());
			Map<String, Integer> mprList = getMPRforOrder(SOItemList);
			Iterator<String> it = mprList.keySet().iterator();
			while (it.hasNext()) {
				String partCode = it.next();
				Integer requiredQty = mprList.get(partCode);
				MPRModel mprModel = new MPRModel();
				// PartDto partDto = invService.getStockbyPartcode(partCode);
				GenericPart part = partDao.findById(partCode);
				// TODO add MPR Type for Parts
				if (part.getPartType().equals("外购"))
					mprModel.setMprType(MPRType.parchase);
				else
					mprModel.setMprType(MPRType.self);

				mprModel.setPart(part);
				mprModel.setSalesOrder(salesOrder);
				mprModel.setRequiredQty(requiredQty);
				// TODO recommanded quantity = required-inv
				mprModel.setDeliveryDate(salesOrder.getDeliveryDate());
				MPRDao.merge(mprModel);
				
				
			}
			salesOrder.setPlanned(true);
			SODao.merge(salesOrder);
		}
	}
}
