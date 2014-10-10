package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.DoublePartCir;

public interface DoublePartCirDao {

	public abstract void addDoublePartCir (DoublePartCir doublePartCir);
	
	public abstract void deleteDoublePartCir (Class clazz, Serializable serializable);
	
	public abstract List getNextPart (String userCir, double value);
	
	public abstract List getNextPart (String userCir,  String type, double value);
	
	public abstract boolean isTrue (String[] userCir, double value);
	
}
