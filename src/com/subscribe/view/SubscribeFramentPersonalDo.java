package com.subscribe.view;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.subscribe.R;
import com.subscribe.bean.PersonalUser;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;
import com.subscribe.view.PersonalSubscribeDialog.PriorityListener;

public class SubscribeFramentPersonalDo extends Fragment{
	
	private TextView tv_RoomNum,tv_HullNum,tv_companyName,tv_RoomNum_ed,tv_HullNum_ed;
	String CompanyName,CompanyUsername,HullNum,RoomNum,PersonalUsername,PersonalName,OrderTime;
	private Button btn_hull,btn_room;
	Url url;
	String HullCount = null;//当前大厅已预订的数量
	String RoomCount = null;//当前房间已预约的数量
	PersonalUser puser = LoginActivity.puser;
	JSONObject json;
	SharedPreferences sp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View subscribeFramentPersonalDO = inflater.inflate(R.layout.frament_personalsubscribe_imp, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	
		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		
		tv_HullNum = (TextView)subscribeFramentPersonalDO.findViewById(R.id.tv_hullnum);
		tv_RoomNum = (TextView)subscribeFramentPersonalDO.findViewById(R.id.tv_roomnum);
		tv_companyName = (TextView)subscribeFramentPersonalDO.findViewById(R.id.tv_companyName);
		tv_RoomNum_ed = (TextView)subscribeFramentPersonalDO.findViewById(R.id.tv_roomnum_ed);
		tv_HullNum_ed = (TextView)subscribeFramentPersonalDO.findViewById(R.id.tv_hullnum_ed);
		
		btn_hull = (Button)subscribeFramentPersonalDO.findViewById(R.id.btn_book_hull);
		btn_room = (Button)subscribeFramentPersonalDO.findViewById(R.id.btn_book_room);
		
		//获取系统时间
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。  
		t.setToNow();
		int hour = t.hour; // 0-23  
		int minute = t.minute;  
		int second = t.second;
		OrderTime = hour+":"+minute+":"+second;
		if(sp.getBoolean("CB_type", false)){
			PersonalUsername = sp.getString("Username", "");
			PersonalName = sp.getString("NAME", "");
		}else{
		    PersonalUsername = puser.getUserName();
		    PersonalName = puser.getPersonalName();
		}
		CompanyName = SubscribeFramentPersonal.companyName;
		CompanyUsername = SubscribeFramentPersonal.companyUsername;
		HullNum = SubscribeFramentPersonal.hullNum;
		RoomNum = SubscribeFramentPersonal.roomNum;
		
System.out.println(CompanyName+"  "+CompanyUsername+"   "+HullNum+"    "+RoomNum +"   peruser:"+PersonalUsername+"    pername:"+PersonalName);

        GetNum();//获得大厅，房间的已预约数
        
		tv_HullNum.setText(HullNum);
		tv_RoomNum.setText(RoomNum);
		tv_companyName.setText(CompanyName);
		tv_HullNum_ed.setText(HullCount);
		tv_RoomNum_ed.setText(RoomCount);
		
		btn_hull.setOnClickListener(new ButtonListener());
		btn_room.setOnClickListener(new ButtonListener());
		
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

        return subscribeFramentPersonalDO;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private void GetNum(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyuser",CompanyName));
		params.add(new BasicNameValuePair("orderwhere","HULL"));
		String Visitname = "GetCompanyCountNum";
		String HC = HttpUtil.HttpSend(url, Visitname, params);
		try {
			json = new JSONObject(HC);
			HullCount = json.getString("count(*)");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("companyuser",CompanyName));
		params1.add(new BasicNameValuePair("orderwhere","ROOM"));
		String Visitname1 = "GetCompanyCountNum";
		String RC = HttpUtil.HttpSend(url, Visitname1, params1);
		try {
			json = new JSONObject(RC);
			RoomCount = json.getString("count(*)");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("HullCount:"+HullCount+"     RoomCount:"+RoomCount+"    "+CompanyName);
	}
	
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.btn_book_hull){
				
				PersonalSubscribeDialog psd = new PersonalSubscribeDialog(getActivity(),new PriorityListener() {
					
					@Override
					public void refreshPriorityUI(String string) {
						if(string.equals("SRUE")){
							String OrderWhere = "HULL";
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("personalname",PersonalName));
							params.add(new BasicNameValuePair("personalusername",PersonalUsername));
							params.add(new BasicNameValuePair("companyname",CompanyName));
							params.add(new BasicNameValuePair("ordertime",OrderTime));
							params.add(new BasicNameValuePair("orderid",(Integer.parseInt(HullCount)+1)+""));
							params.add(new BasicNameValuePair("orderwhere",OrderWhere));
							String Visitname = "PersonalSubscribe";
							String subscbeHull = HttpUtil.HttpSend(url, Visitname, params);
							if(subscbeHull.equals("Sucess")){
								Toast.makeText(getActivity(), "亲，恭喜你，预约成功了", Toast.LENGTH_LONG).show();
							}else if(subscbeHull.equals("Existe")){
								Toast.makeText(getActivity(), "亲，你已经在"+CompanyName+"大厅预约了喔", Toast.LENGTH_LONG).show();
							}
							
							List<NameValuePair> params1 = new ArrayList<NameValuePair>();
							params1.add(new BasicNameValuePair("companyuser",CompanyName));
							params1.add(new BasicNameValuePair("orderwhere","HULL"));
							String Visitname1 = "GetCompanyCountNum";
							String HC = HttpUtil.HttpSend(url, Visitname1, params1);
							try {
								json = new JSONObject(HC);
								HullCount = json.getString("count(*)");
								tv_HullNum_ed.setText(HullCount);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
				psd.show();

			}if(v.getId() == R.id.btn_book_room){
				PersonalSubscribeDialog psd = new PersonalSubscribeDialog(getActivity(),new PriorityListener() {
					
					@Override
					public void refreshPriorityUI(String string) {
						if(string.equals("SRUE")){
							String OrderWhere = "ROOM";
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("personalname",PersonalName));
							params.add(new BasicNameValuePair("personalusername",PersonalUsername));
							params.add(new BasicNameValuePair("companyname",CompanyName));
							params.add(new BasicNameValuePair("ordertime",OrderTime));
							params.add(new BasicNameValuePair("orderid",(Integer.parseInt(RoomCount)+1)+""));
							params.add(new BasicNameValuePair("orderwhere",OrderWhere));
							String Visitname = "PersonalSubscribe";
							String subscbeHull = HttpUtil.HttpSend(url, Visitname, params);
							if(subscbeHull.equals("Sucess")){
								Toast.makeText(getActivity(), "亲，恭喜你，预约成功了", Toast.LENGTH_LONG).show();
							}else if(subscbeHull.equals("Existe")){
								Toast.makeText(getActivity(), "亲，你已经在"+CompanyName+"房间预约了喔", Toast.LENGTH_LONG).show();
							}
							
							List<NameValuePair> params1 = new ArrayList<NameValuePair>();
							params1.add(new BasicNameValuePair("companyuser",CompanyName));
							params1.add(new BasicNameValuePair("orderwhere","ROOM"));
							String Visitname1 = "GetCompanyCountNum";
							String RC = HttpUtil.HttpSend(url, Visitname1, params1);
							try {
								json = new JSONObject(RC);
								RoomCount = json.getString("count(*)");
								tv_RoomNum_ed.setText(RoomCount);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
				psd.show();
			}
		}
		
	}
}
