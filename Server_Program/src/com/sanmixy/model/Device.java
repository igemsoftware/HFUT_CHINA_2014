package com.sanmixy.model;

public class Device {

	private int id;
	
	private Part device;
	
	private String part_seq;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Part getDevice() {
		return device;
	}

	public void setDevice(Part device) {
		this.device = device;
	}

	public String getPart_seq() {
		return part_seq;
	}

	public void setPart_seq(String part_seq) {
		this.part_seq = part_seq;
	}

}
