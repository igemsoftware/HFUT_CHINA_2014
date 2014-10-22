package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief It used to describe the relation of four parts
 *
 */
public class QuadraPartCir {

	/**< The id of a record */
	private int id;
	
	/**< The strength of the relation among four parts */
	private double con;
	
	/**< The first part */
	private Part first;
	
	/**< The second part */
	private Part second;
	
	/**< The third part */
	private Part third;
	
	/**< The forth part */
	private Part forth;

	/**
	 * @brief The getter of id
	 * @return id (int)
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
	 * @brief The getter of con
	 * @return con (double)
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

	/**
	 * @brief The getter of the first part
	 * @return first part (Part)
	 */
	public Part getFirst() {
		return first;
	}

	/**
	 * @brief The setter of the first part
	 * @param first
	 */
	public void setFirst(Part first) {
		this.first = first;
	}

	/**
	 * @brief The getter of the second part
	 * @return second part(Part)
	 */
	public Part getSecond() {
		return second;
	}

	/**
	 * @brief The setter of the second part
	 * @param second
	 */
	public void setSecond(Part second) {
		this.second = second;
	}

	/**
	 * @brief The getter of the third part
	 * @return third part(Part)
	 */
	public Part getThird() {
		return third;
	}

	/**
	 * @brief The setter of the third part
	 * @param third
	 */
	public void setThird(Part third) {
		this.third = third;
	}

	/**
	 * @brief The getter of the forth part
	 * @return forth part(Part)
	 */
	public Part getForth() {
		return forth;
	}

	/**
	 * @brief The setter of the forth part
	 * @param forth
	 */
	public void setForth(Part forth) {
		this.forth = forth;
	}
	
}
