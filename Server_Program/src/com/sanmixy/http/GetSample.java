package com.sanmixy.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.sanmixy.utils.CommonUtils;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Get data from official database
 *
 */
public class GetSample {
	
	/**
	 * @brief Download data from official database
	 * @param temp
	 * 
	 * A static method. Download the given part data and then
	 * create xml files to store them.
	 */
	public static void getData(String temp) {

		BufferedWriter bw = null;
		
		/**< A http client */
		HttpClient httpClient = new HttpClient();

		/**< A get method for http client */
		GetMethod getMethod = new GetMethod(
				"http://parts.igem.org/xml/part." + temp);
		
		getMethod.getParams().setParameter(
				HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {

			/**< Execute the get method */
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			
			/** Get detail data */
			byte[] responseBody = getMethod.getResponseBody();
			bw = CommonUtils.getBufferedWriter("./allData/" + temp + ".xml");
			
			System.out.println(new String(responseBody));
			bw.write(new String(responseBody));
			bw.flush();
			bw.close();

		} catch (HttpException e) {
			
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			/**< Release the connection */
			getMethod.releaseConnection();
		}
	}
}
