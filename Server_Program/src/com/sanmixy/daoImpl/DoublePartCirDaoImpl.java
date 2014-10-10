package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.DoublePartCirDao;
import com.sanmixy.model.DoublePartCir;
import com.sanmixy.utils.HibernateUtils;

public class DoublePartCirDaoImpl extends HibernateDaoSupport implements DoublePartCirDao{

	public void addDoublePartCir(DoublePartCir doublePartCir) {
		
		this.getHibernateTemplate().save(doublePartCir);
		
	}

	public void deleteDoublePartCir(Class clazz, Serializable serializable) {
		
		DoublePartCir doublePartCir = (DoublePartCir) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(doublePartCir);
		
	}

	public List getNextPart(String userCir, double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, DoublePartCir d where part1.partName=:partName and part1.id=d.first and part2.id=d.second and d.con>=:value order by d.con desc");
			query.setString("partName", userCir);
			query.setDouble("value", value);
			query.setFirstResult(0);
			query.setMaxResults(5);
			
			List list = query.list();
			transaction.commit();
			
			return list;
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return null;
		} finally {
			
			HibernateUtils.closeSession(session);
		}
	}
	
	public List getNextPart (String userCir, String type, double value){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, DoublePartCir d where part1.partName=:partName and part1.id=d.first and part2.id = d.second and part2.type=:type and d.con>=:value order by d.con desc");
			query.setString("partName", userCir);
			query.setString("type", type);
			query.setDouble("value", value);
			query.setFirstResult(0);
			query.setMaxResults(5);
			
			List list = query.list();
			transaction.commit();
			
			return list;
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return null;
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
		
		
	}

	public boolean isTrue(String[] userCir, double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, DoublePartCir d where part1.partName=:partName1 and part1.id=d.first and part2.partName=:partName2 and part2.id=d.second and d.con>=:value order by d.con desc");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setDouble("value", value);
			
			List list = query.list();
			transaction.commit();
			
			return 0 == list.size() ?
				   false :
				   true;
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return false;
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}


}
