package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.Device;

public interface DeviceDao{

	public abstract void addDevice (Device device);
	
	public abstract void deleteDevice (Class clazz, Serializable serializable);
	
	public abstract Device findDeviceByID (Serializable serializable);
	
	public abstract Device findDeviceByName (String deviceName);
	
	public abstract List findDeviceByPartSeq (String partNameSeq);
	
	public abstract boolean isDeviceExist (String partName);
	
	public abstract boolean isSeqExist (String part_seq);
}
