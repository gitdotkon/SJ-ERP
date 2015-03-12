package com.deere.manufacture.service;


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
		List<ProductionDto>  proList= proService.productionPlan();
		System.out.print(proList.size());
		for (ProductionDto productionDto : proList) {
			System.out.println(productionDto.getPartName() + productionDto.getRequiredQty());
		}
		
	}

}
