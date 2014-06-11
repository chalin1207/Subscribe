package com.subscribe.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.content.Intent;
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
import com.subscribe.util.HttpUtil;

public class RegistrFramentCompany extends Fragment{
	
	private Button bt_registr,bt_cancel;
	private EditText et_companyName,et_companyUsername,et_companyPassword,et_companyPassword2;
	String Visitname = null;
	Url url;
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.companyregistr){
				if(et_companyName.getText().toString().equals("")||et_companyUsername.getText().toString().equals("")||et_companyPassword.getText().toString().equals("")||et_companyPassword2.getText().toString().equals("")){
					Toast.makeText(getActivity(), "请输入所有信息", Toast.LENGTH_LONG).show();
				} else {
					if (et_companyPassword.getText().toString().trim().equals(et_companyPassword2.getText().toString().trim())) {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("username",et_companyUsername.getText().toString().trim()));
						params.add(new BasicNameValuePair("password",et_companyPassword.getText().toString().trim()));
						params.add(new BasicNameValuePair("companyname",et_companyName.getText().toString().trim()));

						Visitname = "RegistrCompanyUser";
						String result = HttpUtil.HttpSend(url, Visitname,
								params);
						System.out.println("噢噢噢噢噢噢噢噢，喜欢，我喜欢你" + result);
						if (result.equals("Sucess")) {
							Toast.makeText(getActivity(), "恭喜你，注册成功~",Toast.LENGTH_LONG).show();
						} else if (result.equals("Repeat")) {
							Toast.makeText(getActivity(), "对不起，该用户已存在~",Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(getActivity(), "两次输入的密码不一致~",
								Toast.LENGTH_LONG).show();
					}
				}
			}if(v.getId() == R.id.companycancel){
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
			}
		}
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View companyregistr = inflater.inflate(R.layout.frament_company_registr, container, false);
		
		bt_registr = (Button)companyregistr.findViewById(R.id.companyregistr);
		bt_cancel = (Button)companyregistr.findViewById(R.id.companycancel);
		
		et_companyName = (EditText)companyregistr.findViewById(R.id.et_companyname);
		et_companyUsername = (EditText)companyregistr.findViewById(R.id.et_company_username);
		et_companyPassword = (EditText)companyregistr.findViewById(R.id.et_company_password);
		et_companyPassword2 = (EditText)companyregistr.findViewById(R.id.et_company_password2);
		
		bt_registr.setOnClickListener(new ButtonListener());
		bt_cancel.setOnClickListener(new ButtonListener());
		
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
		
		return companyregistr;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
}
