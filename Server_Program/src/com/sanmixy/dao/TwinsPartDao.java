package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.Twins;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Twins DAO interface
 */
public interface TwinsPartDao {

	/**
	 * @brief Add method
	 * @param twins
	 */
	public abstract void addTwinsPart (Twins twins);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteTwinsPart (Class clazz, Serializable serializable);

	/**
	 * @brief Find twins by part name
	 * @param wholeName
	 * @return A list of twin parts.
	 */
	public abstract List findTwinsByBasePartName (String wholeName);
	
}
