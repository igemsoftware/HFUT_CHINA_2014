package com.sanmixy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class CommonUtils {
	
	private static BufferedReader br;
	
	private static BufferedWriter bw;
	
	public static BufferedReader getBufferedReader (String targetPath){
		
		try {
			
			br = new BufferedReader (
				 new FileReader(
				 targetPath));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return br;
		
	}

	public static BufferedWriter getBufferedWriter (String targetPath){
		
		try {
			bw = new BufferedWriter (new FileWriter (targetPath));
			
			return bw;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
		
	}
	public static void export (){
		
		Configuration configuration = new Configuration().configure ();
		
		SchemaExport schemaExport = new SchemaExport(configuration);
		
		schemaExport.create(true, true);
		
	}
}
