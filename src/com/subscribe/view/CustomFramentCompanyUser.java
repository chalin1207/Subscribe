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
import com.subscribe.bean.CompanyUser;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;

public class CustomFramentCompanyUser extends Fragment{
	
	private Button btn_sure;
	private EditText et_Username,et_Password,et_Password2,et_Root;
	Url url;
	SharedPreferences sp;
	CompanyUser user = LoginActivity.user;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View customFramentCompanyUser = inflater.inflate(R.layout.frament_customcompanyuser, container, false);
		
		btn_sure = (Button)customFramentCompanyUser.findViewById(R.id.custom_execute);
		
		et_Username = (EditText)customFramentCompanyUser.findViewById(R.id.et_customUsername);
		et_Password = (EditText)customFramentCompanyUser.findViewById(R.id.et_customPassword);
		et_Password2 = (EditText)customFramentCompanyUser.findViewById(R.id.et_customPassword2);
		et_Root = (EditText)customFramentCompanyUser.findViewById(R.id.et_customRoot);

		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		
		btn_sure.setOnClickListener(new ButtonListener());
		
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
		
		return customFramentCompanyUser;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String username = et_Username.getText().toString().trim();
			String password = et_Password.getText().toString().trim();
			String password2 = et_Password2.getText().toString().trim();
			String root = et_Root.getText().toString().trim();
			if(username.equals("")||password.equals("")||password2.equals("")||root.equals("")){
				Toast.makeText(getActivity(), "请输入所有信息", Toast.LENGTH_LONG).show();
			}else{
				if(password.equals(password2)){
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					 if(sp.getBoolean("CB_type", false)){
						 	params.add(new BasicNameValuePair("companyname", (sp.getString("NAME", ""))));
					  }else{
						  params.add(new BasicNameValuePair("companyname", user.getCompanyName()));
					 }
					params.add(new BasicNameValuePair("root", et_Root.getText().toString().trim()));
					params.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
					params.add(new BasicNameValuePair("password",et_Password.getText().toString().trim()));
					
					String Visitname = "CustomCompanyUser";
					String Customresult = HttpUtil.HttpSend(url, Visitname, params);
					if(Customresult.equals("NOExiste")){
						Toast.makeText(getActivity(), "该企业没有这一用户", Toast.LENGTH_LONG).show();
					}else if(Customresult.equals("PassErr")){
						Toast.makeText(getActivity(), "密码输入错误", Toast.LENGTH_LONG).show();
					}else if(Customresult.equals("Sucess")){
						Toast.makeText(getActivity(), "该用户定制完成", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getActivity(), "两次输入的密码不一致", Toast.LENGTH_LONG).show();
				}
			}
		}
		
	}
}
