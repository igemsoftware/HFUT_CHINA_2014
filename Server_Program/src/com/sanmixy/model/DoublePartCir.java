package com.sanmixy.model;

public class DoublePartCir {

	private int id;
	
	private double con;
	
	private Part first;
	
	private Part second;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCon() {
		return con;
	}

	public void setCon(double con) {
		this.con = con;
	}

	public Part getFirst() {
		return first;
	}

	public void setFirst(Part first) {
		this.first = first;
	}

	public Part getSecond() {
		return second;
	}

	public void setSecond(Part second) {
		this.second = second;
	}
	
}
