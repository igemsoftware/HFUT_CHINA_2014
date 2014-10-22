package com.sanmixy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief A Util class with some simple functions
 *
 */
public class CommonUtils {
	
	/**< A bufferedReader used to get data from source files*/
	private static BufferedReader br;
	
	/** A bufferedWriter used to write down the data that we learned */
	private static BufferedWriter bw;
	
	/**
	 * @brief Create a bufferedReader with a target path
	 * @param targetPath
	 * @return BufferedReader
	 * @exception FileNotFoundException
	 */
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

	/**
	 * @brief Create a bufferedWriter with a target path
	 * @param targetPath
	 * @return bufferedWriter
	 * @exception IOException
	 */
	public static BufferedWriter getBufferedWriter (String targetPath){
		
		try {
			bw = new BufferedWriter (new FileWriter (targetPath));
			
			return bw;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	/**
	 * @brief Export all the modle classes to our database.
	 * 
	 */
	public static void export (){
		
		Configuration configuration = new Configuration().configure ();
		
		SchemaExport schemaExport = new SchemaExport(configuration);
		
		schemaExport.create(true, true);
		
	}
	
	public static String MD5Maker (String target){
		
        byte [] buf = target.getBytes();
        MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        md5.update(buf);
        byte [] tmp = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b:tmp) {
            sb.append(Integer.toHexString(b&0xff));
        }
        String answer = new String ().valueOf(sb);
        
        return answer;
    }
}
