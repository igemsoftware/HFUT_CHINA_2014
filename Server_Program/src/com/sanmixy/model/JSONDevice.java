package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief It is a JSON model class. We create it with our parameters, and return to users and client with the information of device
 *
 */
public class JSONDevice {

	/**< It is the id of a part */
	private int partID;
	
	/**< It is the name of a part */
	private String partName;
	
	/**< It is the type of a part */
	private String type;
	
	/**< It is the url of a part */
	private String partUrl;
	
	/**< It is the sequence of a device */
	private String part_seq;

	/**
	 * @brief The getter of partID
	 * @return partID(int)
	 */
	public int getPartID() {
		return partID;
	}

	/**
	 * @brief The getter of part name
	 * @return part name (String)
	 */
	public String getPartName() {
		return partName;
	}

	/**
	 * @brief The getter of type
	 * @return type(String)
	 */
	public String getType() {
		return type;
	}

	/**
	 * @brief The getter of part url
	 * @return partUrl(String)
	 */
	public String getPartUrl() {
		return partUrl;
	}

	/**
	 * @brief The setter of part id
	 * @param partID
	 */
	public void setPartID(int partID) {
		this.partID = partID;
	}

	/**
	 * @brief The setter of part name
	 * @param partName
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * @brief The setter of type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @brief The setter a part url
	 * @param partUrl
	 */
	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	/**
	 * @brief The getter of part_seq
	 * @return part_seq(String)
	 */
	public String getPart_seq() {
		return part_seq;
	}

	/**
	 * @brief The setter of part_seq
	 * @param part_seq
	 */
	public void setPart_seq(String part_seq) {
		this.part_seq = part_seq;
	}

}
