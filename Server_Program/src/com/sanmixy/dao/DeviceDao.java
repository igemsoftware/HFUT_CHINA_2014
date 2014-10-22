package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.Device;

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Device DAO interface
 *
 */
public interface DeviceDao{

	/**
	 * @brief Add method
	 * @param device
	 */
	public abstract void addDevice (Device device);
	
	/**
	 * @brief Delete method
	 * @param clazz
	 * @param serializable
	 */
	public abstract void deleteDevice (Class clazz, Serializable serializable);
	
	/**
	 * @brief Find device by ID
	 * @param serializable
	 * @return Device
	 */
	public abstract Device findDeviceByID (Serializable serializable);
	
	/**
	 * @brief Find device by name
	 * @param deviceName
	 * @return Device
	 */
	public abstract Device findDeviceByName (String deviceName);
	
	/**
	 * @brief Find device by part sequence
	 * @param partNameSeq
	 * @return A list of Device
	 */
	public abstract List findDeviceByPartSeq (String partNameSeq);
	
	/**
	 * @brief Check method
	 * @param partName
	 * @return true, false
	 */
	public abstract boolean isDeviceExist (String partName);
	
	/**
	 * @brief Check method
	 * @param part_seq
	 * @return true, false
	 */
	public abstract boolean isSeqExist (String part_seq);
}
