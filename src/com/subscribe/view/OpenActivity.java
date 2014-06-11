package com.subscribe.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.subscribe.R;

public class OpenActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒 
	boolean auto = false;
	String loginType = "";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_open);
		
		 SharedPreferences sp = this.getSharedPreferences("loginTest",Context.MODE_PRIVATE);
System.out.println("/*-/*-/*-/*-/-*/*/*-/*-"+ sp.getString("Username", null));
    	 
         auto = sp.getBoolean("CB_type", false);
         loginType = sp.getString("Type", "");
System.out.println("***********************"+auto+"12313132132"+loginType);
		  new Handler().postDelayed(new Runnable(){ 
			  
		         @Override 
		         public void run() { 
		        	 if(auto){
		        		 if(loginType.equals("企业用户")){
		        			 Intent intent = new Intent(OpenActivity.this,NavCompanyActivity.class); 
				             OpenActivity.this.startActivity(intent); 
				             OpenActivity.this.finish(); 
				             
		        		 }if(loginType.equals("个人用户")){
		        			 Intent intent = new Intent(OpenActivity.this,NavPersonlActivity.class); 
				             OpenActivity.this.startActivity(intent); 
				             OpenActivity.this.finish(); 
				             
		        		 }
		        	 }else{
		        		 Intent openIntent = new Intent(OpenActivity.this,LoginActivity.class); 
			             OpenActivity.this.startActivity(openIntent); 
			             OpenActivity.this.finish(); 
		        	 }
		         } 
		            
		        }, SPLASH_DISPLAY_LENGHT); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open, menu);
		return true;
	}

}
