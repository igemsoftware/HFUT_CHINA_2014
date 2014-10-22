package com.sanmixy.model;


/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Describe the relation between two parts, and it is a model class
 *
 */
public class DoublePartCir {

	/**<  It is the id of an record in the database that the relation between two parts */
	private int id;
	
	/**< It describe the link of the two parts */
	private double con;
	
	/**< It is the first part of two */
	private Part first;
	
	/**< It is the second part of two */
	private Part second;
	
	/**
	 * @brief The getter of id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @brief The setter of id
	 * @param Id
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @brief The getter of con 
	 * @return con(double)
	 */
	public double getCon() {
		return con;
	}

	/**
	 * @brief The setter of con
	 * @param con
	 * 
	 */
	public void setCon(double con) {
		this.con = con;
	}

	/**
	 * @brief The getter of part
	 * @return part(Part)
	 */
	public Part getFirst() {
		return first;
	}

	/**
	 * @brief The setter of first part
	 * @param first
	 */
	public void setFirst(Part first) {
		this.first = first;
	}

	/**
	 * @brief The getter of second part
	 * @return part
	 */
	public Part getSecond() {
		return second;
	}

	/**
	 * @brief The setter of second part
	 * @param second
	 */
	public void setSecond(Part second) {
		this.second = second;
	}
	
}
