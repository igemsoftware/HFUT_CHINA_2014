package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.PentaPartCir;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief The PentaPartCir DAO interface
 *
 */
public interface PentaPartCirDao {
	
	/**
	 * @breif Add method
	 * @param pentaPartCir
	 */
	public abstract void addPentaPartCir (PentaPartCir pentaPartCir);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deletePentaPartCir (Class clazz, Serializable serializable);
	
	/**
	 * @brief Get next part based on analysis of current part sequence
	 * @param userCir
	 * @param value
	 * @return A list of next part
	 */
	public abstract List getNextPart (String [] userCir, double value);
	
	/**
	 * @brief Get next part based on analysis of current part sequence and part type
	 * @param userCir
	 * @param type
	 * @param value
	 * @return A list of parts
	 */
	public abstract List getNextPart (String [] userCir, String type, double value);
	
	/**
	 * @brief Check method
	 * @param usreCir
	 * @param value
	 * @return true, false
	 */
	public abstract boolean isTrue (String [] usreCir, double value);
	
}
