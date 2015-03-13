package com.deere.dao.impl;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class NormalDao extends HibernateDaoSupport{
	public List getProperty(String query){
		
		 Query q = this.getSession().createQuery(query);
		
		return objList;
		
	}
}
