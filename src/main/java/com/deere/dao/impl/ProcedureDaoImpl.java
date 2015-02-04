package com.deere.dao.impl;

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
