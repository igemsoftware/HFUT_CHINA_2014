<%@page import="com.sanmixy.dao.HeadPartDao"%>
<%@page import="com.sanmixy.model.HeadPart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sanmixy.model.Part" %>
<%@ page import="com.sanmixy.model.DoublePartCir" %>
<%@ page import="com.sanmixy.model.TriplePartCir" %>
<%@ page import="com.sanmixy.model.QuadraPartCir" %>
<%@ page import="com.sanmixy.model.PentaPartCir" %>
<%@ page import="com.sanmixy.model.*" %>
<%@ page import="com.sanmixy.daoImpl.*" %>
<%@ page import="com.sanmixy.dao.*" %>
<%@ page import="com.sanmixy.utils.HibernateUtils" %>
<%@ page import="java.io.*" %>
<%@ page import="net.sf.json.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Solve page</title>
</head>
<body>

	<%
		
		String requestType = request.getParameter("requestType");
		
		List list = null;
		List list1 = null;
		List list2 = null;
		List list3 = null;
		List list4 = null;
		List list5 = null;
		List recentlyUsedPartList = null;
		List listAll = null;
		String type = null;
		List relevantPartList = null;
		
		JSONObject jsonObject = new JSONObject ();		
		
		if (requestType==null){
		
			response.getWriter().write("plz input parameters");
		}
		else{
			listAll = new ArrayList ();
			//
			if (requestType.equals("relevantPart")){
				
				String partName = request.getParameter("partName");
				
				TwinsPartDao twinsPartDao = (TwinsPartDao) HibernateUtils.getBean("twinsPartDao");
				
				relevantPartList = twinsPartDao.findTwinsByBasePartName(partName);
				
				for (Iterator it = relevantPartList.iterator();
					 it.hasNext();
					 ){
				
					Part part = (Part)(((Object[])it.next())[1]);
					
					listAll.add(part);
						 
				}
				
			}
			
			if (requestType.equals("partRequest")){
			
				PartDao partDao = (PartDao)HibernateUtils.getBean("partDao");
				
				String partName = request.getParameter("partName");
				
				list = partDao.findPartByPartName(partName);
				
			}
			
			
			if (requestType.equals("recommendation")){
			
				RecentlyUsedPartDao recentlyUsedPartDao = (RecentlyUsedPartDao) HibernateUtils.getBean("recentlyUsedPartDao");
				
				recentlyUsedPartList = recentlyUsedPartDao.getRecentlyUsedPart();
				
				int partNum = Integer.parseInt(request.getParameter("partNum"));
				
				type = request.getParameter("type");
				
				if (partNum == 0){
				
					HeadPartDao headPartDao = (HeadPartDao)HibernateUtils.getBean("headPartDao");
						
					if (type == null){
					
						list1 = headPartDao.getHeadPart(0.01);
						
					}else{
						
						list1 = headPartDao.getHeadPart(type, 0.01);
						
					}
					
					listAll.addAll(list1);
					
				}
				else{
					String [] userCir = new String [partNum];
					
					DoublePartCirDao doublePartCirDao = (DoublePartCirDao) HibernateUtils.getBean("doublePartCirDao");
					
					for (int index = 1; index <= partNum; index++){
						
						userCir[index-1] = request.getParameter("partName" + index);
						
					}
					
					if (null == type)
						
						list2 = doublePartCirDao.getNextPart(userCir[partNum-1], 0.001);
					
					else
						
						list2 = doublePartCirDao.getNextPart(userCir[partNum-1], type, 0.001);
						
					if (partNum >= 2){
					
						TriplePartCirDao triplePartCirDao = (TriplePartCirDao) HibernateUtils.getBean("triplePartCirDao");
							
						String [] subCir = Arrays.copyOfRange(userCir, partNum-2, partNum);
						
						if (null == type)
						
							list3 = triplePartCirDao.getNextPart(subCir, 0.001);
							
						else
							
							list3 = triplePartCirDao.getNextPart(subCir, type, 0.001);
								
						if (partNum >= 3){
							
							QuadraPartCirDao quadraPartCirDao = (QuadraPartCirDao) HibernateUtils.getBean("quadraPartCirDao");
							
							String [] subStr = Arrays.copyOfRange(userCir, partNum-3, partNum);
							
							if (null == type)
							 
								list4 = quadraPartCirDao.getNextPart(subStr, 0.0005);
								
							else
								
								list4 = quadraPartCirDao.getNextPart(subStr, type, 0.0005);
								
							if (partNum == 4){
								
								PentaPartCirDao pentaPartCirDao = (PentaPartCirDao) HibernateUtils.getBean("pentaPartCirDao");
								
								if (null == type)
								
									list5 = pentaPartCirDao.getNextPart(userCir, 0.00005);
									
								else
									
									list5 = pentaPartCirDao.getNextPart(userCir, type, 0.00005);
								
							}
						}
					}
					
				}
			}
			
			
		 %>
		 
		 <%
			if (requestType.equals("partRequest")){
			
				for (Iterator it = list.iterator();
					 it.hasNext();
					 ){
					 
					 Part part = (Part) it.next();	 
					 
					 listAll.add(part);			 
		  %>
		 
		  <%
		  		}
		  		
		  	}else{
		  	
		  		if (requestType.equals("relevantPart")){
		  		
		  			for (Iterator it = relevantPartList.iterator();
		  				 it.hasNext();
		  				 ){
		  				 
		  				 Part p = (Part)(((Object[])it.next())[1]);
		  				
		  				 listAll.add(p);		 
		  	%>
		  	<%
		  				 
		  			}
		  		}else{
		  		
			  		if (list5 != null){
			  		
			  			for (Iterator it5 = list5.iterator(); 
			  				 it5.hasNext();
			  				 ){
			  				 Part p5 = (Part)(((Object[])it5.next())[4]);
			  				 
			  				 listAll.add(p5);
			  %>
			  <%
			  				 
			  			}
			  		}
			  		
			  		if (list4 != null){
			  		
			  			for (Iterator it4 = list4.iterator();
			  			     it4.hasNext();
			  			     ){
			  			 
			  			 	Part p4 = (Part)(((Object[])it4.next())[3]);
			  			 	
			  			 	listAll.add(p4);
			  %>
			  <%    
			  			}
			  		}
			  		
			  		if (list3 != null){
			  		
			  			for (Iterator it3 = list3.iterator();
			  				 it3.hasNext();
			  				 ){
			  			Part p3 = (Part) (((Object[])it3.next())[2]);	
			  	
			  			listAll.add(p3);	
			  %>
			  <%	 
			  			}
			  		}
			  		
			  		if (list2 != null){
			  			
			  			for (Iterator it2 = list2.iterator();
			  				 it2.hasNext();
			  				 ){
			  				
			  				Part p2 = (Part) (((Object[])it2.next())[1]);	
			  			
			  				listAll.add(p2);			
			  %>
			  <%	 
			  			}
			  		}
			  		
			  		if (list1 != null){
			  		
			  			for (Iterator it1 = list1.iterator();
			  				 it1.hasNext();
			  				 ){
			  				 Part p1 = (Part)(((Object[])it1.next())[0]);
			  				 
			  				 listAll.add(p1);
			  %>
			  <%	 
			  			}
			  		}
			  	}
			  }
			  %>
			  
			  <%
			  		
			  %>
			  <%
			 
			  
			  jsonObject.put("root", listAll);
			  
			  response.getWriter().write(jsonObject.toString());
		}
			   %>
	
	
</body>
</html>
