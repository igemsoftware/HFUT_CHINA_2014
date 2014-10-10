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
	
	JSONObject jsonObject = new JSONObject ();
	
	List listAll = null;
	
	if (null == requestType){
	
		response.getWriter().write("null request");
		
	}
	if ("record".equals(requestType)){
		
		String ipAddress = request.getRemoteAddr();
		
		String partName = request.getParameter("partName");
		
		Date datetime = new Date();
		
		
	}else{
	
		List[] partRelationLists = new List [4];
	
		listAll = new ArrayList ();
		
		PartDao partDao = (PartDao) HibernateUtils.getBean("partDao");
		
		if ("partRequest".equals(requestType)){
			
			String partName = request.getParameter("partName");
			
			Part part = partDao.findPartByWholePartName(partName);
			
			if (null != part)
				
				listAll.add(part);
			
			
		}
		if ("recentlyUsed".equals(requestType)){
		
			String ipAddress = request.getRemoteAddr();
			
			RecentlyUsedPartDao recentlyUsedPartDao = (RecentlyUsedPartDao) HibernateUtils.getBean("recentlyUsedPartDao");
			
			List recentlyList = recentlyUsedPartDao.getRecentlyUsedPartByIp(ipAddress);
			
			JSONRecentlyUsedPart jsonRecentlyUsedPart = new JSONRecentlyUsedPart ();
			
			if (0 != recentlyList.size()){
			
				for (Iterator recentlyUsedPartIter = recentlyList.iterator();
					 recentlyUsedPartIter.hasNext();
					 ){
					 
					 Object [] listObject = (Object[]) recentlyUsedPartIter.next();
					 
					 Part part = (Part)listObject[0];
					 
					 RecentlyUsedPart recentlyUsedPart = (RecentlyUsedPart)listObject[1];
					 
					 jsonRecentlyUsedPart.setPartID(part.getPartID());
					 jsonRecentlyUsedPart.setPartName(part.getPartName());
					 jsonRecentlyUsedPart.setType(part.getType());
					 jsonRecentlyUsedPart.setPartUrl(part.getPartUrl());
					 jsonRecentlyUsedPart.setCount(recentlyUsedPart.getCount());
					 jsonRecentlyUsedPart.setDatetime(recentlyUsedPart.getDateTime());
					 jsonRecentlyUsedPart.setIpAddress(recentlyUsedPart.getIpAddress());
					 
					 listAll.add(jsonRecentlyUsedPart);
				}
			}
			
			
		}
		if ("deviceRequest".equals(requestType)){
			
			String userCir = request.getParameter("userCir");
			
			DeviceDao deviceDao = (DeviceDao)HibernateUtils.getBean("deviceDao");
			
			JSONDevice jsonDevice = new JSONDevice ();
			
			List deviceList = deviceDao.findDeviceByPartSeq(userCir);
			
			if (0 != deviceList.size()){
			
				for (Iterator deviceListIter = deviceList.iterator();
					 deviceListIter.hasNext();
					 ){
				
					Part part = (Part) (((Object[])deviceListIter.next())[0]);
				
					Device device = (Device)(((Object[])deviceListIter.next())[1]);
					
					jsonDevice.setPartID(part.getPartID());
					jsonDevice.setPartName(part.getPartName());
					jsonDevice.setType(part.getType());
					jsonDevice.setPartUrl(part.getPartUrl());
					jsonDevice.setPart_seq(device.getPart_seq());
					
					listAll.add(jsonDevice);
				}
			}
			
		}
		if ("twinsRequest".equals(requestType)){
		
			TwinsPartDao twinsPartDao = (TwinsPartDao)HibernateUtils.getBean("twinsPartDao");
			
			String basePart = request.getParameter("basePart");
			
			List twinsList = twinsPartDao.findTwinsByBasePartName(basePart);
			
			if (0 != twinsList.size()){
			
				for (Iterator twinsListIter = twinsList.iterator();
					 twinsListIter.hasNext();
					 ){
				
					Part twinPart = (Part)(((Object[])twinsListIter.next())[1]);
					
					listAll.add(twinPart);
						 
				}
			}
		}
		if ("ambiguous".equals(requestType)||
			"recommendation".equals(requestType)){
		
			double[] con = new double[]{0.01, 0.001, 0.001, 0.0005, 0.00005};
		
			int partNum = Integer.parseInt(request.getParameter("partNum"));
			String userInput = request.getParameter("userInput");
			String[] userCir = new String [partNum];
			
			for (int index = 1; index <= partNum; index ++){
				
				userCir[index-1] = request.getParameter("partName"+index);
			
			}
			
			
			List partList = partDao.findPartByPartName(userInput);
			
			if (0 != partList.size())
			
				listAll.addAll(partList);
			
			
			if (0 == partNum){
			
				HeadPartDao headPartDao = (HeadPartDao) HibernateUtils.getBean("headPartDao");
				
				List headPartList = null;
				
				if (null == userInput)
				
					headPartList = headPartDao.getHeadPart(con[0]);
					
				else
					
					headPartList = headPartDao.getHeadPart(userInput, con[0]);
				
				
				if (0 != headPartList.size()){
				
					for (Iterator headPartListIter = headPartList.iterator();
						 headPartListIter.hasNext();
						 ){
					
						Part part = (Part)(((Object[])headPartListIter.next())[0]);
						
						listAll.add(part);	 
						
					}
				}
				
					
			}else{
			
				DoublePartCirDao doublePartCirDao = (DoublePartCirDao)HibernateUtils.getBean("doublePartCirDao");
				
				String subCir2 = userCir[partNum-1];
				
				if (null == userInput)
				
					partRelationLists[0] = doublePartCirDao.getNextPart(subCir2, con[1]);
				
				else
				
					partRelationLists[0] = doublePartCirDao.getNextPart(subCir2, userInput, con[1]);	
				
				if (0 != partRelationLists[0].size()){
				
					for (Iterator doublePartCirIter = partRelationLists[0].iterator();
						 doublePartCirIter.hasNext();
						 ){
					
						Part p2 = (Part)(((Object[])doublePartCirIter.next())[1]);	 
						 
						listAll.add(p2);
						
					}
				}
				
				if (3 <= partNum){
				
					TriplePartCirDao triplePartCirDao = (TriplePartCirDao) HibernateUtils.getBean("triplePartCirDao");
					
					String [] subCir3 = Arrays.copyOfRange(userCir, partNum-2, partNum);
				
					if (null == userInput)
					
						partRelationLists[1] = triplePartCirDao.getNextPart(subCir3, con[2]);
					
					else
						
						partRelationLists[1] = triplePartCirDao.getNextPart(subCir3, userInput, con[2]);
					
					if (0 != partRelationLists[1].size()){
							
						for (Iterator triplePartCirIter = partRelationLists[1].iterator();
							 triplePartCirIter.hasNext();
							 ){
							 
							Part p3 = (Part)(((Object[])triplePartCirIter.next())[2]);
							
							listAll.add(p3);
								 
						}
					}
					
					if (4 <= partNum){
						
						QuadraPartCirDao quadraPartCirDao = (QuadraPartCirDao) HibernateUtils.getBean("quadraPartCirDao");
						
						String [] subCir4 = Arrays.copyOfRange(userCir, partNum-3, partNum);
						
						if (null == userInput)
						
							partRelationLists[2] = quadraPartCirDao.getNextPart(subCir4, con[3]);
							
						else
						
							partRelationLists[2] = quadraPartCirDao.getNextPart(subCir4, userInput, con[3]);
							
						if (0 != partRelationLists[2].size()){
						
							for (Iterator quadraPartCirIter = partRelationLists[2].iterator();
								 quadraPartCirIter.hasNext();
								 ){
							
								Part p4 = (Part) (((Object[])quadraPartCirIter.next())[3]);
								
								listAll.add(p4);
								
							}
						}
						
						if (5 == partNum){
						
							PentaPartCirDao pentaPartCirDao = (PentaPartCirDao) HibernateUtils.getBean("pentaPartCirDao");
							
							String [] subCir5 = Arrays.copyOfRange(userCir, partNum-4, partNum);
							
							if (null == userInput)
							
								partRelationLists[3] = pentaPartCirDao.getNextPart(subCir5, con[4]);
							
							else
								
								partRelationLists[3] = pentaPartCirDao.getNextPart(subCir5, userInput, con[4]);
							
							if (0 != partRelationLists[3].size()){
								
								for (Iterator pentaPartCirIter = partRelationLists[3].iterator();
								     pentaPartCirIter.hasNext();
								     ){
								 
								 	Part p5 = (Part)(((Object[])pentaPartCirIter.next())[4]);
								 	
								 	listAll.add(p5);
								 	    
								}
							}
						}
					}
				}
			}
		}
	}
	
	jsonObject.put("root", listAll);
	
	response.getWriter().write(jsonObject.toString());
		
%>

		
	
	
</body>
</html>
