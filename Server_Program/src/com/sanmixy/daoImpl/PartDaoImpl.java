package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.PartDao;
import com.sanmixy.model.Part;
import com.sanmixy.utils.HibernateUtils;

public class PartDaoImpl extends HibernateDaoSupport implements PartDao{

	public void addPart(Part part) {
		
		this.getHibernateTemplate().save(part);
		
	}

	public void deletePart(Class clazz, Serializable serializable) {
		
		Part part = (Part) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(part);
		
	}

	public Part findPartByID(Serializable serializable) {
		
		Part part = (Part) this.getHibernateTemplate().get(Part.class, serializable);
		
		return part;
		
	}
	
	

	public Part findPartByPartID(int partID) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part where part.partID=:partID");
			query.setInteger("partID", partID);
			
			Iterator iter = query.iterate();
			
			transaction.commit();
			if (iter.hasNext()){
				
				Part part = (Part) iter.next();
				
				return part;
			}
			
			return null;
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return null;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}

	public Part findPartByWholePartName(String wholeName) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part where part.partName=:wholeName");
			query.setString("wholeName", wholeName);
			
			Iterator iter = query.iterate();
			transaction.commit();
			
			if (iter.hasNext()){
				
				Part part = (Part) iter.next();
			
				return part;
			}
			
			return null;
		} catch (HibernateException e) {
			
			transaction.commit();
			
			e.printStackTrace();
			
			return null;
		} finally {
			
			HibernateUtils.closeSession(session);
		}
		
	}

	public List findPartByPartName(String partName) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part where part.partName like ?");
			query.setString(0,"%"+partName+"%");
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
