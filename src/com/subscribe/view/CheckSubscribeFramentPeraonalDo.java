package com.subscribe.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.subscribe.R;
import com.subscribe.bean.Url;
import com.subscribe.broadcast.MySocketIO;
import com.subscribe.util.HttpUtil;

public class CheckSubscribeFramentPeraonalDo extends Fragment{
	
	private TextView tv_companyname,tv_where,tv_callnumber,tv_younum,tv_mess;
	public String CompanyName,OrderWhere,OrderID,OW;
	Url url;
	MyHandler handler=new MyHandler();
	MySocketIO socketIO;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View checkSubscribeFramentPersonal = inflater.inflate(R.layout.frament_personalsubscribecheck_imp, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	
		
		socketIO=new MySocketIO(handler);
		
		tv_companyname = (TextView)checkSubscribeFramentPersonal.findViewById(R.id.tv_check_companyname);
		tv_where = (TextView)checkSubscribeFramentPersonal.findViewById(R.id.tv_check_where);
		tv_callnumber = (TextView)checkSubscribeFramentPersonal.findViewById(R.id.tv_check_nownumber);
		tv_younum = (TextView)checkSubscribeFramentPersonal.findViewById(R.id.tv_check_number);
		tv_mess = (TextView)checkSubscribeFramentPersonal.findViewById(R.id.tv_check_mess);
		
		CompanyName = CheckSubscribeFramentPersonal.CNAME;
		OrderWhere = CheckSubscribeFramentPersonal.OWHERE;
		OrderID = CheckSubscribeFramentPersonal.OID;
		OW = CheckSubscribeFramentPersonal.OW;
		
		tv_companyname.setText(CompanyName);
		tv_where.setText(OrderWhere);
		tv_younum.setText(OrderID);
		
		GetNumber();
		
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
	
	private void GetNumber(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("companyname",CompanyName));
        params.add(new BasicNameValuePair("orderwhere",OW));
		String Visitname = "GetCallNumber";
		String resultNumber = HttpUtil.HttpSend(url, Visitname, params);
		if(resultNumber.equals("NoStart")){
			tv_mess.setText("亲,"+CompanyName+"还没有开始叫号喔");
		}else{
			tv_callnumber.setText(resultNumber);
			tv_mess.setText("亲,你可能还要等"+(Integer.parseInt(OrderID)-Integer.parseInt(resultNumber))*30+"分钟喔");
		}
	}
	
	public class MyHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
		
			switch(msg.what)
			{
			case 1:
			  {
				  break;
			  }
			case 2:
	    		{
	    		 
	    		 Bundle bundle=msg.getData();
	    		 if((CompanyName.equals(bundle.getString("company"))&&(OW.equals(bundle.getString("where"))))){
	    			 if(!bundle.getString("no").equals("叫号完成")){
	    			     tv_callnumber.setText(bundle.getString("no"));
	    			     tv_mess.setText("亲,你可能还要等"+(Integer.parseInt(OrderID)-Integer.parseInt(bundle.getString("no")))*30+"分钟喔");
	    		 
	    			 }
	    		 }
	    			System.out.println("companyName:"+bundle.getString("company")+"   number:"+bundle.getString("no")+"      where:"+bundle.getString("where")+"   OW:"+OW);
				 break;
		    	}
	    	default:
	    		break;
			  
			}
			
		}
		
	}
}
