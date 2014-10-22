package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.TriplePartCir;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief TriplePartCir DAO interface
 *
 */
public interface TriplePartCirDao {

	/**
	 * @brief Add method
	 * @param triplePartCir
	 */
	public abstract void addTriplePartCir (TriplePartCir triplePartCir);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteTriplePartCir (Class clazz, Serializable serializable);
	
	/**
	 * @brief Get next part based on analysis of current part sequence
	 * @param userCir
	 * @param value
	 * @return A list of next parts.
	 */
	public abstract List getNextPart (String [] userCir, double value);
	
	/**
	 * @brief Get next part based on analysis of current part sequence and part type
	 * @param usreCir
	 * @param type
	 * @param value
	 * @return A list of next parts.
	 */
	public abstract List getNextPart (String [] usreCir, String type, double value);
	
	/**
	 * @brief Check method
	 * @param userCir
	 * @param value
	 * @return true, false
	 */
	public abstract boolean isTrue (String [] userCir, double value);
	
}
