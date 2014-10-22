package com.sanmixy.model;

import java.util.Set;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief The model class which is used to store biobrick part
 *
 */
public class Part {

	/**< The id of a record */
	private int id;
	
	/**< The id of a part */
	private int partID;
	
	/**< The name of a part */
	private String partName;
	
	/**< The gene sequence of a part */
	private String sequence;
	
	/**< The type of a part */
	private String type;
	
	/**< The url of a part */
	private String partUrl;
	
	/**< We create the attribute in order to create a one to one relation between two table */
	private Device device;

	public Part (){
		
	}
	
	public Part (int id, int partID, String partName, String partUrl, String type){
		
	}
	

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
	 * @brief The getter of partId
	 * @return partID (int)
	 */
	public int getPartID() {
		return partID;
	}

	/**
	 * @brief The setter of partID
	 * @param partID
	 */
	public void setPartID(int partID) {
		this.partID = partID;
	}

	/**
	 * @brief The getter of part name
	 * @return part name (String)
	 */
	public String getPartName() {
		return partName;
	}

	/**
	 * @brief The setter of part name
	 * @param partName
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * @brief The getter of sequence of a part
	 * @return sequence (String)
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @brief The setter of sequence
	 * @param sequence
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * @brief The getter of type
	 * @return type (String)
	 */
	public String getType() {
		return type;
	}

	/**
	 * @brief The setter of type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @brief The getter of part url
	 * @return part url (String)
	 */
	public String getPartUrl() {
		return partUrl;
	}

	/**
	 * @brief The setter of part url
	 * @param partUrl
	 */
	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	/**
	 * @brief The getter of device
	 * @return device (Part)
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @brief The setter of devicep
	 * @param device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}
	
}
