package com.sanmixy.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import junit.framework.TestCase;

import com.sanmixy.core.MoreLikeThis;
import com.sanmixy.dao.DeviceDao;
import com.sanmixy.dao.DoublePartCirDao;
import com.sanmixy.dao.PartDao;
import com.sanmixy.dao.TwinsPartDao;
import com.sanmixy.http.GetSample;
import com.sanmixy.http.xmlHandle.IXMLReader;
import com.sanmixy.io.DataStorage;
import com.sanmixy.model.Device;
import com.sanmixy.model.Part;
import com.sanmixy.utils.CommonUtils;
import com.sanmixy.utils.Handle;
import com.sanmixy.utils.HibernateUtils;

public class MyTest extends TestCase{

	/**
	 * @author Yu Xia
	 * @function export the models to mysql database;
	 */
	public void testExport (){
		
		CommonUtils.export();
		
	}
	
	public void testDate (){
		
		Timestamp t =  new Timestamp(14, 10, 10, 19, 22, 30, 30);
		
		
		System.out.println(t);
		
	}
	
	public void testDevice (){
		
		String userCir = "BBa_B0034 BBa_K592009";
		
		DeviceDao d = (DeviceDao) HibernateUtils.getBean("deviceDao");
		
		List lst = d.findDeviceByPartSeq(userCir);
		
		for (Iterator it = lst.iterator(); it.hasNext();){
			
			Part part = (Part) (((Object[])it.next())[0]);
			
			Device device = (Device)(((Object[])it.next())[1]);
			
			System.out.println(part.getPartName() + ":" + device.getPart_seq());
		}
		
		System.out.println(lst.size());
	}
	
	public void testGetSample (){
		
		new GetSample().getData("BBa_B0034");
		
	}
	
	public void testXMLReader (){
		
		new IXMLReader().func("BBa_B0034");
		
		
	}
	
	public void testHandStage1 (){
		
		BufferedReader br = CommonUtils.getBufferedReader("./source/allDevice(stage1).data");
		BufferedWriter bw = CommonUtils.getBufferedWriter("./source/allDevice(stage2).data");
		
		String temp = null;
		
		try {
			while (null != (temp = br.readLine())){
				
				String [] line = temp.split(":");
			
				bw.write(line[1]);
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void testHandle (){
		new Train ().train();
	}
	
	public void testRuleStore (){
		new DataStorage().rulesStore();
	}
	
	public void testTwinsDao (){
		
		String partName = "BBa_M31379";
		
		TwinsPartDao t = (TwinsPartDao) HibernateUtils.getBean("twinsPartDao");
		
		List list = t.findTwinsByBasePartName(partName);
		
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println(list.size());
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("********************");
		
		for (Iterator it = list.iterator();
			 it.hasNext();
			){
			
			Part part = (Part) (((Object[])it.next())[1]);
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			System.out.println(part.getPartName());
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("********************");
			
		}
	}
	
	public void testStore (){
		
		new DataStorage().partStore();
		
//		new DataStorage ().twinsStore();
		
		
	}
	
	public void testMoreLikeThis (){
		
		List list = new MoreLikeThis().func("BBa_K088000");
		
		for (Iterator it = list.iterator(); 
		     it.hasNext();
			){
			
			Part part = (Part)(((Object [])it.next())[1]);
			
			System.out.println(part.getId() + "  " + part.getPartName());
		}
	}
	
	public void testA (){
		
		Vector <String> v = new Vector<String>();
		
		String [] d = new String [3];
		
		for (int i = 1; i <= 3; i ++)
			
			d[i-1] = new String ();
		
		d[0] = "a";
		d[1] = "b";
		d[2] = "a";
		
		for (int i = 1; i <= 3; i ++)
			if (!v.contains(d[i-1]))
				v.addElement(d[i-1]);
		
		for (Iterator<String> iter = v.iterator(); iter.hasNext(); ){
			String s = (String)iter.next();
			System.out.println(s);
		}
	}
	
//	public void testScan (){
//		
//		Apriori a = new Apriori();
//		a.func();
//		a.scanSgl();
//		a.countSgl();
//		a.scanDbl();
//		a.countDbl();BBa_K299006
//		a.scanTri();
//		a.countTri();
//		a.scanQuo();
//		a.countQuo();
//		a.scanPen();
//		a.countPen();
//		
//	}
	
	public void testTreemap (){
		
		TreeMap<Integer, Integer> t = new TreeMap ();
		
		t.put(1, 4);
		if (t.containsKey(1))
			t.put(1, 2);
		t.put(2,2);
		
		System.out.println(t.get(1));
		System.out.println(t.get(2));
	}

	
	public void testDeviceStore (){
		new DataStorage().deviceStore();
	}
	
	public void testArray (){
		String [] userCir = new String []{"abc", "def", "ghi", "jkl"};
		
		String [] sub = Arrays.copyOfRange(userCir, 4-2, 4);
		
		for (String detail : sub){
			System.out.println(detail + " ");
			
		}
	}
	public void testFindPartName (){
		
		String str = "BBa_B001";
		
		PartDao p = (PartDao) HibernateUtils.getBean("partDao");
		List list = p.findPartByPartName(str);
		for (Iterator it = list.iterator();
			 it.hasNext();){
			
			Part part = (Part) it.next();
			
			System.out.println(part.getPartName());
		}
		
	}
	
	public void testRecommondation (){
		
		String [] str = new String [] {"BBa_B0010" };
	
		DoublePartCirDao d = (DoublePartCirDao)HibernateUtils.getBean("doublePartCirDao");
		
		List list = d.getNextPart(str[0], 0.001);
		
		for (Iterator it = list.iterator(); it.hasNext(); ){
			
			Object [] o = (Object[]) it.next();
			Part p = (Part) o[1];
			
			System.out.println(p.getPartName());
			
		}
		
	}
	
	public void testT (){
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br =new BufferedReader (new FileReader ("./source/bPart.data"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		String temp = null;
		try {
			while (null != (temp = br.readLine())){
				String [] line = temp.split(" ");
				for (String str : line){
					System.out.println(str);
					GetSample.getData(str);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testAll (){
		
		Handle.getAllSamplesFromData(); //find all the part info from registry
		
	}
	
	public void testPop (){
		
		Vector <String> v = new Vector <String> ();
		
		BufferedReader br = CommonUtils.getBufferedReader("./source/allPart.data");
		BufferedWriter bw = CommonUtils.getBufferedWriter("./source/allPartNew.data");
		
		String temp = null;
		
		try {
			while (null != (temp=br.readLine())){
				
				String [] line = temp.split(" ");
				
				for (int i=1; i<=line.length; i ++){
					
					if (!v.contains(line[i-1])){
						
						v.add(line[i-1]);
						
						System.out.println(line[i-1]);
						
					}
					
					
				}
				
			}
			
			for (Iterator it = v.iterator();it.hasNext();){
				
				String str = (String)it.next();
				
				try {
					bw.write(str);
					bw.newLine();
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
