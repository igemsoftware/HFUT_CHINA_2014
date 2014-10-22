package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.RecentlyUsedPartDao;
import com.sanmixy.model.Part;
import com.sanmixy.model.RecentlyUsedPart;
import com.sanmixy.utils.HibernateUtils;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief RecentlyUsedPart DAO implements
 *
 */
public class RecentlyUsedPartDaoImpl extends HibernateDaoSupport implements RecentlyUsedPartDao{

	/**
	 * @param record
	 * @brief Add method
	 */
	public void addRecord(RecentlyUsedPart record) {
		
		this.getHibernateTemplate().save(record);
	}

	/**
	 * @param clazz
	 * @param serializable
	 * @brief Delete method
	 */
	public void deleteRecord(Class clazz, Serializable serializable) {
		
		RecentlyUsedPart record = (RecentlyUsedPart) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(record);
		
	}

	
	/**
	 * @brief Get the parts that are used recently
	 * @return A list of recentlyUsedParts.
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

	/**
	 * @param type
	 * @brief Get the parts that are used recently and with a certain type
	 * @return A list of Object arrays
	 */
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
	
	/**
	 * @param userInfo
	 * @return A list of recentlyUsedPart
	 * @brief Get the part that are used recently and with a user information
	 */
	public List getRecentlyUsedPartByUserInfo (String userInfo){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part p, RecentlyUsedPart r where r.userInfo=:userInfo and p.id = r.part order by r.count desc");
			query.setString("userInfo", userInfo);
			
			List list = query.list();
			transaction.commit();
			
		
//			for (Iterator it = list.iterator();
//				 it.hasNext();
//				){
//				
//				Part p = (Part)(((Object[])it.next())[0]);
//				
//				System.out.println(p.getPartName());
//				
//				
//			}
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
	 * @param userInfo
	 * @return A list of object arrays
	 * @brief Get the part that are used recently ad with a certain user information 
	 */
	public List getRecentlyUsedPartByTypeAndUserInfo(String type, String userInfo){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where r.userInfo=:userInfo and r.part=part.id and part.type=:type order by r.datetime desc, r.count desc");
			query.setString("userInfo", userInfo);
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
	
	/**
	 * @param partName
	 * @brief Find the count of a part.
	 * @return count
	 */
	public int findCountByPartName (String partName){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where part.partName=:partName and part.id=r.part");
			query.setString("partName", partName);
			
			Iterator it = query.iterate();
			transaction.commit();
			if (it.hasNext()){
					
				RecentlyUsedPart r = (RecentlyUsedPart)(((Object[])it.next())[1]);
				
				return r.getCount();
				
			}else{
			
				return 0;
				
			}
		} catch (HibernateException e) {

			transaction.rollback();
			
			e.printStackTrace();
			
			return 0;
			
		} finally {
			
			HibernateUtils.closeSession(session);
		}
		
	}
	
	/**
	 * @param partName
	 * @param userInfo
	 * @brief Find count of a part with a part name and user information
	 * @return count
	 */
	public int findCountByPartNameAndUserInfo (String partName, String userInfo){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where r.userInfo=:userInfo and r.part=part.id and part.partName=:partName");
			query.setString("partName", partName);
			query.setString("userInfo", userInfo);
			
			Iterator it = query.iterate();
			transaction.commit();
			
			if (it.hasNext()){
				
				RecentlyUsedPart r = (RecentlyUsedPart) (((Object[])it.next())[1]);
				
				return r.getCount();
				
			}else{
			
				return 0;
				
			}
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return 0;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}
	
	/**
	 * @param partName
	 * @param userInfo
	 * @brief Find the id of a record with a partName and user information
	 * @return id
	 */
	public int findIDByPartNameAndUserInfo (String partName, String userInfo){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part, RecentlyUsedPart r where r.userInfo=:userInfo and r.part=part.id and part.partName=:partName");
			query.setString("partName", partName);
			query.setString("userInfo", userInfo);
			
			Iterator it = query.iterate();
			transaction.commit();
			
			if (it.hasNext()){
				
				RecentlyUsedPart r = (RecentlyUsedPart) (((Object[])it.next())[1]);
				
				return r.getId();
				
			}else{
			
				return 0;
				
			}
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
			return 0;
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
	}
	
	/**
	 * @param id
	 * @brief Delete record with a certain id
	 * 
	 */
	public void deleteRecordByID (int id){
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createSQLQuery("DELETE FROM RECENTLYUSEDPART WHERE ID=?");
			query.setInteger(0, id);
			query.executeUpdate();
			
			transaction.commit();
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
		} finally {
			
			HibernateUtils.closeSession(session);
		}
		
		
		
	}
	
}
