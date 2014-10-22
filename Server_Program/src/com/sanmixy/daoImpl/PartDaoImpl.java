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

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Part DAO implements
 *
 */
public class PartDaoImpl extends HibernateDaoSupport implements PartDao{

	/**
	 * @param part
	 * @brief Add method
	 */
	public void addPart(Part part) {
		
		this.getHibernateTemplate().save(part);
		
	}

	/**
	 * @param clazz
	 * @param serializable
	 * @brief Delete method
	 */
	public void deletePart(Class clazz, Serializable serializable) {
		
		Part part = (Part) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(part);
		
	}

	/**
	 * @param serializable
	 * @brief Find part by record id
	 * @return A list of parts.
	 */
	public Part findPartByID(Serializable serializable) {
		
		Part part = (Part) this.getHibernateTemplate().get(Part.class, serializable);
		
		return part;
		
	}
	
	
	/**
	 * @param partID
	 * @brief Find part by part id
	 * @return part
	 */
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

	/**
	 * @param wholeName
	 * @brief Find part by a whole name
	 * @return part
	 */
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

	/**
	 * @param partName
	 * @brief Find part by a name string.
	 * @return A list of parts.
	 */
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

	/**
	 * @param type
	 * @param num
	 * @brief Find a certain type of type
	 * @return A list of parts.
	 */
	public List findPartByType (String type, int num){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part where part.type=:type");
			query.setString("type", type);
			query.setFirstResult(0);
			if (num > 0){
				query.setMaxResults(num);
			}
			
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
