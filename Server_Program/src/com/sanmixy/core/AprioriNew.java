package com.sanmixy.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.sanmixy.utils.CommonUtils;

public class AprioriNew {

	private static BufferedReader br;
	private static BufferedWriter[] bws;
	private static String [] targetFilesNames;
	private static File [] targetFiles;
	private static String sourceFile;
	
	private String [][] cirs;//gene cirs
	private static HashMap [] maps;

	private static final double []support;
	
	static {
		support = new double [] {0.001, 0.0005, 0.0001, 0.00001, 0.00001};
		
		sourceFile = new String ("./source/allDevice(stage2).data");
		br = CommonUtils.getBufferedReader(sourceFile);
		
		targetFilesNames = new String []{"./result/HeadPart.data",
										"./result/DoublePart.data",
										"./result/TriplePart.data",
										"./result/QuadraPart.data",
										"./result/PentaPart.data"};
		
		bws = new BufferedWriter [5];
		for (int ind = 1; ind <= bws.length; ind ++){
			try {
				bws[ind-1] = new BufferedWriter(
							 new FileWriter(
							 targetFilesNames[ind-1]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		maps = new HashMap [5];
		
		for (int ind = 1; ind <= maps.length; ind ++){
			
			maps[ind-1] = new HashMap <String, Double> ();
			
		}
		
	}
	
	
	
	public AprioriNew (String[][] cirs){
		
		this.cirs = new String[2568][];
		
		this.cirs = cirs;
	}
	
	public AprioriNew (){
		
		this.cirs = new String [2568][];
	}

	/**
	 * @fucntion scan data from files
	 * it works
	 */
	public void scan (){
		
		String temp = null;
		int index = 0;
		
		try {
			while (null != (temp = br.readLine())){
				index ++;
				
				String [] lineData = temp.split(" ");
				
				cirs [index-1] = lineData;
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	
	/**
	 * 
	 * @param map
	 * @param key
	 */
	public void addItem (HashMap <String, Double> map, String key){
		
		double value = 1;
		
		if (map.containsKey(key))
				
			value = map.get(key) + 1;
			
		map.put(key, value);
			
	}
	
	public void getSingleItemMap (){
		
		for (int index = 1; index <= cirs.length; index ++){
			
			if (cirs[index-1].length < 1)
				
				continue;
			
			String key = cirs[index-1][0];
			
			addItem (maps[0], key);
			
		}
	}
	
	public void getDoubleItemMap (){
		
		for (int index = 1; index <= cirs.length; index ++){
			
			if (cirs[index-1].length < 2)
				
				continue;
			
			for (int p = 1; p <= cirs[index-1].length-1; p ++){
				
				String key = cirs[index-1][p-1] + " " + 
						     cirs[index-1][p];
				
				addItem(maps[1], key);
				
			}
		}
	}
	
	public void getTripleItemMap (){
		
		for (int index = 1; index <= cirs.length; index ++){
			
			if (cirs[index-1].length < 3)
				
				continue;
			
			for (int p = 1; p <= cirs[index-1].length-2; p ++){
						
				String key = cirs[index-1][p-1] + " " + 
							 cirs[index-1][p] + " " + 
							 cirs[index-1][p+1];
						
				addItem(maps[2], key);
			}
			
		}
			
	}
	
	public void getQuadraItemMap (){
		
		for (int index = 1; index <= cirs.length; index ++){
			
			if (cirs[index-1].length < 4)
				
				continue;
			
			for (int p = 1; p <= cirs[index-1].length-3; p ++){
				
				String key = cirs[index-1][p-1] + " " + 
							 cirs[index-1][p] + " " + 
							 cirs[index-1][p+1] + " " +
							 cirs[index-1][p+2];
							
				addItem(maps[3], key);
				
			}
		}
	}
	
	public void getPentaItemMap (){
		
		for (int index = 1; index <= cirs.length; index ++){
			
			if (cirs[index-1].length < 5)
				
				continue;
			
			for (int p = 1; p <= cirs[index-1].length-4; p ++){
				
				String key = cirs[index-1][p-1] + " " + 
							 cirs[index-1][p] + " " + 
							 cirs[index-1][p+1] + " " +
							 cirs[index-1][p+2] + " " +
							 cirs[index-1][p+3];
								
				addItem(maps[4], key);
				
			}
		}
	}
	
	public void setProb (int sum,
						 Set <Entry <String, Double>> entries,
						 HashMap <String, Double> map,
						 int index){
		
		double prob = 0;
		
		for (Entry <String, Double> entry : entries){
			
			prob = entry.getValue() / (double)sum;
			
			String key = entry.getKey();
			
			if (prob >= support[index-1])
				
				map.put(key, prob);
			
			else
				
				map.put(key, (double) 0);
			
			
		}
		
	}
	
	public void getProbOfItems (){
		
		for (int index = 1; index <= maps.length; index ++){
			
			Set <Entry <String, Double>> entries =
					maps[index-1].entrySet();
			
			int sum = getSum (index);
			
			setProb(sum, entries, maps[index-1], index);
			
		}
		
		
	}
	
	public int getSum (int index){
		
		int sum = 0;
		
		switch (index){
		
			case 1:sum = cirs.length; break;
			
			case 2:{
				
				for (int i = 1; i <= cirs.length; i ++){
					if (cirs[i-1].length>=2){
						sum += cirs[i-1].length-1;
					}
				}
				break;
			}
			case 3:{
				
				for (int i = 1; i <= cirs.length; i ++){
					if (cirs[i-1].length>=3){
						sum += cirs[i-1].length-2;
					}
				}
				break;
			}
			case 4:{
				
				for (int i = 1; i <= cirs.length; i ++){
					if (cirs[i-1].length>=4){
						sum += cirs[i-1].length-3;
					}
				}
				break;
			}
			case 5:{
				
				for (int i = 1; i <= cirs.length; i ++){
					if (cirs[i-1].length>=5){
						sum += cirs[i-1].length-4;
					}
				}
				break;
			}
			
		}
		return sum;
	}
	
	
	public void show (HashMap <String, Double> map){
		
		Set <Entry <String, Double>> entries = map.entrySet();
		
		for (Entry <String, Double> e : entries){
			
			System.out.println(e.getKey() + " " + e.getValue());
			
		}
	}
	
	public void export (){
		
		for (int i = 1; i <= maps.length; i ++){
			
			Set <Entry <String, Double>> entries = 
					maps[i-1].entrySet();
			
			for (Entry <String, Double> entry: entries){
				
				try {
					bws[i-1].write(entry.getValue() + " " + entry.getKey());
					bws[i-1].newLine();
					bws[i-1].flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void func (){
		
		scan ();
		getSingleItemMap();
		getDoubleItemMap();
		getTripleItemMap();
		getQuadraItemMap();
		getPentaItemMap();
		getProbOfItems();
		export();
	}
	
}
