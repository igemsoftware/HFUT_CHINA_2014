package com.sanmixy.model;

import java.util.Date;

public class JSONRecentlyUsedPart {

	private int partID;
	
	private String partName;
	
	private String type;
	
	private String partUrl;
	
	private int count;
	
	private Date datetime;

	private String ipAddress;
	
	public int getPartID() {
		return partID;
	}

	public String getPartName() {
		return partName;
	}

	public String getType() {
		return type;
	}

	public String getPartUrl() {
		return partUrl;
	}

	public int getCount() {
		return count;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setPartID(int partID) {
		this.partID = partID;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
