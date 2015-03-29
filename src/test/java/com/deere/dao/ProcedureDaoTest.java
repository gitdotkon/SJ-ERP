package com.deere.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.model.dto.PlanDto;
import com.deere.model.dto.ProductionDto;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProcedureDaoTest {
	@Autowired
	GenericDao<PlanDto> planDao;
	@Test
	public void test() throws SQLException {
		List<String> sList= new ArrayList<String>();
		sList.add("erp2");
		List<PlanDto> l =planDao.callProcR("proc_plan", sList,PlanDto.class);
		for (PlanDto dto : l) {
//			System.out.println(mrpModel.getPart().getPartCode()+":"+mrpModel.getSalesOrder().getOrderNum());
			System.out.println(dto.getPartCode()+":"+dto.getRequiredQty()+":"+dto.getProcessingQty()+":"+dto.getReservedQty());
		}
		System.out.println(l.size());
		
	}

}
