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

/**
 * 
 * @author Xia Yu
 * @vesion 1.0
 * @brief HeadPart DAO implements
 *
 */
public class HeadPartDaoImpl extends HibernateDaoSupport implements HeadPartDao {

	/**
	 * @brief Add method
	 * @param head
	 * 
	 */
	public void addHeadPart(HeadPart head) {
		
		this.getHibernateTemplate().save(head);
	}

	/**
	 * @param clazz
	 * @param serializable
	 * @brief Delete method
	 */
	public void deleteHeadPart(Class clazz, Serializable serializable) {
		
		HeadPart headPart = (HeadPart) this.getHibernateTemplate().get(clazz, serializable);
		
		this.getHibernateTemplate().delete(headPart);
		
	}

	/**
	 * @param value
	 * @brief Get the head node of a sequence.
	 * @return A list of parts
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
	
	/**
	 * @param type
	 * @brief Get the certain type of head node 
	 * @return A list of head parts
	 */
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

	/**
	 * @param partName
	 * @param value
	 * @brief Check whether the sequence is true or not
	 * @return true, false
	 */
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
