package com.subscribe.view;

import com.example.subscribe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class RegistrActivity extends Activity{
	private TabHost tabHost;
	private static int layoutNum = 0;
	private TabWidget tabWidget;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setTitle("注册用户");
		setContentView(R.layout.activity_registr);
		setFragment("个人账户注册", R.id.fragment1);
		setFragment("企业账户注册", R.id.fragment2);
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.equals("个人账户注册")){
					 tabWidget = tabHost.getTabWidget();
					 tabWidget.setBackgroundColor(getResources().getColor(R.color.tabw_color));
				}else if(tabId.equals("企业账户注册")){
					tabWidget = tabHost.getTabWidget();
					 tabWidget.setBackgroundColor(getResources().getColor(R.color.tabw_color1));
				}
			}
		});
		
		tabHost.setCurrentTab(layoutNum);
	}
	
	private void setFragment(String label,int id){
    	tabHost = (TabHost)findViewById(R.id.tabmenu);
    	tabHost.setup();
    	TabHost.TabSpec spec = tabHost.newTabSpec(label);
		 
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_style, null, false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(label);
		
		spec.setIndicator(tabIndicator);
		//spec.setContent(intent);
		spec.setContent(id);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
    }
}
