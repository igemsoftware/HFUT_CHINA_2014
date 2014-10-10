package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.Part;

public interface PartDao {

	public abstract void addPart (Part part);
	
	public abstract void deletePart (Class clazz, Serializable serializable);
	
	public abstract Part findPartByID (Serializable serializable);
	
	public abstract Part findPartByPartID (int partID);
	
	public abstract Part findPartByWholePartName (String wholeName);
	
	public abstract List findPartByPartName (String partName);


	
}
