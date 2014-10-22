package com.sanmixy.test;

import com.sanmixy.core.AprioriNew;
import com.sanmixy.io.DataStorage;
import com.sanmixy.utils.Handle;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief create a model from source data 
 *
 */
public class Train {

	/**
	 * @brief create a model from source data and store the data to our database
	 */
	public void train (){
		
		new AprioriNew ().func();
		
		DataStorage d = new DataStorage();
		d.partStore();
		d.twinsStore();
		d.rulesStore();
		d.deviceStore();
	}
}
