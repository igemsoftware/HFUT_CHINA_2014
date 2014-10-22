package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @brief It is a JSON model class. We create the object with our parameters and return to client or users
 * @version 1.0
 *
 */
public class JSONCheck {

	/**< It is the index of a wrong part in the sequence that user offered */
	private int index;

	/**
	 * @brief The constructor of the class, which will set the index with the value that user offered
	 * @param index
	 */
	public JSONCheck (int index){
		
		this.index = index;
		
	}
	
	/**
	 * @brief The getter of index
	 * @return index(int)
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @brief The setter of index
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
}
