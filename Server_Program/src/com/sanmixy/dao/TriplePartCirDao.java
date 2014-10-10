package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.TriplePartCir;

public interface TriplePartCirDao {

	public abstract void addTriplePartCir (TriplePartCir triplePartCir);
	
	public abstract void deleteTriplePartCir (Class clazz, Serializable serializable);
	
	public abstract List getNextPart (String [] userCir, double value);
	
	public abstract List getNextPart (String [] usreCir, String type, double value);
	
	public abstract boolean isTrue (String [] userCir, double value);
	
}
