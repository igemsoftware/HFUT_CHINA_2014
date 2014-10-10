package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.RecentlyUsedPart;

public interface RecentlyUsedPartDao {

	public abstract void addRecord (RecentlyUsedPart record);
	
	public abstract void deleteRecord (Class clazz, Serializable serializable);
	
	public abstract List getRecentlyUsedPart ();
	
	public abstract List getRecentlyUsedPartByIp (String ipAddress);
	
	public abstract List getRecentlyUsedPartByType (String type);
	
	public abstract List getRecentlyUsedPartByTypeAndIp (String type, String ipAddress);
	
	
}
