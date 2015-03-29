package com.deere.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.deere.dao.ProcedureDao;

public class ProcedureDaoImpl extends HibernateDaoSupport implements ProcedureDao {

	@Override
	@Transactional
	public void callProc(String proc, List<String> paras) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		// Transaction tx = session.beginTransaction();
		SQLQuery q = session.createSQLQuery("{call " + proc + " }");
		if (paras != null) {
			int i = 0;
			for (String para : paras) {
				q.setString(i++, para);
			}
		}
		q.executeUpdate();

	}
	
	@Override
	@Transactional
	public List callProcR(String proc, List<String> paras) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		// Transaction tx = session.beginTransaction();
		SQLQuery q = session.createSQLQuery("{call " + proc + "(?) }");
		if (paras != null) {
			int i = 0;
			for (String para : paras) {
				q.setString(i++, para);
			}
		}
		return q.list();

	}
	
	/*
	 * 
	 * 
	 * SQLQuery query = session.createSQLQuery("{Call proc(?)}");   
query.setString(0, 参数);   
List<Object[]> list =query.list();(non-Javadoc)
	 * @see com.deere.dao.ProcedureDao#callProcr(java.lang.String, java.util.List)
	 */
	/*@SuppressWarnings("unchecked")
	public ResultSet callProcr(final String proc, final List<String> paras) throws SQLException {
		// TODO Auto-generated method stub
		return getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				CallableStatement cstmt =session.connection().prepareCall("{call " + proc + "(?) }");
				if (paras != null) {
					int i = 1;
					for (final String para : paras) {
//						cstmt.registerOutParameter(i, java.sql.Types.INTEGER);
						cstmt.setString(i++, para);
						
					}
				}
				ResultSet rs = cstmt.ex
				while (rs.next())
				  {
				      System.out.println("<tr><td>" + rs.getString(7) + "</td><td>" + rs.getString(7) + "</td></tr>");
				  }
//				return (ResultSet) cstmt.e
				return null;
			}
			
	
			
		});
		*/
		
			/*
		 getHibernateTemplate().execute(new HibernateCallback()
		 {
		     public Object doInHibernate(Session session) throws HibernateException, SQLException
		     {
		  CallableStatement cstmt = session.connection().prepareCall("{ call prc_3(?) }");
		  cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
		  cstmt.execute();
		  ResultSet rs = (ResultSet) cstmt.getObject(1);
		  while (rs.next())
		  {
		      System.out.println("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td></tr>");
		  }
		  return null;
		     }
		 }, true);
		 */
	

	public void callProc1(final String proc, final List<String> paras) {

		getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery q = session.createSQLQuery("{call " + proc + " }");
				int i = 0;
				for (String para : paras) {
					q.setString(i++, para);
				}
				q.executeUpdate();
				return null;
			}
		});

	}
}
