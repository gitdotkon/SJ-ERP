package com.deere.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deere.dao.GenericDao;
import com.deere.model.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	@Autowired
	private GenericDao<User> userDao;
	
	@Test
	public void testVerifyUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateOrUpdateUser() {
//		fail("Not yet implemented");
		User user = new User();
		user.setPassword("password");
		user.setUserName("system");
		userService.createOrUpdateUser(user);
		User user2 = new User();
		user2.setPassword("password");
		user2.setUserName("testdotkon");
		assertEquals(true, userService.verifyUser(user2));
		
//		assertEquals("5bd5619bb6c63f3f06fd250b6c87331b7e99f0a7", SHA);		
	}

}
