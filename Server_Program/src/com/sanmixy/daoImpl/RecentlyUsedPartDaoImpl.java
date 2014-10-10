package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.RecentlyUsedPartDao;
import com.sanmixy.model.RecentlyUsedPart;
import com.sanmixy.utils.HibernateUtils;

public class RecentlyUsedPartDaoImpl extends HibernateDaoSupport implements RecentlyUsedPartDao{

	public void addRecord(RecentlyUsedPart record) {
		
		this.getHibernateTemplate().save(record);
	}

	public void deleteRecord(Class clazz, Serializable serializable) {
		
		RecentlyUsedPart record = (RecentlyUsedPart) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(record);
		
	}

	/*
	 * try to find a better rule in order to balance the influences of the frequent and date;
	 * maybe e^2?
	 */
	public List getRecentlyUsedPart() {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createSQLQuery("From RecentlyUsedPart r order by r.datetime desc,r.count desc");
			query.setFirstResult(1);
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

	public List getRecentlyUsedPartByType(String type) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where part.type=:type and part.id=r.part order by r.datetime desc,r.count desc");
			query.setString("type", type);
			
			query.setFirstResult(1);
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
	
	public List getRecentlyUsedPartByIp (String ipAddress){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where r.ipAddress=:ipAddress and part.id=r.part order by r.datetime desc, r.count desc");
			query.setString("ipAddress", ipAddress);
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
	
	public List getRecentlyUsedPartByTypeAndIp (String type, String ipAddress){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where r.ipAddress=:ipAddress and r.part=part.id and part.type=:type order by r.datetime desc, r.count desc");
			query.setString("ipAddress", ipAddress);
			query.setString("type", type);
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
	
}
