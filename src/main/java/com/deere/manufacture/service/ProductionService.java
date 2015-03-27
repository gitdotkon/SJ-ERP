package com.deere.manufacture.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.base.util.StringUtil;
import com.deere.dao.GenericDao;
import com.deere.inventory.service.InventoryService;
import com.deere.model.ProductionDetail;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.ProductionOrder;
import com.deere.model.ProductionOrderItem;
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
	private GenericDao<ProductionDetail> accountStatementDao;
	
	@Autowired
	private InventoryService invService;
	
	@Autowired
	private SalesOrderService SOService;
	
	/*public List<ProductionDto> genProOrder(List<PartDto> dtoList){
		List<ProductionDto> productionList= new ArrayList<ProductionDto>();
		Map<String,Integer> mprList = mpr.getMPR(dtoList);
		Iterator<String> it =  mprList.keySet().iterator();
		while(it.hasNext()){
			String partCode =it.next();
			Integer requiredQty = mprList.get(partCode);
			ProductionDto proDto = new ProductionDto();
//			PartDto partDto = invService.getStockbyPartcode(partCode);
			Inventory inv = inventoryDao.findById(partCode);
			if(inv==null){
				GenericPart part = partDao.findById(partCode);
				BeanUtils.copyProperties(part, proDto);
				proDto.setQuantity(0);
			}
			else
				BeanUtils.copyProperties(inv, proDto);
			proDto.setRequiredQty(requiredQty);
			Integer recommandedQty =  requiredQty-proDto.getQuantity();
			if(recommandedQty<0)
				recommandedQty=0;
			proDto.setRecommandedQty(recommandedQty);
			proDto.setActualQty(recommandedQty);
			productionList.add(proDto);
		}
		return productionList;
	}*/
	
	/*
	 * 
	 * /*
	 * 
	 * List unplanned production requirements from mrp<mrpModel> pool
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 *
	 * 
	 */
	public List<ProductionDto> productionPlan(String selectedOrder){

	/*	
		String query = "select new com.deere.model.dto.ProductionDto(a.partCode,partName,partType,a.qty)"
		+ " from (select partCode,sum(requiredQty) qty from MPRModel where mprType=0 group by partCode) a,GenericPart"
		+ " where a.partCode=GenericPart.partCode";*/
		
		String query ="select new com.deere.model.dto.ProductionDto(mm.part,SUM(mm.requiredQty))"
				+ " from MRPModel mm where mm.mprType=0 and mm.planned = false";
		
		StringBuffer buffer = new StringBuffer(query);
		
		if ((null != selectedOrder) && !"".equals(selectedOrder)) 
			buffer.append("  and mm.salesOrder.orderNum in " + StringUtil.getStringSql(selectedOrder));
			
		buffer.append(" group by mm.part.partCode");
		
		System.out.print(buffer.toString());
		List<ProductionDto> dtoList= proDtoDao.query(buffer.toString());
		for (ProductionDto proDto : dtoList) {
			Inventory inv =inventoryDao.findById(proDto.getPartCode());
			if(inv==null) 
				proDto.setInventoryQty(0);
			else 
				proDto.setInventoryQty(inv.getQuantity());
			Integer recommandedQty =  proDto.getRequiredQty()-proDto.getInventoryQty();
			if(recommandedQty<0)
				recommandedQty=0;
			proDto.setRecommandedQty(recommandedQty);
			proDto.setActualQty(recommandedQty);
			
		}
		
		
		return dtoList;
		
	}
	
	public void generatePlan(List<ProductionDto> proDtoList,String PONumber){
		ProductionOrder proOrder= new ProductionOrder();
//		proOrder.setOrderNum(PONumber);
		proOrder.setOrderDate(new Date());
		proOrder=proOrderDao.merge(proOrder);
		for (ProductionDto productionDto : proDtoList) {
			ProductionOrderItem poItem = new ProductionOrderItem();
//			BeanUtils.copyProperties(productionDto, poItem);
			GenericPart part = partDao.findById(productionDto.getPartCode());
			poItem.setPart(part);
			poItem.setPlannedQty(productionDto.getActualQty());
			
			poItem.setProOrder(proOrder);
			proItemDao.merge(poItem);
		}
	}
	/**
	 * 保存工厂的流水单
	 * @param aStatement
	 */
	public void addAccountStatement(ProductionDetail aStatement){
		accountStatementDao.save(aStatement);
	}
	
}
