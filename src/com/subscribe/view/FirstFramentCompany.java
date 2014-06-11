package com.subscribe.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.subscribe.R;
import com.subscribe.bean.CompanyUser;

public class FirstFramentCompany extends Fragment{
	
	private TextView tv_companyusername;
	private Button btn_callnumber;
	CompanyUser cuser = LoginActivity.user;
	SharedPreferences sp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View firstFramentCompany = inflater.inflate(R.layout.frament_companyfirst, container, false);
		
		tv_companyusername = (TextView)firstFramentCompany.findViewById(R.id.tv_companyusername);
		btn_callnumber = (Button)firstFramentCompany.findViewById(R.id.btn_go_callnumber);
		btn_callnumber.setOnClickListener(new ButtonListener());
		
		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		
		if(sp.getBoolean("CB_type", false)){
			tv_companyusername.setText(sp.getString("Username", ""));
		}else{
			tv_companyusername.setText(cuser.getUserName());
		}
		
		return firstFramentCompany;
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
			CallNumberFramentCompany cnfc = new CallNumberFramentCompany();
			tran.replace(R.id.content_frame,cnfc);
			tran.commit();
		}
		
	}
	
}
