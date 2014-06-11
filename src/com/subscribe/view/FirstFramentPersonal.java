package com.subscribe.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.subscribe.R;
import com.subscribe.bean.PersonalUser;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;

public class FirstFramentPersonal extends Fragment{
	
	private TextView tv_personalusername;
	private Button btn_subscribe;
	private String Personalusername;
	PersonalUser puser = LoginActivity.puser;
	SharedPreferences sp;
	Url url;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View firstFramentPersonal = inflater.inflate(R.layout.frament_personalfirst, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()  
        .penaltyLog()
        .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
	    .build());
        
        sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
        
        if(sp.getBoolean("CB_type", false)){
			Personalusername = sp.getString("Username", "");
		}else{
			Personalusername = puser.getUserName();
		}
        
        tv_personalusername = (TextView)firstFramentPersonal.findViewById(R.id.tv_personalusername);
        btn_subscribe = (Button)firstFramentPersonal.findViewById(R.id.btn_go_subscribe);
        btn_subscribe.setOnClickListener(new ButtonListener());
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username",Personalusername));
        String Visitname = "GetPersonalName";
		String resultpersonalName= HttpUtil.HttpSend(url, Visitname, params);
		tv_personalusername.setText(resultpersonalName);
		
		
		
		
		return firstFramentPersonal;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction tran = fragmentManager.beginTransaction();		
			SubscribeFramentPersonal sfp = new SubscribeFramentPersonal();
			tran.replace(R.id.content_frame,sfp);
			tran.commit();
		}
		
	}
}
