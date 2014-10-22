package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief The Twins class is used to store the twin parts of a base part.
 *
 */
public class Twins {

	/**< The id of a record */
	private int id;
	
	/**< The base part */
	private Part basePart;
	
	/**< The twin part of the base part */
	private Part twinPart;

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
	 * @brief The getter of the base part
	 * @return basepart (Part)
	 */
	public Part getBasePart() {
		return basePart;
	}

	/**
	 * @brief The setter of the base part
	 * @param basePart
	 */
	public void setBasePart(Part basePart) {
		this.basePart = basePart;
	}

	/**
	 * @brief The getter of the twin part
	 * @return twinpart (Part)
	 */
	public Part getTwinPart() {
		return twinPart;
	}

	/**
	 * @brief The setter of the twin part
	 * @param twinPart
	 */
	public void setTwinPart(Part twinPart) {
		this.twinPart = twinPart;
	}

}
