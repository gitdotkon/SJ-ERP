package com.deere.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.deere.dao.GenericDao;

/**
 * @ClassName: GenericDaoHibernateImpl
 * @Description: Implementation of GenericDao, A typesafe implementation of CRUD
 *               methods based on Hibernate
 * @author Gavin Li ligavin@johndeere.com
 * @date Aug 20, 2014 2:12:34 PM
 * 
 * @param <T>
 */
public class GenericDaoHibernateImpl<T> extends HibernateDaoSupport implements GenericDao<T> {

	private Class<T> type;

	private GenericDaoHibernateImpl(Class<T> type) {
		this.type = type;
	}

	public GenericDaoHibernateImpl() {
	}

	@Override
	public T findById(Serializable id) {
		return getHibernateTemplate().get(type, id);
	}

	@Override
	public void save(T object) {
		getHibernateTemplate().save(object);
	}

	@Override
	public void merge(T object) {
		getHibernateTemplate().merge(object);
	}

	@Override
	public void update(T object) {

		getHibernateTemplate().update(object);
	}

	@Override
	public void bulkUpdate(String hql) {
		getHibernateTemplate().bulkUpdate(hql);

	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findById(id));
	}

	@Override
	public void delete(T object) {
		getHibernateTemplate().delete(object);
	}

	@Override
	public List<T> findAll() {
		// getHibernateTemplate().l
		List<T> retList = getHibernateTemplate().find("from " + type.getName());
		if ((null == retList) || (retList.size() == 0)) {
			return new ArrayList<T>();
		} else {
			return retList;
		}
	}

	@Override
	public List<T> query(String queryString) {
		return getHibernateTemplate().find(queryString);
	}
	
	@Override
	public int getQuantityByCriteria(String criteria) {
		String query = "select sum(quantity) from " + type.getName() + criteria;
		
//		return ((Long) getHibernateTemplate().iterate(query).next()).intValue();
		
		Long count = (Long)getHibernateTemplate().find(query).listIterator().next();
//		System.out.println(count);
		if(count==null) return 0;
		return count.intValue();


	}
	
	

	@Override
	public int findCountByCriteria(String criteria) {
		String query = "select count(*) from " + type.getName() + criteria;
		return ((Long) getHibernateTemplate().iterate(query).next()).intValue();
	}

	@Override
	public List<T> findByPage(final String queryString, final int startIndex, final int pageSize) {
		List<T> list = getHibernateTemplate().executeFind(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				List<T> result = session.createQuery(queryString).setFirstResult(startIndex).setMaxResults(pageSize)
						.list();
				return (T) result;
			}
		});
		return list;
	}

	@Override
	public List<T> queryResultTransformer(final String queryString, final Class<T> targetObj) {
		List<T> list = getHibernateTemplate().executeFind(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				List<T> result = session.createQuery(queryString)
						.setResultTransformer(Transformers.aliasToBean(targetObj)).list();
				return (T) result;
			}
		});

		return list;
	}

	@Override
	public List<T> nativeQuery(final String query, final Class<T> targetObj) {
		List<T> list = getHibernateTemplate().executeFind(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				List<T> result = session.createSQLQuery(query).addEntity(targetObj).list();
				return (T) result;
			}
		});

		return list;
	}

}
