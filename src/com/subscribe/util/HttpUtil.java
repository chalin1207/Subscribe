package com.subscribe.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.subscribe.bean.Url;


public class HttpUtil {
	//向服务器发送数据
		public static String HttpSend(Url url,String Visitname,List<NameValuePair> params){
			url = new Url();
			HttpClient httpclient =new DefaultHttpClient();
System.out.println("访问的服务器地址："+url.getUrl()+Visitname);
			HttpPost httppost = new HttpPost(url.getUrl()+Visitname);
			try {
				httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpResponse httpresponse = httpclient.execute(httppost);
				
				if(httpresponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
					String mess = EntityUtils.toString(httpresponse.getEntity());
System.out.println(mess);
						return mess;
				}else{
					return "404";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
}
