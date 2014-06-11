package com.subscribe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.subscribe.R;
import com.subscribe.bean.CompanyUser;
import com.subscribe.bean.Url;
import com.subscribe.broadcast.MySocketIO;
import com.subscribe.util.HttpUtil;

public class CallNumberFramentCompany extends Fragment{
	
	private TextView tv_RoomNumber,tv_HullNumber;
	private Button btn_RoomCall,btn_RoomNext,btn_HullCall,btn_HullNext;
	private String CompanyName;
	SharedPreferences sp;
	CompanyUser cuser = LoginActivity.user;
	List <Map<String, String>> HullOrderIdList;
	List <Map<String, String>> RoomOrderIdList;
	JSONArray arrayRoom,arrayHull;
	int numRoom = -1;
	int numHull = -1;
	Url url;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View callNumberFramentCompany = inflater.inflate(R.layout.frament_company_callnum, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	
		
		
		
		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		if(sp.getBoolean("CB_type", false)){
			CompanyName = sp.getString("NAME", "");
		}else{
			CompanyName = cuser.getCompanyName();
		}
		
		tv_RoomNumber = (TextView)callNumberFramentCompany.findViewById(R.id.tv_call_roomnum);
		tv_HullNumber = (TextView)callNumberFramentCompany.findViewById(R.id.tv_call_hullnum);
		btn_RoomCall = (Button)callNumberFramentCompany.findViewById(R.id.call_btn_room);
		btn_RoomNext = (Button)callNumberFramentCompany.findViewById(R.id.call_btn_roomnext);
		btn_HullCall = (Button)callNumberFramentCompany.findViewById(R.id.call_btn_hull);
		btn_HullNext = (Button)callNumberFramentCompany.findViewById(R.id.call_btn_hullnext);
		
		GetLists();
		
		System.out.println("123123123"+(arrayRoom.length() == 0));
		if(arrayRoom.length() == 0){
			tv_RoomNumber.setTextSize(15);
			tv_RoomNumber.setText("无人预约，停止使用");
			btn_RoomCall.setClickable(false);
			btn_RoomNext.setClickable(false);
		}else{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("companyname",CompanyName));
	        params.add(new BasicNameValuePair("orderwhere","ROOM"));
			String Visitname = "GetCallNum";
			String resultNum = HttpUtil.HttpSend(url, Visitname, params);
			if(resultNum.equals("NoStart")){
				
			}else{
			    numRoom = Integer.parseInt(resultNum);
			    if(numRoom>=0){
			         tv_RoomNumber.setText(RoomOrderIdList.get(numRoom).get("RoomID"));
			    }
			}
		}
		
		if(arrayHull.length() == 0){
			tv_HullNumber.setTextSize(15);
			tv_HullNumber.setText("无人预约，停止使用");
			btn_HullCall.setClickable(false);
			btn_HullNext.setClickable(false);
		}else{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("companyname",CompanyName));
	        params.add(new BasicNameValuePair("orderwhere","HULL"));
			String Visitname = "GetCallNum";
			String resultNum = HttpUtil.HttpSend(url, Visitname, params);
            if(resultNum.equals("NoStart")){
				
			}else{
			    numHull = Integer.parseInt(resultNum);
			    if(numHull>=0){
		           tv_HullNumber.setText(HullOrderIdList.get(numHull).get("HullID"));
			    }
			}
		}
		
		btn_RoomCall.setOnClickListener(new ButtonListener());
		btn_RoomNext.setOnClickListener(new ButtonListener());
		btn_HullCall.setOnClickListener(new ButtonListener());
		btn_HullNext.setOnClickListener(new ButtonListener());
		
		
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

        return callNumberFramentCompany;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private class ButtonListener implements OnClickListener{

		public void onClick(View arg0) {
			if(arg0.getId() == R.id.call_btn_room){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("nownumber",tv_RoomNumber.getText().toString()));
		        params.add(new BasicNameValuePair("companyname",CompanyName));
		        params.add(new BasicNameValuePair("orderwhere","ROOM"));
		        params.add(new BasicNameValuePair("nownum",numRoom+""));
				String Visitname = "SaveCallnumberMess";
				String resultSave= HttpUtil.HttpSend(url, Visitname, params);
				if(resultSave.equals("Sucess")){
					//TODO 广播
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			        params1.add(new BasicNameValuePair("CompanyName",CompanyName));
			        params1.add(new BasicNameValuePair("CurrentNo",tv_RoomNumber.getText().toString()));
			        params1.add(new BasicNameValuePair("OrderWhere","ROOM"));
					String Visitname1 = "testBroadcast";
					HttpUtil.HttpSend(url, Visitname1, params1);
				}
			}else if(arg0.getId() == R.id.call_btn_roomnext){
				numRoom++;
				if(numRoom == arrayRoom.length()){
					tv_RoomNumber.setTextSize(15);
					tv_RoomNumber.setText("叫号完成");
					btn_RoomCall.setClickable(false);
					btn_RoomNext.setClickable(false);
				}else{
				   tv_RoomNumber.setText(RoomOrderIdList.get(numRoom).get("RoomID"));
				}
			}else if(arg0.getId() == R.id.call_btn_hull){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("nownumber",tv_HullNumber.getText().toString()));
		        params.add(new BasicNameValuePair("companyname",CompanyName));
		        params.add(new BasicNameValuePair("orderwhere","HULL"));
		        params.add(new BasicNameValuePair("nownum",numHull+""));
				String Visitname = "SaveCallnumberMess";
				String resultSave= HttpUtil.HttpSend(url, Visitname, params);
				if(resultSave.equals("Sucess")){
					//TODO 广播
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			        params1.add(new BasicNameValuePair("CompanyName",CompanyName));
			        params1.add(new BasicNameValuePair("CurrentNo",tv_RoomNumber.getText().toString()));
			        params1.add(new BasicNameValuePair("OrderWhere","HULL"));
					String Visitname1 = "testBroadcast";
					HttpUtil.HttpSend(url, Visitname1, params1);
				}
				
			}else if(arg0.getId() == R.id.call_btn_hullnext){
				numHull++;
				if(numHull == arrayHull.length()){
					tv_HullNumber.setTextSize(15);
					tv_HullNumber.setText("叫号完成");
					btn_HullCall.setClickable(false);
					btn_HullNext.setClickable(false);
				}else{
					tv_HullNumber.setText(HullOrderIdList.get(numHull).get("HullID"));
				}
			}
		}
		
	}
	
	private void GetLists(){
		HullOrderIdList = new ArrayList<Map<String, String>>();
		RoomOrderIdList = new ArrayList<Map<String, String>>();
		
        HashMap<String, String> map = null;
        HashMap<String, String> map1 = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("companyname",CompanyName));
        params.add(new BasicNameValuePair("orderwhere","ROOM"));
		String Visitname = "GetOrderIdList";
		String resultRoomOrderList = HttpUtil.HttpSend(url, Visitname, params);
		try {
			arrayRoom = new JSONArray(resultRoomOrderList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(arrayRoom.length()==0){
			tv_RoomNumber.setTextSize(15);
			tv_RoomNumber.setText("无人预约，停止使用");
			btn_RoomCall.setClickable(false);
			btn_RoomNext.setClickable(false);
		}else{
			int i = 0;
			for(i=0;i<arrayRoom.length();i++){
               try {
	                map = new HashMap<String, String>();
	                map.put("RoomID", arrayRoom.getJSONObject(i).getString("orderId"));
	                
                  } catch (JSONException e) {
	                    e.printStackTrace();
                  }
               RoomOrderIdList.add(map);
			 }
		 }
		
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("companyname",CompanyName));
        params1.add(new BasicNameValuePair("orderwhere","HULL"));
		String Visitname1 = "GetOrderIdList";
		String resultHullOrderList = HttpUtil.HttpSend(url, Visitname1, params1);
		
		try {
			arrayHull = new JSONArray(resultHullOrderList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(arrayHull.length()==0){
			tv_HullNumber.setTextSize(15);
			tv_HullNumber.setText("无人预约，停止使用");
			btn_HullCall.setClickable(false);
			btn_HullNext.setClickable(false);
		}else{
			int i = 0;
			for(i=0;i<arrayHull.length();i++){
               try {
	                map1 = new HashMap<String, String>();
	                map1.put("HullID", arrayHull.getJSONObject(i).getString("orderId"));
	                
                  } catch (JSONException e) {
	                    e.printStackTrace();
                  }
               HullOrderIdList.add(map1);
			 }
		 }
		System.out.println("arrayRoom长度:"+arrayRoom.length()+"     arrayHull的长度:"+arrayHull.length());
	}
	
	

		
		
		
}


//点击叫号按钮就广播，按下一个按钮:先设置一个int为0的变量且少于array.length()，每按一次就加1，并用sp保存其起来。下次进来的时候继续调用当前号数
//当int变量等于array。length()时。按下一个后不能再继续增加按钮失去焦点。并提示叫号业务完成，

