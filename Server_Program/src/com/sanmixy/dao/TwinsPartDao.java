package com.sanmixy.dao;

import java.io.Serializable;
import java.util.List;

import com.sanmixy.model.Twins;

public interface TwinsPartDao {

	public abstract void addTwinsPart (Twins twins);
	
	public abstract void deleteTwinsPart (Class clazz, Serializable serializable);
	
	public abstract List findTwinsByBasePartName (String wholeName);
	
}
