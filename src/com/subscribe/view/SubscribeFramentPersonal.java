package com.subscribe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import com.example.subscribe.R;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SubscribeFramentPersonal extends Fragment{
	
	private ListView personalmessList;
	JSONArray array;
	Url url ;
	Map<String, String> map;
	List<Map<String, String>> list ;
	public static String companyName,companyUsername,hullNum,roomNum;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View subscribeFramentPersonal = inflater.inflate(R.layout.frament_personalsubscribe, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		personalmessList = (ListView)subscribeFramentPersonal.findViewById(R.id.lv_personalmess);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), getList(), R.layout.personalsubscribe_liststyle, new String[]{"CompanyName","zan","pinglun"}, new int[]{R.id.tv_comName,R.id.tv_zan,R.id.tv_pinglun});
		personalmessList.setAdapter(adapter);
		personalmessList.setOnItemClickListener(new ListviewOnItemListener());
		
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
		
		return subscribeFramentPersonal;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public List<Map<String, String>> getList(){
		list = new ArrayList<Map<String, String>>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。  
		t.setToNow();
		int hour = t.hour; // 0-23  
		int minute = t.minute;  
		int second = t.second; 
		String Timestring = hour+":"+minute+":"+second;
		
System.out.println("TimestringTimestringTimestring:"+Timestring);
        params.add(new BasicNameValuePair("time",Timestring));
		String Visitname = "GetCompanyMessList";
		String resultCompanyMess = HttpUtil.HttpSend(url, Visitname, params);
		System.out.println(resultCompanyMess);
		try {
			array = new JSONArray(resultCompanyMess);
			
			int i = 0;
			for(i=0;i<array.length();i++){
				System.out.println("CompanyName"+array.getJSONObject(i).getString("CompanyName"));
				map = new HashMap<String, String>();
				map.put("CompanyName", array.getJSONObject(i).getString("CompanyName"));
				map.put("Companyusername", array.getJSONObject(i).getString("Companyusername"));
				map.put("HullNum",array.getJSONObject(i).getString("HullNum"));
				map.put("RoomNum", array.getJSONObject(i).getString("RoomNum"));
				map.put("StartTime", array.getJSONObject(i).getString("StartTime"));
				map.put("EndTime", array.getJSONObject(i).getString("EndTime"));
				map.put("zan", (int)(Math.random()*100)+"");
				map.put("pinglun", (int)(Math.random()*100)+"");
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	class ListviewOnItemListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			companyName = list.get(arg2).get("CompanyName");
			companyUsername = list.get(arg2).get("Companyusername");
			hullNum = list.get(arg2).get("HullNum"); 
			roomNum = list.get(arg2).get("RoomNum");
System.out.println("hullNum:"+hullNum+"    roomNum:"+roomNum);
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction tran = fragmentManager.beginTransaction();
			tran.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
			SubscribeFramentPersonalDo sfpd = new SubscribeFramentPersonalDo();
			tran.replace(R.id.content_frame,sfpd);
			tran.commit();
		}
		
	}
}
