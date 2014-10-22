package com.sanmixy.model;

import java.util.List;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief It is a JSON model class. It stores the final list that we will return to users or client.
 *
 */
public class JSONFinalList {

	/**< It is the final list that we will return */
	private List root;

	/**
	 * @brief The getter of root
	 * @return root(List)
	 */
	public List getRoot() {
		return root;
	}

	/**
	 * @brief The setter of root
	 * @param root
	 */
	public void setRoot(List root) {
		this.root = root;
	}
	
	
}
