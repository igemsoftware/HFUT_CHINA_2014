package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.QuadraPartCir;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief QuadraPartCir DAO interface
 *
 */
public interface QuadraPartCirDao {

	/**
	 * @brief Add method
	 * @param quadraPartCir
	 */
	public abstract void addQuadraPartCir (QuadraPartCir quadraPartCir);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteQuadraPartCir (Class clazz, Serializable serializable);
	
	/**
	 * @brief Get next part based on analysis the current part sequence
	 * @param userCir
	 * @param value
	 * @return A list of next parts
	 */
	public abstract List getNextPart (String [] userCir, double value);
	
	/**
	 * @brief Get next part based on analysis the current part sequence and part type
	 * @param userCir
	 * @param type
	 * @param value
	 * @return A list of next partss
	 */
	public abstract List getNextPart (String [] userCir, String type, double value);
	
	/**
	 * @brief Check method
	 * @param userCir
	 * @param value
	 * @return true, false
	 */
	public abstract boolean isTrue (String [] userCir, double value);
	
}
