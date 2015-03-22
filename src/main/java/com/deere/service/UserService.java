package com.deere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.base.crypto.HashCrypt;
import com.deere.dao.GenericDao;
import com.deere.model.User;
@Service
public class UserService {
	@Autowired
	private GenericDao<User> userDao;
	
	public Boolean verifyUser(User user){
		String cypherPass = HashCrypt.getDigestHash(user.getPassword());
		try {
			
			String realPass= userDao.findById(user.getUserName()).getPassword();
			if(realPass.equals(cypherPass))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public void createOrUpdateUser(User user){
		user.setPassword(HashCrypt.getDigestHash(user.getPassword()));
		userDao.merge(user);
	}

}
