package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.DeviceDao;
import com.sanmixy.model.Device;
import com.sanmixy.utils.HibernateUtils;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Device Dao implements
 *
 */
public class DeviceDaoImpl extends HibernateDaoSupport implements DeviceDao{

	/**
	 * @brief Add method
	 * @param device
	 */
	public void addDevice(Device device) {

		this.getHibernateTemplate().save(device);
		
	}

	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public void deleteDevice(Class clazz, Serializable serializable) {
		
		Device device = (Device) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(device);
		
	}

	/**
	 * @param serializable
	 * @brief Find Device by record id.
	 * @exception HibernateException
	 */
	public Device findDeviceByID(Serializable serializable) {
		
		/**< Create Hibernate Session and Transaction */
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			/**< Create a query to execute the HQL command*/
			Query query = session.createQuery ("From Device device where device.id=:id");
			query.setString("id", new String().valueOf(serializable));
			
			Iterator iter = query.iterate();
			transaction.commit();
			
			if (iter.hasNext()){
				
				Device device = (Device) iter.next();
				
				return device;
				
			}else {
				
				return null;
			}
		} catch (HibernateException e) {
			
			/**< Rollback in the database */
			transaction.rollback ();
			
			e.printStackTrace();
			
			return null;
			
		} finally {
			
			/**< Close session */
			HibernateUtils.closeSession(session);
			
		}
		
	}

	/**
	 * @param deviceName
	 * @brief Find Device by device name.
	 * @exception Hibernate Exception
	 */
	public Device findDeviceByName(String deviceName) {
		/**< Create a Hibernate session and transaction */
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery ("From Device device where device.deviceName=:deviceName");
			query.setString("deviceName", deviceName);
			
			Iterator iter = query.iterate();
			transaction.commit();
			
			if (iter.hasNext()){
				
				Device device = (Device) iter.next();
				
				return device;
				
			}else {
				
				return null;
			}
		} catch (HibernateException e) {
			
			transaction.rollback ();
			
			e.printStackTrace();
			
			return null;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}

	/**
	 * @brief Find Device by part Name Sequence
	 * @param partNameSeq
	 * 
	 * Create a hibernate session and transaction, then create a query object
	 * to execute HQL command, and return the answer to users.
	 */
	public List findDeviceByPartSeq(String partNameSeq) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, Device device where device.part_seq like ? and part.id=device.device");
			query.setString(0,"%"+partNameSeq+"%");
			query.setFirstResult(0);
			query.setMaxResults(20);
			
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
	 * @brief Check whether the device is exist or not
	 * @param partName
	 * @exception Hibernate Exception
	 */
	public boolean isDeviceExist(String partName) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, Device device where part.partName=:partName and part.id=device.device");
			query.setString("partName", partName);
			
			Iterator it = query.iterate();
			transaction.commit();
			
			return it.hasNext();
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return false;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}
	
	/**
	 * @brief Check whether the part sequence is exist or not
	 * @param part_seq
	 * @exception HibernateException
	 */
	public boolean isSeqExist (String part_seq){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Device device where device.part_seq=:part_seq");
			query.setString("part_seq", part_seq);
			
			Iterator it = query.iterate();
			transaction.commit();
			
			return it.hasNext();
			
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return false;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}
}
