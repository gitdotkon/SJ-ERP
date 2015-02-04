package com.deere.service;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.exception.GenericException;
import com.deere.inventory.service.InventoryService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InventoryServiceTest {
	@Autowired
	private InventoryService invService;
	
	@Test
	public void test() throws GenericException {
//		fail("Not yet implemented");
		File file = new File(this.getClass().getClassLoader().getResource("test.xls").getPath());
		try {
			
			invService.importInventory(file, "xls");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}

}
