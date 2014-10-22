<%@page import="net.sf.json.util.CycleDetectionStrategy"%>
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
<%@ page import="com.sanmixy.utils.CommonUtils" %>
<%@ page import="org.hibernate.*" %>
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
	
	List listAll = new ArrayList ();
	
	if (null == requestType){
	
		response.getWriter().write("null request");
		
	}
	
	if ("regRequest".equals(requestType)){
	
		String userInfo = request.getParameter("userInfo");
		
		String ans = CommonUtils.MD5Maker(userInfo);
		
		listAll.add(new JSONUserInfo(ans));
		
	}
	
	
	if ("checkRequest".equals(requestType)){
	
		int partNum=Integer.parseInt(request.getParameter("partNum"));
		
		String[] userCir = new String [partNum];
		
		boolean flag = false;
		
		JSONCheck[] checkAnswers = new JSONCheck [partNum];
		
		for (int ind = 1; ind <= partNum; ind ++){
		
			userCir[ind-1] = request.getParameter("partName"+ind);
			
		}
		
		HeadPartDao headPartDao = (HeadPartDao)HibernateUtils.getBean("headPartDao");
		DoublePartCirDao doublePartCirDao = (DoublePartCirDao)HibernateUtils.getBean("doublePartCirDao");
		List resultList = new ArrayList ();
		
		if (!headPartDao.isTrue(userCir[0], 0.005)){
			
			checkAnswers[0] = new JSONCheck (1);
			listAll.add(checkAnswers[0]);
			
			flag = true;
			
		}
		
		for (int ind = 1; ind <= partNum-1; ind ++){
		
			String[] subStr = new String [] {userCir[ind-1], userCir[ind]};
			
			if (!doublePartCirDao.isTrue(subStr, 0.001)){
				
				checkAnswers[ind] = new JSONCheck (ind+1);
				listAll.add(checkAnswers[ind]);
				
				
				flag = true;
			}
		}
		
		if (!flag){
			
			listAll.add(new JSONCheck(-1));
			
		}
	}
	
	
	if ("record".equals(requestType)){
	
		PartDao partDao = (PartDao) HibernateUtils.getBean("partDao");
		RecentlyUsedPartDao recentlyUsedPartDao = (RecentlyUsedPartDao) HibernateUtils.getBean("recentlyUsedPartDao");
		
		String userInfo = request.getParameter("userInfo");
		
		String partName = request.getParameter("partName");
		
		Date datetime = new Date();
		
		int id = recentlyUsedPartDao.findIDByPartNameAndUserInfo(partName, userInfo);
		
		int count = 1;
		
		if (0 != id){
			
			count = recentlyUsedPartDao.findCountByPartNameAndUserInfo(partName, userInfo) + 1;
			
			recentlyUsedPartDao.deleteRecordByID(id);
			
		}
		
		Part part = partDao.findPartByWholePartName(partName);
		
		if (null != part){
		
			RecentlyUsedPart rup = new RecentlyUsedPart();
			
			rup.setPart(part);
			
			rup.setDateTime(new Date());
			
			rup.setUserInfo(userInfo);
			
			rup.setCount(count);
			
			recentlyUsedPartDao.addRecord(rup);
			
		}
		
		
	}else{
	
		List[] partRelationLists = new List [4];
	
		
		PartDao partDao = (PartDao) HibernateUtils.getBean("partDao");
		
		if ("partRequest".equals(requestType)){
			
			String partName = request.getParameter("partName");
			
			Part part = partDao.findPartByWholePartName(partName);
			
			if (null != part)
				
				listAll.add(part);
			
			
		}
		if ("recentlyUsed".equals(requestType)){
		
			String userInfo = request.getParameter("userInfo");
			
			RecentlyUsedPartDao recentlyUsedPartDao = (RecentlyUsedPartDao) HibernateUtils.getBean("recentlyUsedPartDao");
			List recentlyList = null;
			
		
			recentlyList = recentlyUsedPartDao.getRecentlyUsedPartByUserInfo(userInfo);
			
			if (null != recentlyList){
	
			
				if (0 != recentlyList.size()){
				
					for (Iterator recentlyUsedPartIter = recentlyList.iterator();
						 recentlyUsedPartIter.hasNext();
						 ){
						 
						 Object [] listObject = (Object[]) recentlyUsedPartIter.next();
						 
						 Part part = (Part)listObject[0];
						 
						 RecentlyUsedPart recentlyUsedPart = (RecentlyUsedPart)listObject[1];
						 
						 JSONRecentlyUsedPart jsonRecentlyUsedPart = new JSONRecentlyUsedPart ();
						 
						 jsonRecentlyUsedPart.setPartID(part.getPartID());
						 jsonRecentlyUsedPart.setPartName(part.getPartName());
						 jsonRecentlyUsedPart.setType(part.getType());
						 jsonRecentlyUsedPart.setPartUrl(part.getPartUrl());
						 jsonRecentlyUsedPart.setCount(recentlyUsedPart.getCount());
						 jsonRecentlyUsedPart.setDatetime(recentlyUsedPart.getDateTime().toString());
						 jsonRecentlyUsedPart.setUserInfo(recentlyUsedPart.getUserInfo());
					
						 
						 listAll.add(jsonRecentlyUsedPart);
					}
				}
				
			}
			
			
		}
		if ("deviceRequest".equals(requestType)){
			
			String userCir = request.getParameter("userCir");
			
			DeviceDao deviceDao = (DeviceDao)HibernateUtils.getBean("deviceDao");
						
			List deviceList = deviceDao.findDeviceByPartSeq(userCir);
			
		
			
			if (0 != deviceList.size()){
				
				JSONDevice[] devices = new JSONDevice[deviceList.size()];
				
				int ind = 0;
				for (Iterator deviceListIter = deviceList.iterator();
					 deviceListIter.hasNext();
					 ){
					 
					ind ++;
				
					Part part = (Part) (((Object[])deviceListIter.next())[0]);
				
					Device device = (Device)(((Object[])deviceListIter.next())[1]);
					
					devices[ind-1] = new JSONDevice();
					devices[ind-1].setPartID(part.getPartID());
					devices[ind-1].setPartName(part.getPartName());
					devices[ind-1].setType(part.getType());
					devices[ind-1].setPartUrl(part.getPartUrl());
					devices[ind-1].setPart_seq(device.getPart_seq());
					
					listAll.add(devices[ind-1]);
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
			
		
			double[] con = new double[]{0.005, 0.001, 0.0001, 0.0005, 0.000005};
		
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
				
				List headPartList = new ArrayList();
				
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
					
					if (15 > listAll.size()){
					
						int targetNum = 15 - listAll.size();
						
						List restPartList = partDao.findPartByType(userInput, targetNum);
						
						listAll.addAll(restPartList);
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
				
				if (2 <= partNum){
				
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
					
					if (3 <= partNum){
						
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
						
						if (4 <= partNum){
						
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
				
				if (15 > listAll.size()){
					
					int targetNum = 15 - listAll.size();
						
					List restPartList = partDao.findPartByType(userInput, targetNum);
						
					listAll.addAll(restPartList);
				}
			}
		}
	}
	
	List<Object> finalList = new ArrayList<Object> ();
	
	Set<Object> set = new HashSet<Object> ();
	for (Iterator<Object> listAllIter = listAll.iterator();
		 listAllIter.hasNext();
		 ){
		
		Object listAllObj = listAllIter.next();
		
		String value = null;
		
			
		if ("recommendation".equals(requestType) ||
		    "ambiguous".equals(requestType) ||
		    "partRequest".equals(requestType) ||
		    "twinsRequest".equals(requestType))
				
			value = ((Part)(listAllObj)).getPartName();
				
		if ("deviceRequest".equals(requestType))
				
			value = ((JSONDevice) listAllObj).getPartName();
			
		if ("checkRequest".equals(requestType))
			
			value = new String ().valueOf(((JSONCheck)listAllObj).getIndex());
			
		if ("recentlyUsed".equals(requestType))
		
			value = ((JSONRecentlyUsedPart) listAllObj).getPartName();
				
		if ("regRequest".equals(requestType))
			
			value = ((JSONUserInfo) listAllObj).getUserInfo();
			
			
		if (set.add(value))
			
			finalList.add(listAllObj);
			
			
	}
	
	
	
	JSONFinalList fl = new JSONFinalList ();
	fl.setRoot(finalList);
	
	JsonConfig jsonConfig = new JsonConfig ();
	jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	jsonObject = JSONObject.fromObject(fl, jsonConfig);
	
	response.getWriter().write(jsonObject.toString());
		
%>

</body>
</html>
