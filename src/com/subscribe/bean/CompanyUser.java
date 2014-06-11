package com.subscribe.bean;

public class CompanyUser {
	private String userName;
	private String CompanyName;
	
	public CompanyUser(){}
	
	public CompanyUser(String userName,String companyName){
		this.userName = userName;
		this.CompanyName = companyName;
System.out.println("username:"+userName+"   companyname:"+companyName);
	}
	
	public String getUserName() {
		return userName;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
