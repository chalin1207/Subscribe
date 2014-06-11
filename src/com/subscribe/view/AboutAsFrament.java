package com.subscribe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.subscribe.R;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AboutAsFrament extends Fragment{
	
	private ListView lv_about;
	private List<Map<String, String>> data;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View aboutAsFrament = inflater.inflate(R.layout.frament_about_as, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	
		data = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String, String>();
		map.put("mess","软件名称 ： Subscribe");
		Map<String,String> map1 = new HashMap<String, String>();
		map1.put("mess","软件版本：Subscribe 5.2");
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("mess","创作团队：超人团队");
		Map<String,String> map3 = new HashMap<String, String>();
		map3.put("mess","检测新版本");
		Map<String,String> map4 = new HashMap<String, String>();
		map4.put("mess","系统通知");
		data.add(map);
		data.add(map1);
		data.add(map2);
		data.add(map3);
		data.add(map4);
		
		lv_about = (ListView)aboutAsFrament.findViewById(R.id.lv_about);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.lv_about_stype, new String []{"mess"}, new int[]{R.id.tv_about_mess});
		lv_about.setAdapter(adapter);
		
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

        return aboutAsFrament;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
