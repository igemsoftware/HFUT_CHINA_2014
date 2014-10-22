package com.sanmixy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.sanmixy.http.GetSample;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Scan all data into buffer and then it will collect all the information from website
 *
 */
public class Handle {
	
	/**
	 * @brief Scan data, and collect the data information from website
	 * @exception FileNotFoundException IOException
	 * 
	 */
	public static void getAllSamplesFromData (){
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br =new BufferedReader (new FileReader ("./source/allPart.data"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		String temp = null;
		try {
			while (null != (temp = br.readLine())){
				String [] line = temp.split(" ");
				for (String str : line){
					GetSample.getData(str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
