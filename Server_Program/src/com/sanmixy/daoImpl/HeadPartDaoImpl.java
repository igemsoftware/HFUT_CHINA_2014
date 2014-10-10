package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.HeadPartDao;
import com.sanmixy.model.HeadPart;
import com.sanmixy.utils.HibernateUtils;

public class HeadPartDaoImpl extends HibernateDaoSupport implements HeadPartDao {

	public void addHeadPart(HeadPart head) {
		
		this.getHibernateTemplate().save(head);
	}

	public void deleteHeadPart(Class clazz, Serializable serializable) {
		
		HeadPart headPart = (HeadPart) this.getHibernateTemplate().get(clazz, serializable);
		
		this.getHibernateTemplate().delete(headPart);
		
	}

	/**
	 * @return Object array of Part and HeadPart
	 */
	public List getHeadPart(double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, HeadPart headPart where part.id = headPart.first and headPart.con >=:value order by headPart.con desc");
			query.setDouble("value", value);
			query.setFirstResult(0);
			query.setMaxResults(10);
			
			List list = query.list();
			transaction.commit();
			
			return list;
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return null;
		} finally{
			
			HibernateUtils.closeSession(session);
		}
	}
	
	public List getHeadPart (String type, double value){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, HeadPart headPart where part.id=headPart.first and part.type=:type and headPart.con>=:value order by headPart.con desc");
			query.setString ("type", type);
			query.setDouble("value", value);
			query.setFirstResult(0);
			query.setMaxResults(10);
			
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

	public boolean isTrue(String partName, double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, HeadPart headPart where part.partName =:partName and part.id=headPart.first and headPart.con>=:value");
			query.setString("partName", partName);
			query.setDouble("value", value);
			
			List list = query.list();
			transaction.commit();
			
			if (0 != list.size())
				
				return true;
			
			return false;
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return false;
		} finally{
			
			HibernateUtils.closeSession(session);
			
		}
	}
		
}
