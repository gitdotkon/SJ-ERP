package com.deere.service;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.dao.GenericDao;
import com.deere.exception.GenericException;
import com.deere.model.BOMTree;
import com.deere.model.GenericPart;
import com.deere.model.json.BomJsonDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BOMServiceTest {
	@Autowired
	BOMService bomService;
	
	@Autowired
	private GenericDao<GenericPart> partDao;
	
	@Test
	public void testImportTree() throws GenericException {
//		fail("Not yet implemented");
		System.out.println(this.getClass().getClassLoader());
//		String
		File file = new File(this.getClass().getClassLoader().getResource("2-(6)949(0).xls").getPath());
		bomService.importTree(file, "xls");
	}
	
	@Test
	public void testImportTreeList() throws GenericException{
		bomService.importTree();
	}
	
	
	@Test
	public void testGetMachine(){
		List<GenericPart> machineList = bomService.getMachine("1");
		System.out.println(machineList.size());
		for (GenericPart gp : machineList) {
			System.out.println( gp.getPartCode()+""+gp.getPartName());
		}
	}
	
	@Test
	public void testGetChild(){
		GenericPart parent = new GenericPart();
		parent.setPartCode("111108YYYH");
		List<BOMTree> bomList = bomService.getBOM(parent);
		for (BOMTree bom : bomList) {
			System.out.println(bom.getChild().getPartCode()+ bom.getChild().getPartName()+" "+ bom.getDosage());
		}
	}
	
	@Test
	public void testGetJson(){
		GenericPart machine = partDao.findById("HD6-140SFZ(");
		List<BomJsonDto> bomList = bomService.getJson(machine,null,0,"0");
		System.out.println(bomList.size());
	}

}
