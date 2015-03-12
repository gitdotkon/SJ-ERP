package com.deere.manufacture.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.dao.GenericDao;
import com.deere.model.GenericPart;
import com.deere.model.MPRModel;
import com.deere.model.SalesOrder;
import com.deere.sales.service.SalesOrderService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MPRServiceTest {
	@Autowired
	private MPRService mpr;
	
	@Autowired
	private SalesOrderService SOService;
	
	@Autowired
	private GenericDao<GenericPart> partDao;
	
	@Test
	public void testGetMPR() {
//		fail("Not yet implemented");
		GenericPart parent = partDao.findById("HD6-140SFY(");
		Map<String,Integer> mprList =mpr.getMPR(parent, 1);
//		Map<String,Integer> childMap=getMPR(child,bomTree.getDosage());
		Iterator<String> it =  mprList.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key+":"+mprList.get(key));
		}
	}
	
	@Test
	public void testRunMPR(){
		List<SalesOrder> SOList= SOService.findUnplannedOrder("");
		mpr.runMPR(SOList);
		
	}
	
	@Test
	public void testListorder(){
		List<MPRModel> mprm=mpr.listOrderforPart("BSL-01");
		for (MPRModel mprModel : mprm) {
			System.out.println(mprModel.getSalesOrder().getOrderNum()+"/"+mprModel.getPart().getPartCode()
					+":"+mprModel.getRequiredQty());
		}
//		2-(6)923(B)
	}
}
