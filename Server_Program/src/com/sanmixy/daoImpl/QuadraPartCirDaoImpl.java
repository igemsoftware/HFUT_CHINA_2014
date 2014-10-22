package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.QuadraPartCirDao;
import com.sanmixy.model.QuadraPartCir;
import com.sanmixy.utils.HibernateUtils;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief QuadraPartCir DAO implements
 *
 */
public class QuadraPartCirDaoImpl extends HibernateDaoSupport implements QuadraPartCirDao{

	/**
	 * @param quadraPartCir
	 * @brief Add method
	 */
	public void addQuadraPartCir(QuadraPartCir quadraPartCir) {
		
		this.getHibernateTemplate().save(quadraPartCir);
		
	}

	/**
	 * @param clazz
	 * @param serializable
	 * @brief Delete method
	 * 
	 */
	public void deleteQuadraPartCir(Class clazz, Serializable serializable) {
		
		QuadraPartCir quadraPartCir = (QuadraPartCir) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(quadraPartCir);
		
		
	}

	/**
	 * @param userCir
	 * @param value
	 * @brief Get next part by analysing current part sequence
	 * @return A list of part arrays
	 */
	public List getNextPart(String[] userCir, double value) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, Part part4, QuadraPartCir q where part1.partName=:partName1 and part1.id=q.first and part2.partName=:partName2 and part2.id=q.second and part3.partName=:partName3 and part3.id=q.third and part4.id=q.forth and q.con>=:value order by q.con desc");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setString("partName3", userCir[2]);
			query.setDouble("value", value);
			query.setFirstResult(0);
			query.setMaxResults(5);
			
			List list = query.list();
			
			transaction.commit ();
			
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
	 * @param userCir
	 * @param type
	 * @param value
	 * @brief Get next by analysing the current part sequence and the type of parts
	 * @return A list of part arrays.
	 */
	public List getNextPart (String[] userCir, String type, double value){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, Part part4, QuadraPartCir q where part1.partName=:partName1 and part1.id=q.first and part2.partName=:partName2 and part2.id=q.second and part3.partName=:partName3 and part3.id=q.third and part4.id=q.forth and part4.type=:type and q.con>=:value order by q.con desc");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setString("partName3", userCir[2]);
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
	
	/**
	 * @param userCir
	 * @param value
	 * @brief Check whether the sequence is true or not
	 * @return true, false
	 */
	public boolean isTrue(String[] userCir, double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, Part part4, QuadraPartCir q where part1.partName=:partName1 and part1.id=q.first and part2.partName=:partName2 and part2.id=q.forth and part3.partName=:partName3 and part3.id=q.third and part4.partName=:partName4 and part4.id=q.forth and q.con>=:value");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setString("partName3", userCir[2]);
			query.setString("partName4", userCir[3]);
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
