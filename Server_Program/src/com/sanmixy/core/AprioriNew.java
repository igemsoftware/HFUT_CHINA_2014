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

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Find the relation among parts.
 */
public class AprioriNew {

	/**< The br is used to create a BufferedReader, so that the program can get data from files*/
	private static BufferedReader br;
	
	/**< The bws is a BufferedWriter Array to write */
	private static BufferedWriter[] bws;
	
	/**< The File name array */
	private static String [] targetFilesNames;
	
	/**< The File array used to save the relation of parts */
	private static File [] targetFiles;
	
	/**< The source file */
	private static String sourceFile;
	
	private String [][] cirs;
	
	/**< The hashmap array used to store the relations of parts */
	private static HashMap [] maps;

	/**< Min support value */
	private static final double []support;
	
	/**
	 * @brief Initial data.
	 */
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
	
	
	/**
	 * @brief Construct function
	 * @param cirs
	 * @return A AprioriNew object.
	 */
	public AprioriNew (String[][] cirs){
		
		this.cirs = new String[2568][];
		
		this.cirs = cirs;
	}
	
	public AprioriNew (){
		
		this.cirs = new String [2568][];
	}

	/**
	 * @brief scan data from files
	 * @exception IOException
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
	 * @brief Add Item to map. Check whether the map contains the key or not.
	 */
	public void addItem (HashMap <String, Double> map, String key){
		
		double value = 1;
		
		if (map.containsKey(key))
				
			value = map.get(key) + 1;
			
		map.put(key, value);
			
	}
	
	/**
	 * @brief Get the single items from the source sequence and put them into the map
	 */
	public void getSingleItemMap (){
		
		for (int index = 1; index <= cirs.length; index ++){
			
			if (cirs[index-1].length < 1)
				
				continue;
			
			String key = cirs[index-1][0];
			
			addItem (maps[0], key);
			
		}
	}
	
	/**
	 * @brief Get the double items from the source sequence and add them into the map.
	 */
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
	
	/**
	 * @brief Get the triple parts items from the source sequence and add them into the map.
	 */
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
	
	/**
	 * @brief Get the quedra parts items from the source sequence and add them into the map.
	 */
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
	
	/**
	 * @brief Get the penta parts items from the source sequence and add them into the map.
	 */
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
	
	/**
	 * @brief Calculate the strength of the relations
	 * @param sum
	 * @param entries
	 * @param map
	 * @param index
	 */
	public void setProb (int sum,
						 Set <Entry <String, Double>> entries,
						 HashMap <String, Double> map,
						 int index){
		
		double prob = 0;
		
		for (Entry <String, Double> entry : entries){
			
			prob = entry.getValue() / (double)sum;
			
			String key = entry.getKey();
			
//			if (prob >= support[index-1])
//				
//				map.put(key, prob);
//			
//			else
//				
//				map.put(key, (double) 0);
			
			map.put(key, prob);
			
			
		}
		
	}
	
	/**
	 * @brief Get the strength of these items.
	 */
	public void getProbOfItems (){
		
		for (int index = 1; index <= maps.length; index ++){
			
			Set <Entry <String, Double>> entries =
					maps[index-1].entrySet();
			
			int sum = getSum (index);
			
			setProb(sum, entries, maps[index-1], index);
			
		}
		
		
	}
	
	/**
	 * @brief Get the sum of each type of relation
	 */
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
	
	
	/**
	 * @brief Print data.
	 * @param map
	 */
	public void show (HashMap <String, Double> map){
		
		Set <Entry <String, Double>> entries = map.entrySet();
		
		for (Entry <String, Double> e : entries){
			
			System.out.println(e.getKey() + " " + e.getValue());
			
		}
	}
	
	/**
	 * @brief Put the rules into the files
	 */
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
	
	/**
	 * @brief Pack all the operations together.
	 */
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
