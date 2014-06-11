package com.subscribe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.subscribe.R;
import com.subscribe.bean.PersonalUser;
import com.subscribe.bean.Url;
import com.subscribe.bean.CompanyUser;
import com.subscribe.util.HttpUtil;

public class LoginActivity extends Activity {
	
	//登录，注册按钮
	private Button btn_Login, btn_Registr;
	//用户，密码，登录类型的输入框
	private EditText et_Username, et_Password,et_Type;
	//下拉列表的按钮
	private CheckBox cb_remember;
	//登录类型
	public static String LOGINTYPE = "";
    //定义配置文件
	public static SharedPreferences sp;   
	private SharedPreferences.Editor editor; 
	//定义自定义下拉列表
	private ListView TypeList=null;
	//下拉列表启动按钮
	ImageButton  Imgbtn_select = null;
	
	ArrayList<HashMap<String,String>> list;
	
	private static boolean isVisible=false;         //ListView是否可见
    private static boolean isIndicatorUp=false;     //指示器的方向
    //bean对象里的
    Url url;
    static CompanyUser user;
    static PersonalUser puser;

	private void init() {

		btn_Login = (Button) findViewById(R.id.btn_Login);
		btn_Registr = (Button) findViewById(R.id.bt_Registr);
		
		et_Type = (EditText)findViewById(R.id.et_Type);
		et_Username = (EditText) findViewById(R.id.et_Username);
		et_Password = (EditText) findViewById(R.id.et_Password);
		//自动登录多选框
		cb_remember = (CheckBox) findViewById(R.id.cb_remember);
		
		//自定义下拉按钮
		Imgbtn_select = (ImageButton)findViewById(R.id.ImgBtn_select);
		//ListView 的 list
		
		
		TypeList = (ListView)findViewById(R.id.Type_List);
		//TODO 改样式
//		btn_Login.setBackgroundResource(resid)
		btn_Login.setOnClickListener(new ButtonListener());
		btn_Registr.setOnClickListener(new ButtonListener());
		//声明SharedPreferences文件
		sp = this.getSharedPreferences("loginTest",Context.MODE_PRIVATE); 
		editor = sp.edit();
		
		 list=new ArrayList<HashMap<String,String>>();
	     HashMap<String,String> map1=new HashMap<String,String>();  
	     HashMap<String,String> map2=new HashMap<String,String>();  
	     map1.put("loginType", "个人用户");  
	     map2.put("loginType", "企业用户");  
	     list.add(map1);  
	     list.add(map2);
		
	     
	     
	     et_Type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 if(et_Type.getText().toString().equals("")==true){
					 TypeList.setVisibility(View.VISIBLE);
					 Imgbtn_select.setBackgroundResource(R.drawable.indicator_up);
               }
			}
		});
	     
		if (sp.getBoolean("CB_type", false)) {
			if (sp.getString("Type", "").equals("企业用户")) {
				et_Type.setText("企业用户");
			} else if (sp.getString("Type", "").equals("个人用户")) {
				et_Type.setText("个人用户");
			} 
			et_Username.setText(sp.getString("Username", ""));
			et_Password.setText(sp.getString("Password", ""));
			cb_remember.setChecked(sp.getBoolean("CB_type", false));
		}
		
		 SimpleAdapter  adapter=new SimpleAdapter (this, list, R.layout.listview_styles,new String[] {"loginType"}, new int[] {R.id.et_type});
	     TypeList.setAdapter(adapter);
	     
	     TypeList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				    et_Type.setText((String)list.get(arg2).get("loginType"));
				    
				    if(list.get(arg2).get("loginType").toString().equals("个人用户")){
				    	btn_Login.setBackgroundResource(R.drawable.btn_style);
				    	btn_Registr.setBackgroundResource(R.drawable.btn_style);
				    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
				    }else if(list.get(arg2).get("loginType").toString().equals("企业用户")){
				    	btn_Login.setBackgroundResource(R.drawable.btn_style1);
				    	btn_Registr.setBackgroundResource(R.drawable.btn_style1);
				    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_down1);
				    }
	                //相应完点击后List就消失，指示箭头反向！
	                TypeList.setVisibility(View.GONE);
//	                Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
	                
	                System.out.println("---------Selected!!"+list.get(arg2).get("loginType"));
			}
	    	 
	     });
	     
	     Imgbtn_select.setOnClickListener(new OnClickListener(){

	            @Override
	            public void onClick(View v) {
	            	if(isIndicatorUp){
	                    isIndicatorUp=false;
	                    isVisible=false;
	                    if(et_Type.getText().toString().equals("企业用户")){
	                    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_down1);
	                    }else{
	                        Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
	                    }
	                    TypeList.setVisibility(View.GONE);   //让ListView列表消失
	                    
	                }
	                else{
	                    isIndicatorUp=true;
	                    isVisible=true;
	                    if(et_Type.getText().toString().equals("企业用户")){
	                    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_up1);
	                    }else{
	                        Imgbtn_select.setBackgroundResource(R.drawable.indicator_up);
	                    }
	                    TypeList.setVisibility(View.VISIBLE);
	                }
	            }
	            
	        });
	}



	@Override
    public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN && isVisible){
            int[] location=new int[2];
            //调用getLocationInWindow方法获得某一控件在窗口中左上角的横纵坐标
            TypeList.getLocationInWindow(location);
            //获得在屏幕上点击的点的坐标
            int x=(int)event.getX();  
            int y=(int)event.getY();
            if(x<location[0]|| x>location[0]+TypeList.getWidth() ||
                    y<location[1]||y>location[1]+TypeList.getHeight()){
                isIndicatorUp=false;
                isVisible=false;
                Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
                TypeList.setVisibility(View.GONE);   //让ListView列表消失，并且让游标向下指！
                
            }
            
            
        }
        
        
        return super.onTouchEvent(event);
    }
	
	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_Login) {
				LOGINTYPE = et_Type.getText().toString().trim();
System.out.println("LOGINTYPE+++++++++++"+LOGINTYPE);
				if (LOGINTYPE.equals("")) {

					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					builder.setMessage("请选择登录类别~")
				    .setCancelable(false)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(
							DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
					builder.setTitle("温馨提醒");
					builder.create().show();
				} else if (LOGINTYPE.equals("企业用户")) {
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
					params.add(new BasicNameValuePair("password",et_Password.getText().toString().trim()));
					
					String Visitname = "LoginCompanyUser";
					String resultLoginCompany = HttpUtil.HttpSend(url, Visitname, params);
System.out.println("企业用户登录-----resultLoginCompany"+resultLoginCompany);
					if (resultLoginCompany.equals("OK")) {
						//获得企业名称
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
						String Visitname1 = "GetCompanyName";
						String getCompanyname = HttpUtil.HttpSend(url, Visitname1, params1);
						
						user = new CompanyUser(et_Username.getText().toString().trim(),getCompanyname);
						
						//TODO 记录用户信息，登录类别
						editor.putString("Type",LOGINTYPE);  
		                editor.putString("Username", et_Username.getText().toString().trim()); 
		                editor.putString("Password", et_Password.getText().toString().trim());
		                editor.putString("NAME", getCompanyname);
		                editor.putBoolean("CB_type", cb_remember.isChecked());
		                editor.commit();
		                
System.out.println("-------------------------"+sp.getString("Username", null));		                
		                //通过cb_remember.isChecked()来判断是否自动登录
							Intent intent = new Intent();
							intent.setClass(LoginActivity.this,
									NavCompanyActivity.class);
							LoginActivity.this.startActivity(intent);
							LoginActivity.this.finish();
					} else if(resultLoginCompany.equals("PassErr")){
						//TODO 登录失败
						Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_LONG).show();
					}else if(resultLoginCompany.equals("NOExiste")){
						Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
					}
				} else if (LOGINTYPE.equals("个人用户")) {
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
					params.add(new BasicNameValuePair("password",et_Password.getText().toString().trim()));
					
					String Visitname = "LoginPersonalUser";
					String resultLoginPersonal = HttpUtil.HttpSend(url, Visitname, params);
System.out.println("个人用户登录-----resultLoginPersonal"+resultLoginPersonal);	
					if (resultLoginPersonal.equals("OK")) {
						//获得用户身份姓名
						List<NameValuePair> params2 = new ArrayList<NameValuePair>();
						params2.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
						String Visitname2 = "GetPersonalName";
						String getPersonalname = HttpUtil.HttpSend(url, Visitname2, params2);

						puser = new PersonalUser(et_Username.getText().toString().trim(),getPersonalname);
						//TODO 记录用户信息，登录类别
						editor.putString("Type",LOGINTYPE);  
		                editor.putString("Username", et_Username.getText().toString().trim()); 
		                editor.putString("Password", et_Password.getText().toString().trim()); 
		                editor.putString("NAME", getPersonalname);//个人身份姓名
		                editor.putBoolean("CB_type", cb_remember.isChecked());
		                editor.commit();
		                
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this,
								NavPersonlActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
							//TODO 没有自动登录
					}else if(resultLoginPersonal.equals("PassErr")){
						//TODO 登录失败
						Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_LONG).show();
					}else if(resultLoginPersonal.equals("NOExiste")){
						Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
					}
				}

			}
			if (v.getId() == R.id.bt_Registr) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegistrActivity.class);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
				LoginActivity.this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
			}
		}

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.setTitle("用户登录");
		init();
		
		if(et_Type.getText().toString().equals("企业用户")){
	    	 btn_Login.setBackgroundResource(R.drawable.btn_style1);
		     btn_Registr.setBackgroundResource(R.drawable.btn_style1);
		     Imgbtn_select.setBackgroundResource(R.drawable.indicator_down1);
	     }
		
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
	        
	}
	

}
