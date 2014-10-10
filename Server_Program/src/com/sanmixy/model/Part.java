package com.sanmixy.model;

import java.util.Set;

public class Part {

	private int id;
	
	private int partID;
	
	private String partName;
	
	private String sequence;
	
	private String type;
	
	private String partUrl;
	
	private RecentlyUsedPart recentlyUsedPart;
	
	private Device device;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPartID() {
		return partID;
	}

	public void setPartID(int partID) {
		this.partID = partID;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartUrl() {
		return partUrl;
	}

	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	public RecentlyUsedPart getRecentlyUsedPart() {
		return recentlyUsedPart;
	}

	public void setRecentlyUsedPart(RecentlyUsedPart recentlyUsedPart) {
		this.recentlyUsedPart = recentlyUsedPart;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
}
