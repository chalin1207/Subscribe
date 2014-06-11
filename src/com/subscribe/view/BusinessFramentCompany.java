package com.subscribe.view;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.subscribe.R;
import com.subscribe.bean.Url;
import com.subscribe.bean.CompanyUser;
import com.subscribe.util.HttpUtil;


public class BusinessFramentCompany extends Fragment{
	
	private Button btn_execute;
	private EditText et_Hullnum,et_Roomnum,et_Starttme,et_Endtime;
	String Visitname = null;
	Url url;
	CompanyUser user = LoginActivity.user;
	SharedPreferences sp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View businessFramentCompany = inflater.inflate(R.layout.frament_business, container, false);
		btn_execute = (Button)businessFramentCompany.findViewById(R.id.businness_execute);
		
		et_Hullnum = (EditText)businessFramentCompany.findViewById(R.id.et_hallnum);
		et_Roomnum = (EditText)businessFramentCompany.findViewById(R.id.et_roomnum);
		et_Starttme = (EditText)businessFramentCompany.findViewById(R.id.et_starttime);
		et_Endtime = (EditText)businessFramentCompany.findViewById(R.id.et_endtime);
		
		btn_execute.setOnClickListener(new ButtonListener());
		
		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		
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
		
		return businessFramentCompany;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	public class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {	   
		   String Hullnum = et_Hullnum.getText().toString().trim();
		   String Roomnum = et_Roomnum.getText().toString().trim();
		   String Starttime = et_Starttme.getText().toString().trim();
		   String Endtime = et_Endtime.getText().toString().trim();
		   if(Hullnum.equals("")||Roomnum.equals("")||Starttime.equals("")||Endtime.equals("")){
			   Toast.makeText(getActivity(), "请输入所有信息", Toast.LENGTH_LONG).show();
		   }else{
			   //TODO 如果按了自动登录，就从sp里面读取
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   if(sp.getBoolean("CB_type", false)){
System.out.println("-----------------------------------------------------------"+sp.getString("Username", ""));
				   params.add(new BasicNameValuePair("companyname", (sp.getString("Username", ""))));
			   }else{
			       params.add(new BasicNameValuePair("companyname", user.getUserName()));
			   }
				params.add(new BasicNameValuePair("hallnum",Hullnum));
				params.add(new BasicNameValuePair("roomnum",Roomnum));
				params.add(new BasicNameValuePair("starttime",Starttime));
				params.add(new BasicNameValuePair("endtime",Endtime));
				
				Visitname = "CompanyCustomization";
				String result = HttpUtil.HttpSend(url, Visitname, params);
				
				if(result.equals("Sucess")){
					Toast.makeText(getActivity(), "企业业务已执行", Toast.LENGTH_LONG).show();
				}else if(result.equals("Repeat")){
					Toast.makeText(getActivity(), "企业业务不能重复执行", Toast.LENGTH_LONG).show();
				}
		   }
		}
		
	}
	
}
