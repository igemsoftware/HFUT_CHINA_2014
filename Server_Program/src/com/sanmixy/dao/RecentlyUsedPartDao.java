package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.RecentlyUsedPart;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief RecentlyUsedPart DAO interface
 *
 */
public interface RecentlyUsedPartDao {

	/**
	 * @brief Add method
	 * @param record
	 */
	public abstract void addRecord (RecentlyUsedPart record);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteRecord (Class clazz, Serializable serializable);
	
	/**
	 * @brief Get all recently used parts
	 * @return A list of parts
	 */
	public abstract List getRecentlyUsedPart ();
	
	/**
	 * @brief Get parts by user information 
	 * @param userInfo
	 * @return A list of Parts
	 */
	public abstract List getRecentlyUsedPartByUserInfo (String userInfo);
	
	/**
	 * @brief Get parts by part type
	 * @param type
	 * @return A list of parts
	 * 
	 */
	public abstract List getRecentlyUsedPartByType (String type);
	
	/**
	 * @brief Get parts by user information and part type
	 * @param type
	 * @param userInfo
	 * @return A list of parts
	 */
	public abstract List getRecentlyUsedPartByTypeAndUserInfo (String type, String userInfo);
	
	/**
	 * @brief Get the count of a part
	 * @param partName
	 * @return count
	 */
	public abstract int findCountByPartName (String partName);
	
	/**
	 * @brief Get the count of part by part name and user information
	 * @param partName
	 * @param userInfo
	 * @return count
	 */
	public abstract int findCountByPartNameAndUserInfo (String partName, String userInfo);
	
	/**
	 * @brief Delete method
	 * @param id
	 */
	public abstract void deleteRecordByID (int id);
	
	/**
	 * @brief Find the record id by part name and user information
	 * @param partName
	 * @param userInfo
	 * @return id
	 */
	public abstract int findIDByPartNameAndUserInfo(String partName, String userInfo);
	
}
