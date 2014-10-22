package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.DoublePartCir;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief DoublePartCir DAO interface
 *
 */
public interface DoublePartCirDao {

	/**
	 * @param doublePartCir
	 * @brief Add method
	 */
	public abstract void addDoublePartCir (DoublePartCir doublePartCir);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteDoublePartCir (Class clazz, Serializable serializable);
	/**
	 * @brief Analyse the part sequence
	 * @param userCir
	 * @param value
	 * @return A list of next parts
	 */
	public abstract List getNextPart (String userCir, double value);
	
	/**
	 * @brief get Next parts with a certain type
	 * @param userCir
	 * @param type
	 * @param value
	 * @return A list of next parts
	 */
	public abstract List getNextPart (String userCir,  String type, double value);
	
	/**
	 * @brief Check method
	 * @param userCir
	 * @param value
	 * @return true, false
	 */
	public abstract boolean isTrue (String[] userCir, double value);
	
}
