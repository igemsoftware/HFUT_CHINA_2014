package com.sanmixy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.sanmixy.http.GetSample;

public class Handle {
	
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
					System.out.println(str);
					GetSample.getData(str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
