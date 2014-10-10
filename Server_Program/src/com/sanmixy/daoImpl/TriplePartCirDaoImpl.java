package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.TriplePartCirDao;
import com.sanmixy.model.TriplePartCir;
import com.sanmixy.utils.HibernateUtils;

public class TriplePartCirDaoImpl extends HibernateDaoSupport implements TriplePartCirDao {

	public void addTriplePartCir(TriplePartCir triplePartCir) {
		
		this.getHibernateTemplate().save(triplePartCir);
		
	}

	public void deleteTriplePartCir(Class clazz, Serializable serializable) {
		
		TriplePartCir triplePartCir = (TriplePartCir) this.getHibernateTemplate().load(clazz, serializable);
		
		this.getHibernateTemplate().delete(triplePartCir);
		
	}

	public List getNextPart(String[] userCir, double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, TriplePartCir t where part1.partName=:partName1 and part1.id=t.first and part2.partName=:partName2 and part2.id=t.second and part3.id=t.third and t.con>=:value order by t.con desc");
			query.setString ("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
			query.setDouble("value", value);
			
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
	
	public List getNextPart (String[] userCir, String type, double value){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, TriplePartCir t where part1.partName=:partName1 and part1.id=t.first and part2.partName=:partName2 and part2.id=t.second and part3.id=t.third and part3.type=:type and t.con>=:value order by t.con desc");
			query.setString("partName1", userCir[0]);
			query.setString("partName2", userCir[1]);
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

	public boolean isTrue(String[] userCir, double value) {
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Part part3, TriplePartCir t where part1.partName=:partName1 and part1.id=t.first and part2.partName=:partName2 and part2.id=t.second and part3.partName3=:partName3 and part3.id=t.third and t.con>=value");
			query.setString ("partName1", userCir[0]);
			query.setString ("partName2", userCir[1]);
			query.setString ("partName3", userCir[2]);
			query.setDouble ("value", value);
			
			List list = query.list ();
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
