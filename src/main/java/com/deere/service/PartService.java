package com.deere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.model.GenericPart;

@Service
public class PartService {

	@Autowired
	private GenericDao<GenericPart> partDao;
	
	
}
