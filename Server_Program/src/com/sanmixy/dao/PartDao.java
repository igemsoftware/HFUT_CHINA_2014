package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.Part;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Part DAO interface
 *
 */
public interface PartDao {

	/**
	 * @brief Add method
	 * @param part
	 */
	public abstract void addPart (Part part);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deletePart (Class clazz, Serializable serializable);
	
	/**
	 * @brief Find part by id
	 * @param serializable
	 * @return Part
	 */
	public abstract Part findPartByID (Serializable serializable);
	
	/**
	 * @brief Find part by part id
	 * @param partID
	 * @return Part
	 */
	public abstract Part findPartByPartID (int partID);
	
	/**
	 * @brief Find part by Whole part name
	 * @param wholeName
	 * @return Part
	 */
	public abstract Part findPartByWholePartName (String wholeName);
	
	/**
	 * @brief Find part by a name string.
	 * @param partName
	 * @return A list of parts.
	 */
	public abstract List findPartByPartName (String partName);
	
	/**
	 * @brief Find part by a certain type
	 * @param type
	 * @param num
	 * @return
	 */
	public abstract List findPartByType (String type, int num);


	
}
