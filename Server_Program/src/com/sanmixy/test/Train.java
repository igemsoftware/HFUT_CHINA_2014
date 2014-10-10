package com.sanmixy.test;

import com.sanmixy.core.AprioriNew;
import com.sanmixy.io.DataStorage;
import com.sanmixy.utils.Handle;

public class Train {

	public void train (){
		/**
		 * training from source data;
		 */
//		new AprioriNew ().func();
//		
		DataStorage d = new DataStorage();
//		d.partStore();
//		d.twinsStore();
//		d.rulesStore();
		d.deviceStore();
	}
}
