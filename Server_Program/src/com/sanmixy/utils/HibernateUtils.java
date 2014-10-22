package com.sanmixy.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Some hibernate utils are offered such as Session getter, SessionFactory getter, Bean getter, etc. 
 *
 */
public final class HibernateUtils {

	/**< The factory class of session. It is safe while you are using it in thread*/
	private static SessionFactory sessionFactory;
	
	/**< The factory class of bean. It will offer you all the DAO beans if you tell it the name of bean*/
	private static BeanFactory beanFactory;
	
	/**
	 * @brief Scan the files named hibernate.cfg.xml and applicationContext-*.xml, then initial hibernate and spring, in order to create session factory. 
	 */
	static {
		
		Configuration configuration = new Configuration ().configure ();
		
		sessionFactory = configuration.buildSessionFactory();
		
		beanFactory = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		
	}
	
	/**
	 * @brief Constructor
	 */
	private HibernateUtils (){
		
	}
	
	/**
	 * @brief A Bean getter. It return an object of DAO with the parameter of the DAO name.
	 * @param beanName(DAO class name)
	 * @return The DAO object you want.
	 */
	public static Object getBean (String beanName){
		
		return beanFactory.getBean(beanName);
		
	}
	
	/**
	 * @brief A session factory getter
	 * @return session factory(SessionFactory)
	 */
	public static SessionFactory getSessionFactory (){
		
		return sessionFactory;
		
	}
	
	/**
	 * @brief A hibernate session getter
	 * @return session (Hibernate session)
	 */
	public static Session getSession (){
		
		return sessionFactory.openSession();
		
	}
	
	/**
	 * @brief A session closer. It can close the session you offered.
	 * @param session
	 * @exception Hibernate Exception
	 */
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
