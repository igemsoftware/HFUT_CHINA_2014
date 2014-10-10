package com.sanmixy.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class HibernateUtils {

	private static SessionFactory sessionFactory;
	
	private static BeanFactory beanFactory;
	
	static {
		
		Configuration configuration = new Configuration ().configure ();
		
		sessionFactory = configuration.buildSessionFactory();
		
		beanFactory = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		
	}
	
	private HibernateUtils (){
		
	}
	
	public static Object getBean (String beanName){
		
		return beanFactory.getBean(beanName);
		
	}
	
	public static SessionFactory getSessionFactory (){
		
		return sessionFactory;
		
	}
	
	public static Session getSession (){
		
		return sessionFactory.openSession();
		
	}
	
	public static void closeSession (Session session) {
		
		try {
			if (null != session){
				
				if (session.isOpen()){
					
					session.close();
					
				}
				
			}
		} catch (HibernateException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
