package com.deere.manufacture.service;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.model.dto.ProductionDto;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProductionServiceTest {
	@Autowired
	ProductionService proService;
	@Test
	public void testGeneratePlan() {
//		fail("Not yet implemented");
		List<ProductionDto> proDtoList = new ArrayList<ProductionDto>();
		ProductionDto proDto = new ProductionDto();
		proDto.setPartCode("test");
		proDto.setActualQty(10);
		proDto.setQuantity(100);
		proDtoList.add(proDto);
		proService.generatePlan(proDtoList, "100");
	}

}
