package com.sanmixy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sanmixy.dao.TwinsPartDao;
import com.sanmixy.model.Twins;
import com.sanmixy.utils.HibernateUtils;

public class TwinsPartDaoImpl extends HibernateDaoSupport implements TwinsPartDao{

	public void addTwinsPart(Twins twins) {
		
		this.getHibernateTemplate().save(twins);
		
	}

	public void deleteTwinsPart(Class clazz, Serializable serializable) {
		
		Twins t = (Twins) this.getHibernateTemplate().get(clazz, serializable);
		
		this.getHibernateTemplate().delete(t);
		
	}

	public List findTwinsByBasePartName(String wholeName) {
		
		Session session = null;
		Transaction t = null;
		
		try {
			session = HibernateUtils.getSession ();
			t = session.beginTransaction();
			
			Query query = session.createQuery("From Part part1, Part part2, Twins twins where part1.partName=:partName and part1.id = twins.basePart and part2.id=twins.twinPart");
			query.setString("partName", wholeName);
			
			List list = query.list();
			t.commit();
			
			return list;
		} catch (HibernateException e) {
			
			t.rollback();
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtils.closeSession(session);
		}
		
	}
	
	
}
