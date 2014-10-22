package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief The relation of a head node in a part sequence
 *
 */
public class HeadPart {

	/**< It is the id of a record in the database */
	private int id;
	
	/**< It is the head part */
	private Part first;
	
	/** It describe the relation of a head node */
	private double con;
	
	/**
	 * @brief The getter of id
	 * @return id(int)
	 */
	public int getId() {
		return id;
	}

	/**
	 * @brief The setter of id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @brief The getter of the head node
	 * @return part(Part)
	 */
	public Part getFirst() {
		return first;
	}

	/**
	 * @brief The setter of the head node
	 * @param first
	 */
	public void setFirst(Part first) {
		this.first = first;
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
	 */
	public void setCon(double con) {
		this.con = con;
	}
	
}
