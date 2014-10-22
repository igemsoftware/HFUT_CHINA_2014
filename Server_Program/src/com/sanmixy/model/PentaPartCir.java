package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief This class is used to describe the relations among five parts
 *
 */
public class PentaPartCir {

	/**< The id of a record in database */
	private int id;
	
	/**< The strength of a relation */
	private double con;
	
	/**< The first part */
	private Part first;
	
	/**< The second part */
	private Part second;
	
	/**< The third part */
	private Part third;
	
	/**< The forth part */
	private Part forth;

	/**< The fifth part */
	private Part fifth;
	
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
	 * @brief The getter of first part
	 * @return first part (Part)
	 */
	public Part getFirst() {
		return first;
	}

	/**
	 * @brief The setter of First part
	 * @param first
	 */
	public void setFirst(Part first) {
		this.first = first;
	}

	/**
	 * @brief The getter of second part
	 * @return second part (Part)
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

	/**
	 * @brief The getter of third part
	 * @return third part (Part)
	 */
	public Part getThird() {
		return third;
	}

	/**
	 * @brief The setter of third part
	 * @param third
	 */
	public void setThird(Part third) {
		this.third = third;
	}

	/**
	 * @brief THe getter of Forth part
	 * @return forth part(Part)
	 */
	public Part getForth() {
		return forth;
	}

	/**
	 * @brief The setter of forth part
	 * @param forth
	 */
	public void setForth(Part forth) {
		this.forth = forth;
	}

	/**
	 * @brief The getter of the fifth part
	 * @return fifth part (Part)
	 */
	public Part getFifth() {
		return fifth;
	}

	/**
	 * @brief The setter of the fifth part
	 * @param fifth
	 */
	public void setFifth(Part fifth) {
		this.fifth = fifth;
	}

}
