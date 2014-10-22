package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.PentaPartCirDao;
import com.sanmixy.model.PentaPartCir;
import com.sanmixy.utils.HibernateUtils;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief PentaPartCir DAO implements
 *
 */
public class PentaPartCirDaoImpl extends HibernateDaoSupport implements PentaPartCirDao{

	/**
	 * @param pentaPartCir
	 * @brief Add method
	 */
	public void addPentaPartCir(PentaPartCir pentaPartCir) {
		
		this.getHibernateTemplate().save(pentaPartCir);
	}

	/**
	 * @param clazz
	 * @param serializable
	 * @brief Delete method
	 */
	public void deletePentaPartCir(Class clazz, Serializable serializable) {
		
		PentaPartCir pentaPartCir = (PentaPartCir) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(pentaPartCir);
		
		
	}
	
	/**
	 * @param userCir
	 * @param value
	 * @brief Get the next part by analysing the current part sequence
	 * @return A list of parts.
	 */
	public List getNextPart(String[] userCir, double value) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, Part part4, Part part5, PentaPartCir p where part1.partName=:partName1 and part1.id=p.first and part2.partName=:partName2 and part2.id=p.second and part3.partName=:partName3 and part3.id=p.third and part4.partName=:partName4 and part4.id=p.forth and part5.id=p.fifth and p.con>=:value order by p.con desc");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setString("partName3", userCir[2]);
			query.setString("partName4", userCir[3]);
			query.setDouble("value", value);
			
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
	 * @param value
	 * @brief Get the next part by analysing the current part sequence and the type of parts
	 * @return A list of parts.
	 */
	public List getNextPart (String[] userCir, String type, double value){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, Part part4, Part part5, PentaPartCir p where part1.partName=:partName1 and part1.id=p.first and part2.partName=:partName2 and part2.id=p.second and part3.partName=:partName3 and part3.id=p.third and part4.partName=:partName4 and part4.id=p.forth and part5.id=p.fifth and part5.type=:type and p.con>=:value order by p.con desc");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setString("partName3", userCir[2]);
			query.setString("partName4", userCir[3]);
			query.setString("type", type);
			query.setDouble("value", value);
			query.setFirstResult(0);
			query.setMaxResults(5);
			
			List list = query.list();
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
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, Part part4, Part part5, PentaPartCir p where part1.partName=:partName1 and part1.id=p.first and part2.partName=:partName2 and part2.id=p.second and part3.partName=:partName3 and part3.id=p.third and part4.partName=:partName4 and part4.id=p.forth and part5.partName=:partName5 and part5.id=p.fifth and p.con>=:value");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setString("partName3", userCir[2]);
			query.setString("partName4", userCir[3]);
			query.setString("partName5", userCir[4]);
			query.setDouble("value", value);
			
			List list = query.list();
			
			transaction.commit ();
			
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
