package com.sanmixy.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.sanmixy.dao.PartDao;
import com.sanmixy.dao.TwinsPartDao;
import com.sanmixy.daoImpl.PartDaoImpl;
import com.sanmixy.model.Part;
import com.sanmixy.utils.HibernateUtils;

public class MoreLikeThis {

//	 public void print_canditate() {
//
//		  for (int i = 0; i < frequencySet[0].size(); i++) {
//		   Iterator ix = candidateSet[i].iterator();
//		   Iterator iy = frequencySet[i].iterator();
//		   System.out.print("候选集" + (i + 1) + ":");
//		   while (ix.hasNext()) {
//		    System.out.print((String) ix.next() + "t");
//		    //System.out.print(toDigit((String) ix.next()) + "t");
//		   }
//		   System.out.print("n" + "频繁集" + (i + 1) + ":");
//		   while (iy.hasNext()) {
//		    System.out.print((String) iy.next() + "t");
//		    //System.out.print(toDigit((String) iy.next()) + "t");
//		   }
//		   System.out.println();
//		  }
//		 }
//
//		 
//		 private String toDigit(String str) {
//		  if (str != null) {
//		   StringBuffer temp = new StringBuffer();
//
//		   for (int i = 0; i < str.length(); i++) {
//		    char c = str.charAt(i);
//		    temp.append(((int) c - 65) + " ");
//		   }
//
//		   return temp.toString();
//		  } else {
//		   return null;
//		  }
//
//		 }
//
//		 public String[] getTrans_set() {
//		  return transSet;
//		 }
//
//		 public void setTrans_set(String[] transSet) {
//		  transSet = transSet;
//		 }
//
//		 public double getMinsup() {
//		  return minsup;
//		 }
//
//		 public void setMinsup(double minsup) {
//		  this.minsup = minsup;
//		 }
//
//		 public double getMinconf() {
//		  return minconf;
//		 }
//
//		 public void setMinconf(double minconf) {
//		  this.minconf = minconf;
//		 }
//
//		 public void run() {
//		  int k = 1;
//
//		  item1_gen();
//
//		  do {
//		   k++;
//		   canditate_gen(k);
//		   frequent_gen(k);
//		  } while (!is_frequent_empty(k));
//		  frequencyIndex = k - 1;
//		  print_canditate();
//		  maxfrequent_gen();
//		  print_maxfrequent();
//		  ruleGen();
//		  rulePrint();
//
//		 }
	
	
	
//	public Iterator find (){
//		
//		PartDao partDao = new PartDaoImpl();
//		
//		Iterator iter = partDao().iterator();
//		
//		return iter;
//		
//	}
	
//	public void calc (){
//		
//		Iterator it = find();
//		
//		if (it.hasNext()){
//			
//			Part part = (Part) it.next();
//			
//			int value = count(sequence, part.getSequence());
//			
//			hashmap.put(part.getPartName(), value);
//			
//		}
//			
//	}
//	
//	public void sort (){
//		
//		list = new ArrayList<Entry<String,Integer>>(hashmap.entrySet());   
//		  
//		Collections.sort(list, new Comparator<Object>(){   
//		        public int compare(Object e1, Object e2){   
//		        int v1 = (int) ((Entry<String, Integer>)e1).getValue();   
//		        int v2 = (int) ((Entry<String, Integer>)e2).getValue();   
//		        return v2-v1; 
//		    }   
//		});
//	}
	
//	public int count (String source, String value){
//		
//		int ans = 0;
//		
//		int length = (source.length()<value.length())?source.length():value.length();
//		
//		for (int i = 1; i <= length; i ++){
//			
//			if (source.charAt(i-1)==value.charAt(i-1)){
//				
//				ans ++;
//			}
//		}
//		return ans;
//		
//	}
	
	
	/**
	 * 
	 * easily get hamin dis between target sequence and other part sequence
	 */
//	public ArrayList <Entry <String, Integer>> func (){
//		
//		calc ();
//		
//		sort();
//		
//		return list;
//	}
	
	public List func (String partName){
		
		TwinsPartDao twinsPartDao = (TwinsPartDao) HibernateUtils.getBean("twinsPartDao");
		
		List list = twinsPartDao.findTwinsByBasePartName(partName);
		
		return list;
		
	}

	
	
	
}
