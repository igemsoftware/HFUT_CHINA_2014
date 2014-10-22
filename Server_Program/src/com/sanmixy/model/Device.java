package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Model of Device
 *
 */
public class Device {

	/**< The id is the id of a device*/
	private int id;
	
	/**< The device is the name of this sequence*/
	private Part device;
	
	/**< The part_seq is a String used to store the sequence of a device*/
	private String part_seq;

	/**
	 * @bried The ID getter
	 * @param Null
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @brief The ID setter
	 * @param Id
	 * @return Null
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @brief The getter of Device
	 * @return Device part
	 */
	public Part getDevice() {
		return device;
	}

	/**
	 * @brief The setter of device
	 * @param Device
	 */
	public void setDevice(Part device) {
		this.device = device;
	}

	/**
	 * @brief The getter of part sequence
	 * @return A string of the sequence
	 */
	public String getPart_seq() {
		return part_seq;
	}

	/**
	 * @brief The setter of part sequence
	 * @param Part_seq
	 * 
	 */
	public void setPart_seq(String part_seq) {
		this.part_seq = part_seq;
	}

}
