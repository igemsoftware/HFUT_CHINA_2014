package com.sanmixy.model;

import java.util.Date;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief It is used to store the record of the useage of all parts
 *
 */
public class RecentlyUsedPart {

	/**< The id of a record */
	private int id;
	
	/**< The part */
	private Part part;
	
	/**< The time that the part was used */
	private Date dateTime;
	
	/**< The counter */
	private int count;
	
	/**< The user information */
	private String userInfo;

	/**
	 * @brief The getter of id
	 * @return id (int)
	 */
	public int getId() {
		return id;
	}

	/**
	 * @Brief The setter of id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @brief The getter of part
	 * @return part (Part)
	 */
	public Part getPart() {
		return part;
	}

	/**
	 * @brief The setter of part
	 * @param part
	 */
	public void setPart(Part part) {
		this.part = part;
	}

	/**
	 * @brief The getter of datetime
	 * @return datetime (Date)
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * @brief The setter of datetime
	 * @param dateTime
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @brief The getter of count
	 * @return count (int)
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @brief The setter of count
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @brief The getter of user information
	 * @return user info (String)
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @brief The setter of user information
	 * @param userInfo
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
}
