package com.deere.inventory.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.exception.GenericException;
import com.deere.manufacture.service.MPRService;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.Stock;
import com.deere.model.dto.PartDto;
import com.deere.model.dto.ExStockDto;
import com.deere.model.enums.PartType;
import com.deere.model.exdto.ExInventory;
import com.deere.service.DataImportService;

@Service
public class InventoryService {
	@Autowired
	private GenericDao<Inventory> inventoryDao;

	@Autowired
	private GenericDao<PartDto> StockdtoDao;

	@Autowired
	private GenericDao<GenericPart> partDao;

	@Autowired
	private GenericDao<Stock> stockDao;

	@Autowired
	private MPRService mpr;

	/*
	 * public void importTree(File file, String pattern) throws GenericException
	 * { DataImportService<ExBOMTree, BOMTree> bomImport = new
	 * DataImportService<ExBOMTree, BOMTree>( ExBOMTree.class, BOMTree.class);
	 * 
	 * List<BOMTree> bomList = bomImport.importSpreadsheet(file, pattern);
	 * String fileName= file.getName();
	 * fileName=fileName.substring(0,fileName.length()-4); for (BOMTree bomTree
	 * : bomList) { GenericPart parentPart = getParentPart(fileName);
	 * bomTree.setParent(parentPart); bomTreeDao.merge(bomTree); } public
	 * InventoryDto(String partCode, String partName, String partType, String
	 * address, Integer quantity,String modifiedBy,Date modifiedDate) { }
	 */

	public List<PartDto> serachInventory(String partCode) {
		String query = "select new com.deere.model.dto.PartDto"
				+ "(gp.partCode,gp.partName,gp.partType,inv.address,inv.quantity,inv.modifiedBy,inv.modifiedDate)"
				+ " from GenericPart gp,Inventory inv"
				+ " where  gp.partCode=inv.part.partCode"
				+ " and gp.partCode like '%" + partCode + "%'";

		List<PartDto> invList = null;
		try {

			invList = StockdtoDao.query(query);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return invList;
	}

	public PartDto getStockbyPartcode(String partCode) {
		String query = "select new com.deere.model.dto.PartDto"
				+ "(gp.partCode,gp.partName,gp.partType,inv.address,inv.quantity,inv.modifiedBy,inv.modifiedDate)"
				+ " from GenericPart gp,Inventory inv"
				+ " where gp.partCode = '" + partCode + "'";

		List<PartDto> invList = StockdtoDao.query(query);
		if (invList.size() == 0)
			return null;
		return invList.get(0);
	}

	public void warehouseEntry(List<PartDto> stockList, Date entryDate) {

		for (PartDto stockDto : stockList) {
			Stock stock = new Stock();
			BeanUtils.copyProperties(stockDto, stock);
			stock.setWarehousingType(true);
			stock.setWarehousingDate(entryDate);

			Inventory inv = getInvbyPart(stock.getPart());
			inv.addQuantity(stock.getQuantity());
			inv.setAddress(stock.getAddress());
			inventoryDao.merge(inv);
			stock.setInvQty(inv.getQuantity());
			stockDao.merge(stock);
		}

	}

	public List<ExStockDto> warehouseOut(List<PartDto> stockList, Date entryDate) {

		for (PartDto partDto : stockList) {
			GenericPart part = new GenericPart();
			BeanUtils.copyProperties(partDto, part);
			Integer qty = partDto.getQuantity();
			Map<String, Integer> mprList = mpr.getMPR(part, qty);

			Iterator<String> it = mprList.keySet().iterator();
			while (it.hasNext()) {
				String partCode = it.next();
				Integer requireQty = mprList.get(partCode);
				PartDto invDto = this.getStockbyPartcode(partCode);
				ExStockDto exstock = new ExStockDto();
				BeanUtils.copyProperties(invDto, exstock);
				exstock.setRequireQty(requireQty);
				Integer retrievalQty = 0;
			}

			/*
			 * mprList.put(partCode, quantity); }
			 * stock.setWarehousingType(true);
			 * stock.setWarehousingDate(entryDate);
			 * 
			 * Inventory inv = getInvbyPart(stock.getPart());
			 * inv.addQuantity(stock.getQuantity());
			 * inv.setAddress(stock.getAddress()); inventoryDao.merge(inv);
			 * stock.setInvQty(inv.getQuantity()); stockDao.merge(stock); }
			 */
		}
		return null;

	}

	public Inventory getInvbyPart(GenericPart part) {

//		List<Inventory> invList = inventoryDao.query(query);
		Inventory inv = inventoryDao.findById(part.getPartCode());
//		Inventory inv = null;
		if(inv==null){
			inv = new Inventory();
			inv.setPartCode(part.getPartCode());
			inv.setPartName(part.getPartName());
			inv.setQuantity(0);
		}
		return inv;
	}

	/*
	 * 
	 * 
	 * public List<GenericPart> searchMachine(String partCode,String partIndex){
	 * 
	 * // String partType= PartType.getPartType(partIndex); String query=
	 * "from GenericPart where partCode like '%"+partCode+"%'";
	 * if(!partIndex.equals("0")){ String partType=
	 * PartType.getPartType(partIndex); query+=" and partType='"+partType+"'"; }
	 * 
	 * List<GenericPart> machineList = partDao.query(query);
	 * 
	 * return machineList; }
	 */
	public void importInventory(File file, String pattern)
			throws GenericException {
		System.out.println("start");
		DataImportService<ExInventory, Inventory> invImport = new DataImportService<ExInventory, Inventory>(
				ExInventory.class, Inventory.class);
		List<Inventory> invList = invImport.importSpreadsheet(file, pattern);
		for (Inventory inv : invList) {
			GenericPart part = partDao.findById(inv.getPartCode());
			inv.setPartName(part.getPartName());
			inv.setPartType(part.getPartType());
			Inventory baseInv = inventoryDao.findById(inv.getPartCode());
			this.merge(inv, baseInv);
			inventoryDao.merge(inv);
		}

	}

	private void merge(Inventory newInv, Inventory baseInv) {
		if (baseInv == null)
			return;
		newInv.setAddress(newInv.getAddress() + "/" + baseInv.getAddress());
		newInv.addQuantity(baseInv.getQuantity());
	}
}
