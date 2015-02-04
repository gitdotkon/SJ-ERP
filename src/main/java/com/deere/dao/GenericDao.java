package com.deere.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: GenericDao
 * @Description: The basic GenericDao interface with CRUD methods
 * @author Gavin Li ligavin@johndeere.com
 * @date Aug 20, 2014 10:37:47 AM
 * 
 * @param <T> Model type
 */
public interface GenericDao<T> {

	/**
	 * @Title: findById
	 * @Description: Retrieve an object that was previously persisted to the database using the indicated id as primary
	 *               key
	 * @param id
	 * @return the model
	 * @throws
	 */
	public T findById(Serializable id);

	/**
	 * @Title: save
	 * @Description: Persist the new instance object into database
	 * @param model object
	 * @return void
	 * @throws
	 */
	public void save(T object);

	/**
	 * @Title: merge
	 * @Description: Persist the new instance object into database
	 * @param model object
	 * @return void
	 * @throws
	 */
	public void merge(T object);

	/**
	 * @Title: update
	 * @Description: Save changes made to a persisted object
	 * @param object
	 * @return void
	 * @throws
	 */
	public void update(T object);

	/**
	 * @Title: bulkUpdate
	 * @Description: bulk update to the database
	 * @param @param hql
	 * @return void
	 * @throws
	 */
	public void bulkUpdate(String hql);

	/**
	 * @Title: delete
	 * @Description: Remove an object from persistent storage using the indicated id as primary key
	 * @param id
	 * @return void
	 * @throws
	 */
	public void delete(Serializable id);

	/**
	 * @Title: delete
	 * @Description: Remove an object from persistent storage in the database
	 * @param object
	 * @return void
	 * @throws
	 */
	public void delete(T object);

	/**
	 * @Title: findAll
	 * @Description: Find all records from database
	 * @param
	 * @return List<T>
	 * @throws
	 */
	public List<T> findAll();

	/**
	 * @Title: query
	 * @Description: Find data from database by native query
	 * @param queryString
	 * @param
	 * @return List<T>
	 * @throws
	 */
	public List<T> query(String queryString);

	/**
	 * @Title: findCountByCriteria
	 * @Description: find count of records from datatables
	 * @param criteria
	 * @param
	 * @return int
	 * @throws
	 */
	public int findCountByCriteria(final String criteria);

	/**
	 * @Title: findByPage
	 * @Description: Returns a list of entities meeting the paging restriction provided in the PaginationSupport object
	 * @param queryString
	 * @param offset
	 * @param pageSize
	 * @return List<T>
	 * @throws
	 */
	public List<T> findByPage(final String queryString, final int offset, final int pageSize);

	/**
	 * @Title: queryResultTransformer
	 * @Description: Transfer the query result to target object
	 * @param queryString
	 * @param argetObj
	 * @return List<T>
	 * @throws
	 */
	public List<T> queryResultTransformer(String queryString, Class<T> targetObj);

	/**
	 * @Title: nativeQuery
	 * @Description: User native sql and transfer the query result to target object
	 * @param @param query
	 * @param @param targetObj
	 * @param @return
	 * @return List<T>
	 * @throws
	 */
	public List<T> nativeQuery(String query, Class<T> targetObj);

	public int getQuantityByCriteria(String criteria);

}
