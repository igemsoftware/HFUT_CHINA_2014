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

public class DeviceDaoImpl extends HibernateDaoSupport implements DeviceDao{

	public void addDevice(Device device) {

		this.getHibernateTemplate().save(device);
		
	}

	public void deleteDevice(Class clazz, Serializable serializable) {
		
		Device device = (Device) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(device);
		
	}

	public Device findDeviceByID(Serializable serializable) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
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
			
			transaction.rollback ();
			
			e.printStackTrace();
			
			return null;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
		
	}

	public Device findDeviceByName(String deviceName) {
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

	public List findDeviceByPartSeq(String partNameSeq) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, Device device where device.part_seq like ? and part.id=device.device");
			query.setString(0,"%"+partNameSeq+"%");
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
