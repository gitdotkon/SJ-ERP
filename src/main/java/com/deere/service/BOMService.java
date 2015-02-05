package com.deere.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.common.ExcelFileFilter;
import com.deere.dao.GenericDao;
import com.deere.exception.GenericException;
import com.deere.inventory.service.InventoryService;
import com.deere.model.BOMTree;
import com.deere.model.GenericPart;
import com.deere.model.Inventory;
import com.deere.model.dto.PartDto;
import com.deere.model.enums.PartType;
import com.deere.model.exdto.ExBOMTree;
import com.deere.model.json.BomJsonDto;
import com.deere.model.json.JsonCell;

@Service
public class BOMService {
	@Autowired
	private GenericDao<BOMTree> bomTreeDao;
	
	@Autowired
	private InventoryService invSerivice;
	
	@Autowired
	private GenericDao<GenericPart> partDao;
	
	private Integer id=1;
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public GenericPart getPart(String code){
		return partDao.findById(code);
	}
	
	
	
	public List<BomJsonDto> getJson(GenericPart machine,Integer index,int level,String prefix){
//		index++;
		List<BomJsonDto> bomList= new ArrayList<BomJsonDto>();
		BomJsonDto bomJson = new BomJsonDto();
		Integer tIndex = ++id;
		bomJson.setId(tIndex);
		List<GenericPart> childList= getChild(machine);
//		BomJsonDto bomJson= new BomJsonDto();
		JsonCell cell = new JsonCell();
		cell.setName(machine.getPartCode());
		cell.setLevel(level);
		cell.setParentId(index);
		cell.setIsLeaf(childList.size()==0);
		cell.setIsExpended(true);
		bomJson.setCell(cell.toList());
		
		bomList.add(bomJson);
			
		
		System.out.println(machine.getPartCode()+childList.size());
		if(childList.size()>0){
			int order=1;
			for (GenericPart child : childList) {
				bomList.addAll(getJson(child,tIndex,level+1,prefix+"."+order++));
			}

		}
		return bomList;
		
	}
	
	public List<BOMTree> getBOM(GenericPart parent){
		String query= "from BOMTree where parent='"+parent.getPartCode()+"'";
		List<GenericPart> childList = new ArrayList<GenericPart>();
		List<BOMTree> machineList = bomTreeDao.query(query);
		for (BOMTree bomTree : machineList) {
			childList.add(bomTree.getChild());
		}
		return machineList;
		
	}
	
	public List<GenericPart> getChild(GenericPart parent){
		String query= "from BOMTree where parent='"+parent.getPartCode()+"'";
		List<GenericPart> childList = new ArrayList<GenericPart>();
		List<BOMTree> machineList = bomTreeDao.query(query);
		for (BOMTree bomTree : machineList) {
			GenericPart child = bomTree.getChild();
			if(!"自制外购".contains(child.getPartType()))
				childList.add(child);
		}
		return childList;
		
	}
	
	
	
	
	
	
	

	public List<GenericPart> getMachine(String partIndex){
		
		String partType= PartType.getPartType(partIndex);
		String query= "from GenericPart where partType='"+partType+"'";
		List<GenericPart> machineList = partDao.query(query);
		
		return machineList;
	}
	
	public List<GenericPart> searchMachine(String partCode,String partIndex){
		
//		String partType= PartType.getPartType(partIndex);
		String query= "from GenericPart where partCode like '%"+partCode+"%'";
		if(!partIndex.equals("0")){
			String partType= PartType.getPartType(partIndex);
			query+=" and partType='"+partType+"'";
		}
		
		List<GenericPart> machineList = partDao.query(query);
		
		return machineList;
	}
	
public List<PartDto> searchMachine(String partCode,String partIndex,int pagesize){
		
//		String partType= PartType.getPartType(partIndex);
		String query= "from GenericPart where partCode like '%"+partCode+"%'";
		if(!partIndex.equals("0")){
			String partType= PartType.getPartType(partIndex);
			query+=" and partType='"+partType+"'";
		}
				List<GenericPart> machineList = partDao.findByPage(query, 0, pagesize);
		List<PartDto> dtoList = new ArrayList<PartDto>();
		for (GenericPart genericPart : machineList) {
			PartDto dto = new PartDto();
			BeanUtils.copyProperties(genericPart, dto);
			Inventory inv = invSerivice.getInvbyPart(genericPart);
			dto.setAddress(inv.getAddress());
			dto.setQuantity(inv.getQuantity());
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public void importTree() throws GenericException {
		File[] fileList = LoadFiles();
		for (File file : fileList) {
			try {
				
				importTree(file,"xls");
			} catch (Exception e) {
				e.printStackTrace();
				importTree(file,"xlsx");
			}
		}
	}

	public void importTree(File file, String pattern) throws GenericException {
		DataImportService<ExBOMTree, BOMTree> bomImport = new DataImportService<ExBOMTree, BOMTree>(
				ExBOMTree.class, BOMTree.class);

		List<BOMTree> bomList = bomImport.importSpreadsheet(file, pattern);
		String fileName= file.getName();
		fileName=fileName.substring(0,fileName.length()-4);
		for (BOMTree bomTree : bomList) {
			GenericPart parentPart = getParentPart(fileName);
			bomTree.setParent(parentPart);
			bomTreeDao.merge(bomTree);
		}

	}

	private GenericPart getParentPart(String fileName) {

		int i = 1;
		for (; i <=fileName.length(); i++) {
			String leftStr = fileName.substring(0, i);
			if (leftStr.length() < leftStr.getBytes().length)
				break;
		}
			
		String partCode = fileName.substring(0, i - 1);
		String partName = fileName.substring(i-1);
		if (i == fileName.length()) {
			partCode=fileName;
			partName="";
		}
		
		GenericPart part = partDao.findById(partCode);
		
		if(part!=null)
			return part;
		part= new GenericPart();
		part.setPartCode(partCode);
		part.setPartName(partName);
		part.setPartType("整机");
		partDao.save(part);
		return part;
		
	}

	
	private File[] LoadFiles(){
		File folder = new File("D:\\dummy");
		File[] fList =folder.listFiles(new ExcelFileFilter());
		return fList;
//		folder.lis
	}

}
