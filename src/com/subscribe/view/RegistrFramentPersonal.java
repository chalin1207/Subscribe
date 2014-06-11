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

public class RegistrFramentPersonal extends Fragment {

	private Button bt_personlRegistr, bt_personlCancel;
	private EditText et_personalUsername,et_personalName, et_personalPassword, et_personalPassword2;
	Url url;
	private String Visitname;

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.personlregistr) {
				if(et_personalUsername.getText().toString().equals("")||et_personalName.getText().toString().equals("")||et_personalPassword.getText().toString().equals("")||et_personalPassword2.getText().toString().equals("")){
					Toast.makeText(getActivity(), "请输入所有信息", Toast.LENGTH_LONG).show();
				} else {
					if (et_personalPassword.getText().toString().trim().equals(et_personalPassword2.getText().toString().trim())) {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("username",et_personalUsername.getText().toString().trim()));
						params.add(new BasicNameValuePair("password",et_personalPassword.getText().toString().trim()));
						params.add(new BasicNameValuePair("personalname",et_personalName.getText().toString().trim()));
						Visitname = "RegistrPersonalUser";
						String result = HttpUtil.HttpSend(url, Visitname,
								params);
						System.out.println("sdfsdfsdfsdfdsaafasdfa" + result);
						// String newResult = result.replaceAll("\"","");
						if (result.equals("Sucess")) {// 注册成功
							Toast.makeText(getActivity(), "注册成功~",Toast.LENGTH_SHORT).show();
						} else if (result.equals("Repeat")) {// 用户已存在
							Toast.makeText(getActivity(), "该用户已存在~",Toast.LENGTH_SHORT).show();
						}
					} else {// 两次密码不一致
						Toast.makeText(getActivity(), "两次输入的密码不一致，请重新输入~",Toast.LENGTH_SHORT).show();
					}
				}
			}
			if (v.getId() == R.id.personlcancel) {
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
		View personlregistr = inflater.inflate(
				R.layout.frament_personl_registr, container, false);

		bt_personlRegistr = (Button) personlregistr
				.findViewById(R.id.personlregistr);
		bt_personlCancel = (Button) personlregistr
				.findViewById(R.id.personlcancel);
		et_personalUsername = (EditText) personlregistr
				.findViewById(R.id.et_personl_username);
		et_personalPassword = (EditText) personlregistr
				.findViewById(R.id.et_personl_password);
		et_personalPassword2 = (EditText) personlregistr
				.findViewById(R.id.et_personl_password2);
		et_personalName = (EditText)personlregistr
				.findViewById(R.id.et_personl_name);
		bt_personlRegistr.setOnClickListener(new ButtonListener());
		bt_personlCancel.setOnClickListener(new ButtonListener());
		
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
		
		return personlregistr;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

}
