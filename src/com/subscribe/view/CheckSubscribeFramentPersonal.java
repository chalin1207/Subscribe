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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.subscribe.R;
import com.subscribe.bean.PersonalUser;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;

public class CheckSubscribeFramentPersonal extends Fragment{
	
	private ListView CheckSubscribeList;
	List<Map<String, String>>list;
	public String personalUsername;
	SharedPreferences sp;
	JSONArray array;
	PersonalUser puser = LoginActivity.puser;
	public static String CNAME,OWHERE,OID,OW;
	
	Url url;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View checkSubscribeFramentPersonal = inflater.inflate(R.layout.frament_personalsubscribecheck, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	
		
		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		if(sp.getBoolean("CB_type", false)){
			personalUsername = sp.getString("Username", "");
		}else{
			personalUsername = puser.getUserName();
		}
		
		CheckSubscribeList = (ListView)checkSubscribeFramentPersonal.findViewById(R.id.lv_personalsubscribeed);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), getList(), R.layout.personalsubscribecheck_liststyle, new String[]{"companyName","Where"}, new int[]{R.id.tv_check_companyname,R.id.tv_check_type});
		CheckSubscribeList.setAdapter(adapter);
		CheckSubscribeList.setOnItemClickListener(new ListViewListener());
		
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

        return checkSubscribeFramentPersonal;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public List<Map<String, String>> getList(){
		list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("personaluser",personalUsername));
		String Visitname = "GetOrderCheckList";
		String resultGetOrderCheckList = HttpUtil.HttpSend(url, Visitname, params);
System.out.println(resultGetOrderCheckList);
		
		try {
			array = new JSONArray(resultGetOrderCheckList);

			int i = 0;
			for(i=0;i<array.length();i++){
				map = new HashMap<String, String>();
				map.put("personalName", array.getJSONObject(i).getString("personalName"));
				map.put("personalUsername", array.getJSONObject(i).getString("personalUsername"));
				map.put("companyName",array.getJSONObject(i).getString("companyName"));
				map.put("orderTime", array.getJSONObject(i).getString("orderTime"));
				map.put("orderId", array.getJSONObject(i).getString("orderId"));
				map.put("orderWhere", array.getJSONObject(i).getString("orderWhere"));
				if(array.getJSONObject(i).getString("orderWhere").equals("ROOM")){
					map.put("Where", "·¿¼ä");
				}else{
					map.put("Where", "´óÌü");
				}
				
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private class ListViewListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			CNAME = list.get(arg2).get("companyName");
			OWHERE = list.get(arg2).get("Where");
			OID = list.get(arg2).get("orderId");
			OW = list.get(arg2).get("orderWhere");
			
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction tran = fragmentManager.beginTransaction();
			tran.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
			CheckSubscribeFramentPeraonalDo csfpd = new CheckSubscribeFramentPeraonalDo();
			tran.replace(R.id.content_frame,csfpd);
			tran.commit();
		}
		
	}
}
