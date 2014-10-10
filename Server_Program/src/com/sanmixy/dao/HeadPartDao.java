package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.HeadPart;

public interface HeadPartDao {

	public abstract void addHeadPart (HeadPart head);
	
	public abstract void deleteHeadPart (Class clazz, Serializable serializable);
	
	public abstract List getHeadPart (double value);

	public abstract List getHeadPart (String type, double value);
	
	public abstract boolean isTrue (String partName, double value);
	
	
}
