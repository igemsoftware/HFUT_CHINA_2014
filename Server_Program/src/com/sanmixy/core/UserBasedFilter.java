package com.sanmixy.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sanmixy.model.RecentlyUsedPart;
import com.sanmixy.utils.HibernateUtils;

/**
 * 
 * @author Xia Yu
 * @brief A filter based on user behaviors
 * @version 1.0
 */
public class UserBasedFilter {

	/**< The use that we want to analyse */
	private HashMap targetUserDataMap;
	
	/**< The user list except the target user */
	private List userList;
	
	/**
	 * @brief Pearson data of two map
	 * @param map1
	 * @param map2
	 * @return pearson data
	 */
	public double pearson (HashMap map1, HashMap map2){
		
		double xy = 0;
		double x = 0;
		double y = 0;
		double x2 = 0;
		double y2 = 0;
		int n = 0;
		
		Set<Entry<String, Double> > entries1 = map1.entrySet();
		Set<Entry<String, Double> > entries2 = map2.entrySet();
		
		for (Entry <String, Double> entry : entries1){
			
			if (map2.containsKey(entry)){
				double tempX = entry.getValue();
				double tempY = (Double) map2.get(entry.getKey());
				n ++;
				xy += tempX*tempY;
				x += tempX;
				y += tempY;
				x2 += tempX*tempX;
				y2 += tempY*tempY;
				
			}
		}
		
		if (0 == n){
			return 0;
		}else{
			
			double sx = Math.sqrt(x2 - (Math.pow(x, 2)/n));
			double sy = Math.sqrt(y2 - (Math.pow(y, 2)/n));
			
			if (0 != sx && 0 != sy){
				return (xy - x*y)/sx/sy;
			}else{
				return 0;
			}
		}
		
	}
	
	/**
	 * @brief Get all user except the current user.
	 * @param userInfo
	 */
	public void getAllUser (String userInfo){
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtils.getSession();
			transaction = session.beginTransaction();
			
			Query query = session.createSQLQuery("SELECT DISTINCT(IPADDRESS) FROM RECENTLYUSEDPART WHERE IPADDRESS <> '?'");
			query.setString(0, userInfo);
			userList = query.list();
		} catch (HibernateException e) {
			
			transaction.rollback();
			
			e.printStackTrace();
			
		} finally {
			
			HibernateUtils.closeSession(session);
			
		}
		
	}
	
	/**
	 * @brief Get target user data
	 * @param userInfo
	 */
	public void getTargetUserData (String userInfo){
		
		Session session = null;
		Transaction transaction = null;
		
		session = HibernateUtils.getSession();
		transaction = session.beginTransaction();
		
		Query query = session.createQuery("From RecentlyUsedPart r where r.ipAddress=:ipAddress");
		List targetUserDataList = query.list();
		
		targetUserDataMap = new HashMap ();
		for (Iterator tIter = targetUserDataList.iterator();
			 tIter.hasNext();
			){
			
			RecentlyUsedPart r = (RecentlyUsedPart) tIter.next();
			
			String key = new String().valueOf(r.getPart().getPartName());
			
			int value = r.getCount();
			
			targetUserDataMap.put(key, value);
			
		}
		
	}
	
	
	
}
