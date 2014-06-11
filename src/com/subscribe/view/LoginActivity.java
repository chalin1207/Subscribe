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
	
	//��¼��ע�ᰴť
	private Button btn_Login, btn_Registr;
	//�û������룬��¼���͵������
	private EditText et_Username, et_Password,et_Type;
	//�����б�İ�ť
	private CheckBox cb_remember;
	//��¼����
	public static String LOGINTYPE = "";
    //���������ļ�
	public static SharedPreferences sp;   
	private SharedPreferences.Editor editor; 
	//�����Զ��������б�
	private ListView TypeList=null;
	//�����б�������ť
	ImageButton  Imgbtn_select = null;
	
	ArrayList<HashMap<String,String>> list;
	
	private static boolean isVisible=false;         //ListView�Ƿ�ɼ�
    private static boolean isIndicatorUp=false;     //ָʾ���ķ���
    //bean�������
    Url url;
    static CompanyUser user;
    static PersonalUser puser;

	private void init() {

		btn_Login = (Button) findViewById(R.id.btn_Login);
		btn_Registr = (Button) findViewById(R.id.bt_Registr);
		
		et_Type = (EditText)findViewById(R.id.et_Type);
		et_Username = (EditText) findViewById(R.id.et_Username);
		et_Password = (EditText) findViewById(R.id.et_Password);
		//�Զ���¼��ѡ��
		cb_remember = (CheckBox) findViewById(R.id.cb_remember);
		
		//�Զ���������ť
		Imgbtn_select = (ImageButton)findViewById(R.id.ImgBtn_select);
		//ListView �� list
		
		
		TypeList = (ListView)findViewById(R.id.Type_List);
		//TODO ����ʽ
//		btn_Login.setBackgroundResource(resid)
		btn_Login.setOnClickListener(new ButtonListener());
		btn_Registr.setOnClickListener(new ButtonListener());
		//����SharedPreferences�ļ�
		sp = this.getSharedPreferences("loginTest",Context.MODE_PRIVATE); 
		editor = sp.edit();
		
		 list=new ArrayList<HashMap<String,String>>();
	     HashMap<String,String> map1=new HashMap<String,String>();  
	     HashMap<String,String> map2=new HashMap<String,String>();  
	     map1.put("loginType", "�����û�");  
	     map2.put("loginType", "��ҵ�û�");  
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
			if (sp.getString("Type", "").equals("��ҵ�û�")) {
				et_Type.setText("��ҵ�û�");
			} else if (sp.getString("Type", "").equals("�����û�")) {
				et_Type.setText("�����û�");
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
				    
				    if(list.get(arg2).get("loginType").toString().equals("�����û�")){
				    	btn_Login.setBackgroundResource(R.drawable.btn_style);
				    	btn_Registr.setBackgroundResource(R.drawable.btn_style);
				    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
				    }else if(list.get(arg2).get("loginType").toString().equals("��ҵ�û�")){
				    	btn_Login.setBackgroundResource(R.drawable.btn_style1);
				    	btn_Registr.setBackgroundResource(R.drawable.btn_style1);
				    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_down1);
				    }
	                //��Ӧ������List����ʧ��ָʾ��ͷ����
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
	                    if(et_Type.getText().toString().equals("��ҵ�û�")){
	                    	Imgbtn_select.setBackgroundResource(R.drawable.indicator_down1);
	                    }else{
	                        Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
	                    }
	                    TypeList.setVisibility(View.GONE);   //��ListView�б���ʧ
	                    
	                }
	                else{
	                    isIndicatorUp=true;
	                    isVisible=true;
	                    if(et_Type.getText().toString().equals("��ҵ�û�")){
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
            //����getLocationInWindow�������ĳһ�ؼ��ڴ��������Ͻǵĺ�������
            TypeList.getLocationInWindow(location);
            //�������Ļ�ϵ���ĵ������
            int x=(int)event.getX();  
            int y=(int)event.getY();
            if(x<location[0]|| x>location[0]+TypeList.getWidth() ||
                    y<location[1]||y>location[1]+TypeList.getHeight()){
                isIndicatorUp=false;
                isVisible=false;
                Imgbtn_select.setBackgroundResource(R.drawable.indicator_down);
                TypeList.setVisibility(View.GONE);   //��ListView�б���ʧ���������α�����ָ��
                
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
					builder.setMessage("��ѡ���¼���~")
				    .setCancelable(false)
					.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						public void onClick(
							DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
					builder.setTitle("��ܰ����");
					builder.create().show();
				} else if (LOGINTYPE.equals("��ҵ�û�")) {
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
					params.add(new BasicNameValuePair("password",et_Password.getText().toString().trim()));
					
					String Visitname = "LoginCompanyUser";
					String resultLoginCompany = HttpUtil.HttpSend(url, Visitname, params);
System.out.println("��ҵ�û���¼-----resultLoginCompany"+resultLoginCompany);
					if (resultLoginCompany.equals("OK")) {
						//�����ҵ����
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
						String Visitname1 = "GetCompanyName";
						String getCompanyname = HttpUtil.HttpSend(url, Visitname1, params1);
						
						user = new CompanyUser(et_Username.getText().toString().trim(),getCompanyname);
						
						//TODO ��¼�û���Ϣ����¼���
						editor.putString("Type",LOGINTYPE);  
		                editor.putString("Username", et_Username.getText().toString().trim()); 
		                editor.putString("Password", et_Password.getText().toString().trim());
		                editor.putString("NAME", getCompanyname);
		                editor.putBoolean("CB_type", cb_remember.isChecked());
		                editor.commit();
		                
System.out.println("-------------------------"+sp.getString("Username", null));		                
		                //ͨ��cb_remember.isChecked()���ж��Ƿ��Զ���¼
							Intent intent = new Intent();
							intent.setClass(LoginActivity.this,
									NavCompanyActivity.class);
							LoginActivity.this.startActivity(intent);
							LoginActivity.this.finish();
					} else if(resultLoginCompany.equals("PassErr")){
						//TODO ��¼ʧ��
						Toast.makeText(LoginActivity.this, "�����������", Toast.LENGTH_LONG).show();
					}else if(resultLoginCompany.equals("NOExiste")){
						Toast.makeText(LoginActivity.this, "�û�������", Toast.LENGTH_LONG).show();
					}
				} else if (LOGINTYPE.equals("�����û�")) {
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
					params.add(new BasicNameValuePair("password",et_Password.getText().toString().trim()));
					
					String Visitname = "LoginPersonalUser";
					String resultLoginPersonal = HttpUtil.HttpSend(url, Visitname, params);
System.out.println("�����û���¼-----resultLoginPersonal"+resultLoginPersonal);	
					if (resultLoginPersonal.equals("OK")) {
						//����û��������
						List<NameValuePair> params2 = new ArrayList<NameValuePair>();
						params2.add(new BasicNameValuePair("username", et_Username.getText().toString().trim()));
						String Visitname2 = "GetPersonalName";
						String getPersonalname = HttpUtil.HttpSend(url, Visitname2, params2);

						puser = new PersonalUser(et_Username.getText().toString().trim(),getPersonalname);
						//TODO ��¼�û���Ϣ����¼���
						editor.putString("Type",LOGINTYPE);  
		                editor.putString("Username", et_Username.getText().toString().trim()); 
		                editor.putString("Password", et_Password.getText().toString().trim()); 
		                editor.putString("NAME", getPersonalname);//�����������
		                editor.putBoolean("CB_type", cb_remember.isChecked());
		                editor.commit();
		                
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this,
								NavPersonlActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
							//TODO û���Զ���¼
					}else if(resultLoginPersonal.equals("PassErr")){
						//TODO ��¼ʧ��
						Toast.makeText(LoginActivity.this, "�����������", Toast.LENGTH_LONG).show();
					}else if(resultLoginPersonal.equals("NOExiste")){
						Toast.makeText(LoginActivity.this, "�û�������", Toast.LENGTH_LONG).show();
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
		this.setTitle("�û���¼");
		init();
		
		if(et_Type.getText().toString().equals("��ҵ�û�")){
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
