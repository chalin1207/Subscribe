package com.subscribe.bean;

public class Url {
	//private String url="http://10.10.16.12:3000/";//����δ����ʱ��IP��ַ
    //private String url="http://10.10.16.165:3000/";//����δ����ʱ��IP��ַ
    //private String url="http://192.168.0.51:3000/";//�������ʱ��IP��ַ
	private String url="http://10.10.16.63:3000/";//�������ʱ��IP��ַ
	//private String url="http://10.10.16.78:3000/";//�������ʱ��IP��ַ
	//private String url = "http://169.254.211.90:3000/";
	
	public Url(){}
	
	public Url(String url){
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
