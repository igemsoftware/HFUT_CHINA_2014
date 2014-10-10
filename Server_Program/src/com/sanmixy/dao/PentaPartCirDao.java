package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.PentaPartCir;

public interface PentaPartCirDao {

	public abstract void addPentaPartCir (PentaPartCir pentaPartCir);
	
	public abstract void deletePentaPartCir (Class clazz, Serializable serializable);
	
	public abstract List getNextPart (String [] userCir, double value);
	
	public abstract List getNextPart (String [] userCir, String type, double value);
	
	public abstract boolean isTrue (String [] usreCir, double value);
	
}
