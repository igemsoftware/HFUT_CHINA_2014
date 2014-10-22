package com.sanmixy.model;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief It is a JSON model class. We put recently used part in the object.
 *
 */
public class JSONRecentlyUsedPart {

	/**< It is the id of a part */
	private int partID;
	
	/**< It is the name of a part */
	private String partName;
	
	/**< It is the type of a part */
	private String type;
	
	/**< It is the url of a part */
	private String partUrl;
	
	/**< It is a counter of the part of users used */
	private int count;
	
	/**< It is the time*/
	private String datetime;

	/**< It is the user id */
	private String userInfo;
	
	/**
	 * @brief The getter of part id;
	 * @return partID
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
	 * @return type (String)
	 */
	public String getType() {
		return type;
	}

	/**
	 * @brief The getter of part url
	 * @return part url (String)
	 */
	public String getPartUrl() {
		return partUrl;
	}

	/**
	 * @brief The getter of count
	 * @return count (int)
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @brief The getter of datetime
	 * @return datetime (Date)
	 */
	public String getDatetime() {
		return datetime;
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
	 * @brief The setter of part url
	 * @param partUrl
	 */
	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	/**
	 * @brief The setter of count
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @brief The setter of datetime
	 * @param datetime
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	/**
	 * @brief The getter of userInfo
	 * @return userInfo (String)
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @brief The setter of userInfo
	 * @param userInfo
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

}
