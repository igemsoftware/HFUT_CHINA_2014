package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.QuadraPartCir;

public interface QuadraPartCirDao {

	public abstract void addQuadraPartCir (QuadraPartCir quadraPartCir);
	
	public abstract void deleteQuadraPartCir (Class clazz, Serializable serializable);
	
	public abstract List getNextPart (String [] userCir, double value);
	
	public abstract List getNextPart (String [] userCir, String type, double value);
	
	public abstract boolean isTrue (String [] userCir, double value);
	
}
