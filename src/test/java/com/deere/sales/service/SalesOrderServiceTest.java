package com.deere.sales.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.dao.GenericDao;
import com.deere.model.Customer;
import com.deere.model.GenericPart;
import com.deere.model.SalesOrder;
import com.deere.model.SalesOrderItem;
import com.deere.model.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SalesOrderServiceTest {
	
	@Autowired
	private SalesOrderService SOService;
	@Autowired
	private GenericDao<GenericPart> partDao;
	
	@Autowired
	private GenericDao<SalesOrder> SODao;
	
	@Autowired
	private GenericDao<Customer> customerDao;
	
	@Test
	public void testAddCustomer(){
		Customer cus = new Customer("huaxing", "huang", "189776344", "广州" );
		customerDao.merge(cus);
	}
	
	@Test
	public void testGenerateOrder() {
		SalesOrder SO =  new SalesOrder("huaxingNO.1");
//		SO.setCustomer(new Customer("huaxing","huangguoxiong","147654452","guangdong"));
//		SO.setOrderNum();
		SO.setOrderDate(new Date());
		SO.setDeliveryDate(new Date());
		SO.setNotes("SEW");
//		SO.setSales(new User("testdotkon"));
		
//		Set<SalesOrderItem> SOItems = SO.getOrderItems();
		
		SalesOrderItem SOItem= new SalesOrderItem();
		GenericPart part = new GenericPart();
		part.setPartCode("H-JPT5.5-02-00B");
		SOItem.setPart(part);
		SOItem.setQuantity(10);
		SOItem.setPrice(new Float(1000));
		SO.addOrderItem(SOItem);
		
		SalesOrderItem SOItem1= new SalesOrderItem();
		GenericPart part1 = new GenericPart();
		part1.setPartCode("H-JPT5.5-03-00");
		SOItem1.setPart(part1);
		SOItem1.setQuantity(101);
		SOItem1.setPrice(new Float(10001));
		SO.addOrderItem(SOItem1);
		SOService.generateOrder(SO);
		
	}
	
	
	@Test
	public void testUpdateOrder() {
		SalesOrder SO = SODao.findById("huaxingNO.2");
		SO.setCustomer(new Customer("huaxing","huangguoxiong","147654452","guangdong"));
		SO.setOrderNum("huaxingNO.1");
		SO.setOrderDate(new Date());
		SO.setDeliveryDate(new Date());
		SO.setNotes("SEW");
		SO.setSales(new User("testdotkon"));
		
		SalesOrderItem SOItem= new SalesOrderItem();
		GenericPart part = partDao.findById("H-JPT5.5-02-00B");
		SOItem.setPart(part);
		SOItem.setQuantity(10);
		SOItem.setPrice(new Float(1000));
		SO.addOrderItem(SOItem);
		
		SalesOrderItem SOItem1= new SalesOrderItem();
		GenericPart part1 = partDao.findById("H-JPT5.5-03-00");
		SOItem1.setPart(part1);
		SOItem1.setQuantity(101);
		SOItem1.setPrice(new Float(10001));
		SO.addOrderItem(SOItem1);
		SOService.updateOrder(SO);
		
	}
	
	/*@Test
	public void testfindAll() {
		List<SalesOrderItem> SOItemList= SOService.findOrder();
		for (SalesOrderItem salesOrderItem : SOItemList) {
			System.out.println(salesOrderItem.getSalesOrder().getOrderNum() + salesOrderItem.getPart().getPartCode()+"	:"+salesOrderItem.getQuantity());
		}
	}*/
}
