package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.HeadPart;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Headpart DAO interface
 *
 */
public interface HeadPartDao {

	/**
	 * @brief Add method
	 * @param head
	 */
	public abstract void addHeadPart (HeadPart head);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteHeadPart (Class clazz, Serializable serializable);
	
	/**
	 * @brief Get next part
	 * @param value
	 * @return A list of next part
	 */
	public abstract List getHeadPart (double value);

	/**
	 * @brief Get next part with a certain type
	 * @param type
	 * @param value
	 * @return A list of next part
	 */
	public abstract List getHeadPart (String type, double value);
	
	/**
	 * @brief Check method
	 * @param partName
	 * @param value
	 * @return true, false
	 */
	public abstract boolean isTrue (String partName, double value);
	
	
}
