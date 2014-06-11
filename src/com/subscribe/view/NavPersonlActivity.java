package com.subscribe.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.example.subscribe.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NavPersonlActivity extends Activity {

	DrawerLayout drawerLayout;
	ListView lv;
	ActionBarDrawerToggle barDrawerToggle;
	ArrayList<HashMap<String,String>> list;
	private static boolean isQuit = false;
	Timer timer = new Timer(); 

	public void init() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		lv = (ListView) this.findViewById(R.id.left_drawer);

//		ArrayAdapter adapter = new ArrayAdapter(this,
//				android.R.layout.simple_list_item_1, str);

//		lv.setAdapter(adapter);

		barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_launcher, R.string.hello_world,
				R.string.hello_world);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		 list=new ArrayList<HashMap<String,String>>();
	     HashMap<String,String> map1=new HashMap<String,String>();  
	     HashMap<String,String> map2=new HashMap<String,String>();  
	     HashMap<String,String> map3=new HashMap<String,String>();  
	     HashMap<String,String> map4=new HashMap<String,String>();  
	     HashMap<String,String> map5=new HashMap<String,String>();  
	     map1.put("MenuName", "预约");  
	     map2.put("MenuName", "查号");  
	     map3.put("MenuName", "关于");  
	     map4.put("MenuName", "注销");  
	     map5.put("MenuName", "退出");  
	     list.add(map1);  
	     list.add(map2);
	     list.add(map3);
	     list.add(map4);
	     list.add(map5);
	     
	     SimpleAdapter  adapter=new SimpleAdapter (this, list, R.layout.personal_menu_style,new String[] {"MenuName"}, new int[] {R.id.tv_menus});
		 lv.setAdapter(adapter);

		listviewListener listener = new listviewListener();
		lv.setOnItemClickListener(listener);
	}

	public class listviewListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction tran = fragmentManager.beginTransaction();

			tran.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);

			switch (arg2) {

			case 0:
				// Fragment con1 = new Content();
				SubscribeFramentPersonal sfp = new SubscribeFramentPersonal();
				tran.replace(R.id.content_frame,sfp);
				NavPersonlActivity.this.setTitle("预约");
				break;
			case 1:
				CheckSubscribeFramentPersonal csfp = new CheckSubscribeFramentPersonal();
				tran.replace(R.id.content_frame,csfp);
				NavPersonlActivity.this.setTitle("查号");
				break;
			case 2:
				AboutAsFrament aaf = new AboutAsFrament();
				tran.replace(R.id.content_frame,aaf);
				NavPersonlActivity.this.setTitle("关于");
				break;
			case 3:
				Intent intent = new Intent();
				intent.setClass(NavPersonlActivity.this, LoginActivity.class);
				NavPersonlActivity.this.startActivity(intent);
				NavPersonlActivity.this.finish();
				break;
			case 4:
				System.exit(0);
				break;
			}
			tran.commit();
			drawerLayout.closeDrawer(lv);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		getMenuInflater().inflate(R.menu.open, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			if (barDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nav_drawer);
		this.setTitle("个人用户服务");
		init();
		getOverflowMenu();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction tran = fragmentManager.beginTransaction();		
		FirstFramentPersonal ffp = new FirstFramentPersonal();
		tran.replace(R.id.content_frame,ffp);
		tran.commit();
		
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK) {  
	            if (isQuit == false) {  
	                isQuit = true;  
	                Toast.makeText(NavPersonlActivity.this, "亲，再按一次退出应用", Toast.LENGTH_SHORT).show();  
	                TimerTask task = null;  
	                task = new TimerTask() {  
	                    @Override  
	                    public void run() {  
	                        isQuit = false;  
	                    }  
	                };  
	                timer.schedule(task, 2000);  
	            } else {  
	                finish();  
	                System.exit(0);  
	            }  
	        }  
	        return true;  
	} 
}
