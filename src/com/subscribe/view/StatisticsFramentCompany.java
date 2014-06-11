package com.subscribe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.widget.TextView;
import com.example.subscribe.R;
import com.subscribe.bean.CompanyUser;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;

public class StatisticsFramentCompany extends Fragment{
	
	private TextView tv_roomCount,tv_hullCount;
	private ListView StaticticsList;
	private String CompanyName,HullCount,RoomCount;
	public static String PerName,PerUsername,ComName,OrderTime,OrderID,OrederWhere,OW,CompanyUSER;
	CompanyUser cuser = LoginActivity.user;
	List<Map<String, String>>list;
	SharedPreferences sp;
	JSONObject json;
	JSONArray array;
	Url url;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View statisticsFramentCompany = inflater.inflate(R.layout.frament_company_statistics, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		sp = getActivity().getSharedPreferences("loginTest",Context.MODE_PRIVATE);
		if(sp.getBoolean("CB_type", false)){
			CompanyName = sp.getString("NAME", "");
			CompanyUSER = sp.getString("Username", "");
		}else{
			CompanyName = cuser.getCompanyName();
			CompanyUSER = cuser.getUserName();
		}
		GetNum();
		tv_roomCount = (TextView)statisticsFramentCompany.findViewById(R.id.tv_roomCount);
		tv_hullCount = (TextView)statisticsFramentCompany.findViewById(R.id.tv_hullCount);
		StaticticsList = (ListView)statisticsFramentCompany.findViewById(R.id.lv_statistics);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), getList(), R.layout.companystatistics_liststyle, new String[]{"personalName","Where"}, new int[]{R.id.tv_username,R.id.tv_where});
		StaticticsList.setAdapter(adapter);
		StaticticsList.setOnItemClickListener(new ListviewListener());
		
		tv_roomCount.setText(RoomCount);
		tv_hullCount.setText(HullCount);

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

        return statisticsFramentCompany;
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
	
	public List<Map<String, String>> getList(){
		list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("companyname",CompanyName));
		String Visitname = "GetCompanyFromOrderList";
		String resultCompanyMess = HttpUtil.HttpSend(url, Visitname, params);
		System.out.println(resultCompanyMess);
		try {
			array = new JSONArray(resultCompanyMess);
			
			int i = 0;
			for(i=0;i<array.length();i++){
System.out.println("CompanyName"+array.getJSONObject(i).getString("companyName"));
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
	
	private class ListviewListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			PerName = list.get(arg2).get("personalName");
			PerUsername = list.get(arg2).get("personalUsername");
			ComName = list.get(arg2).get("companyName");
			OrderTime = list.get(arg2).get("orderTime");
			OrderID = list.get(arg2).get("orderId");
			OrederWhere = list.get(arg2).get("Where");
			OW = list.get(arg2).get("orderWhere");
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction tran = fragmentManager.beginTransaction();
			tran.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
			StatisticsFramentCompanyDo sfcd = new StatisticsFramentCompanyDo();
			tran.replace(R.id.content_frame,sfcd);
			tran.commit();
		}
		
	}
}
