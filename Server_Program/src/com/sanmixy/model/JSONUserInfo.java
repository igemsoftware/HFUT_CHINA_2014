package com.sanmixy.model;

public class JSONUserInfo {

	private String userInfo;
	
	public JSONUserInfo (String userInfo){
		
		this.userInfo = userInfo;
		
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
}
