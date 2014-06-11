package com.subscribe.bean;

public class PersonalUser {
	private String userName;
	private String personalName;
	
	public PersonalUser(){}
	
	public PersonalUser(String userName,String personalName){
		this.userName = userName;
		this.personalName = personalName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPersonalName() {
		return personalName;
	}
	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
}
