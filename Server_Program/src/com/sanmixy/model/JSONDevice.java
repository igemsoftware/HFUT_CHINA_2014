package com.sanmixy.model;

public class JSONDevice {

	private int partID;
	
	private String partName;
	
	private String type;
	
	private String partUrl;
	
	private String part_seq;

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

	public String getPart_seq() {
		return part_seq;
	}

	public void setPart_seq(String part_seq) {
		this.part_seq = part_seq;
	}

}
