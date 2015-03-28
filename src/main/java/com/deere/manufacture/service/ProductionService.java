package com.deere.manufacture.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.base.util.StringUtil;
import com.deere.dao.GenericDao;
import com.deere.inventory.service.InventoryService;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.MRPModel;
import com.deere.model.ProductionOrder;
import com.deere.model.ProductionOrderItem;
import com.deere.model.dto.PlanDto;
import com.deere.model.dto.ProductionDto;
import com.deere.sales.service.SalesOrderService;

@Service
public class ProductionService {

	@Autowired
	private MRPService mpr;

	@Autowired
	private GenericDao<GenericPart> partDao;

	@Autowired
	private GenericDao<Inventory> inventoryDao;

	@Autowired
	private GenericDao<ProductionOrder> proOrderDao;

	@Autowired
	private GenericDao<ProductionOrderItem> proItemDao;

	@Autowired
	private GenericDao<ProductionDto> proDtoDao;

	@Autowired
	private GenericDao<MRPModel> MRPDao;

	@Autowired
	private InventoryService invService;

	@Autowired
	private SalesOrderService SOService;
	
	@Autowired
	GenericDao<PlanDto> planDao;
	/*
	 * public List<ProductionDto> genProOrder(List<PartDto> dtoList){
	 * List<ProductionDto> productionList= new ArrayList<ProductionDto>();
	 * Map<String,Integer> mprList = mpr.getMPR(dtoList); Iterator<String> it =
	 * mprList.keySet().iterator(); while(it.hasNext()){ String partCode
	 * =it.next(); Integer requiredQty = mprList.get(partCode); ProductionDto
	 * proDto = new ProductionDto(); // PartDto partDto =
	 * invService.getStockbyPartcode(partCode); Inventory inv =
	 * inventoryDao.findById(partCode); if(inv==null){ GenericPart part =
	 * partDao.findById(partCode); BeanUtils.copyProperties(part, proDto);
	 * proDto.setQuantity(0); } else BeanUtils.copyProperties(inv, proDto);
	 * proDto.setRequiredQty(requiredQty); Integer recommandedQty =
	 * requiredQty-proDto.getQuantity(); if(recommandedQty<0) recommandedQty=0;
	 * proDto.setRecommandedQty(recommandedQty);
	 * proDto.setActualQty(recommandedQty); productionList.add(proDto); } return
	 * productionList; }
	 */

	/*
	 * 
	 * /*
	 * 
	 * List unplanned production requirements from mrp<mrpModel> pool
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	/*public List<ProductionDto> productionPlan(String selectedOrder) {

		
		 * String query =
		 * "select new com.deere.model.dto.ProductionDto(a.partCode,partName,partType,a.qty)"
		 * +
		 * " from (select partCode,sum(requiredQty) qty from MPRModel where mprType=0 group by partCode) a,GenericPart"
		 * + " where a.partCode=GenericPart.partCode";
		 

		String query = "select new com.deere.model.dto.ProductionDto(mm.part,SUM(mm.requiredQty),SUM(pi.plannedQty-pi.finishedQty),SUM(ma.requiredQty))"
				+ " from MRPModel mm,ProductionOrderItem pi,MRPModel ma where mm.mprType=0 and mm.planned = false and pi.part.partCode=mm.part.partCode and mm.part.partCode=ma.part.partCode";

		StringBuffer buffer = new StringBuffer(query);

		if ((null != selectedOrder) && !"".equals(selectedOrder))
			buffer.append("  and mm.salesOrder.orderNum in "
					+ StringUtil.getStringSql(selectedOrder));

		buffer.append(" group by mm.part.partCode");

		System.out.print(buffer.toString());
		List<ProductionDto> dtoList = proDtoDao.query(buffer.toString());
		for (ProductionDto proDto : dtoList) {
			Inventory inv = inventoryDao.findById(proDto.getPartCode());
			if (inv == null)
				proDto.setInventoryQty(0);
			else
				proDto.setInventoryQty(inv.getQuantity());
			Integer processingQty = proDto.getProcessingQty();
			Integer reservedQty = proDto.getReservedQty();
//			proDto.setProcessingQty(processingQty);
//			proDto.setReservedQty(reservedQty);
			Integer recommandedQty = proDto.getRequiredQty()+reservedQty
					- proDto.getInventoryQty()-processingQty;
			if (recommandedQty < 0)
				recommandedQty = 0;
			proDto.setRecommandedQty(recommandedQty);
			proDto.setActualQty(recommandedQty);
			
			

		}

		return dtoList;

	}*/
	
	public List<ProductionDto> productionPlan(String selectedOrder){
		List<String> sList= new ArrayList<String>();
		sList.add(selectedOrder);
		List<PlanDto> planList =planDao.callProcR("proc_plan", sList,PlanDto.class);
		List<ProductionDto> dtoList= new ArrayList<ProductionDto>();
		for (PlanDto planDto : planList) {
			ProductionDto proDto = new ProductionDto();
			
			BeanUtils.copyProperties(planDto, proDto);
			Inventory inv = inventoryDao.findById(proDto.getPartCode());
			if (inv == null)
				proDto.setInventoryQty(0);
			else
				proDto.setInventoryQty(inv.getQuantity());
			if(proDto.getProcessingQty()==null)
				proDto.setProcessingQty(0);
			if(proDto.getReservedQty()==null)
				proDto.setReservedQty(0);
			Integer recommandedQty = proDto.getRequiredQty()+proDto.getReservedQty()
					- proDto.getInventoryQty()-proDto.getProcessingQty();
			if (recommandedQty < 0)
				recommandedQty = 0;
			proDto.setRecommandedQty(recommandedQty);
			proDto.setActualQty(recommandedQty);
			dtoList.add(proDto);
		}
		return dtoList;
	}

	public void generatePlan(List<ProductionDto> proDtoList, String orderNum) {
		ProductionOrder proOrder = new ProductionOrder();
		// proOrder.setOrderNum(PONumber);
		proOrder.setOrderDate(new Date());
		proOrder = proOrderDao.merge(proOrder);
		for (ProductionDto productionDto : proDtoList) {
			ProductionOrderItem poItem = new ProductionOrderItem();
			// BeanUtils.copyProperties(productionDto, poItem);
			GenericPart part = partDao.findById(productionDto.getPartCode());
			poItem.setPart(part);
			poItem.setPlannedQty(productionDto.getActualQty());

			poItem.setProOrder(proOrder);
			poItem.setSelf(productionDto.getProType());
			poItem=proItemDao.merge(poItem);
			if (orderNum != null) {
				List<MRPModel> mrpList = mpr.listRequirement(
						part.getPartCode(), orderNum);
				for (MRPModel mrpModel : mrpList) {
					mrpModel.setPlanned(true);
					mrpModel.setProOrderItem(poItem);
					MRPDao.merge(mrpModel);
				}
			}
		}
	}
	
	private Integer getProcessingQty(String partCode){
//		proItemDao
		String query= "from ProductionOrderItem where part.partCode='"+partCode+"' and completed = false";
		List<ProductionOrderItem> proItems= proItemDao.query(query);
		Integer processingQty=0;
		for (ProductionOrderItem productionOrderItem : proItems) {
			processingQty+=productionOrderItem.getPlannedQty();
			processingQty-=productionOrderItem.getFinishedQty();
		}
		return processingQty;
		
	}
	
	private Integer getReservedQty(String partCode){
//		TOTO
		String query= "from MRPModel where part.partCode='"+partCode+"'";
		List<MRPModel> mrpList=MRPDao.query(query);
		Integer reservedQty=0;
		for (MRPModel mrpModel : mrpList) {
			reservedQty+=mrpModel.getRequiredQty();
		}
		return reservedQty;
		
	}



}
