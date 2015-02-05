package com.deere.inventory.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.exception.GenericException;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.Stock;
import com.deere.model.dto.PartDto;
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

	public void warehouseOut(List<PartDto> stockList, Date entryDate) {

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

	public Inventory getInvbyPart(GenericPart part) {
		String query = "from Inventory where part.partCode = '"
				+ part.getPartCode() + "'";

		List<Inventory> invList = inventoryDao.query(query);
		Inventory inv = null;
		if (invList.size() == 0) {
			inv = new Inventory();
			inv.setPart(part);
			inv.setQuantity(0);
		} else {
			inv = invList.get(0);
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
			try {

				inventoryDao.merge(inv);
			} catch (Exception e) {
				GenericPart part = inv.getPart();
				part.setMachineType(PartType.self.getType());
				partDao.merge(inv.getPart());
				inventoryDao.merge(inv);
			}
		}

	}
}
