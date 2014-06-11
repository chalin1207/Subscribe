package com.subscribe.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subscribe.R;
import com.subscribe.bean.Url;
import com.subscribe.util.HttpUtil;
import com.subscribe.view.DeleteSubscribeDialog.PriorityListener;

public class StatisticsFramentCompanyDo extends Fragment{
	
	public String PerName,PerUsername,ComName,OrderTime,OrderID,OrederWhere,OW,CompanyUsername;
	private TextView tv_perName,tv_perUsername,tv_OrderTime,tv_OrderId,tv_OrderWhere;
	private Button btn_Delete;
	Url url;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View statisticsFramentCompanyDo = inflater.inflate(R.layout.frament_companystatistics_imp, container, false);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}	
		
		tv_perName = (TextView)statisticsFramentCompanyDo.findViewById(R.id.tv_sta_Name);
		tv_perUsername = (TextView)statisticsFramentCompanyDo.findViewById(R.id.tv_sta_Username);
		tv_OrderWhere = (TextView)statisticsFramentCompanyDo.findViewById(R.id.tv_sta_Where);
		tv_OrderId = (TextView)statisticsFramentCompanyDo.findViewById(R.id.tv_sta_Id);
		tv_OrderTime = (TextView)statisticsFramentCompanyDo.findViewById(R.id.tv_sta_Time);
		btn_Delete = (Button)statisticsFramentCompanyDo.findViewById(R.id.btn_staDelete);
		
		PerName = StatisticsFramentCompany.PerName;
		PerUsername = StatisticsFramentCompany.PerUsername;
		ComName = StatisticsFramentCompany.ComName;
		OrderTime = StatisticsFramentCompany.OrderTime;
		OrderID = StatisticsFramentCompany.OrderID;
		OrederWhere = StatisticsFramentCompany.OrederWhere;
		OW = StatisticsFramentCompany.OW;
		CompanyUsername = StatisticsFramentCompany.CompanyUSER;
		
System.out.println(PerName+"  "+PerUsername+"  "+ComName+"  "+OrderTime+"  "+OrderID+"  "+OrederWhere);
       
       tv_perName.setText(PerName);
       tv_perUsername.setText(PerUsername);
       tv_OrderTime.setText(OrderTime);
       tv_OrderId.setText(OrderID);
       tv_OrderWhere.setText(OrederWhere);
       
       btn_Delete.setOnClickListener(new ButtonListener());
       getActivity().setTitle(PerName+"的预约订单");

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

        return statisticsFramentCompanyDo;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			DeleteSubscribeDialog dsd = new DeleteSubscribeDialog(getActivity(), new PriorityListener() {
				
				@Override
				public void refreshPriorityUI(String string) {
					if(string.equals("SRUE")){
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("companyname",ComName));
						params.add(new BasicNameValuePair("personalusername",PerUsername));
						params.add(new BasicNameValuePair("orderwhere",OW));
						params.add(new BasicNameValuePair("companyusername",CompanyUsername));
						String Visitname = "DeletePersonalOrder";
						String deletePersonalOrder = HttpUtil.HttpSend(url, Visitname, params);
						if(deletePersonalOrder.equals("NoROOT")){
							Toast.makeText(getActivity(), "亲，你没有删除订单的权限喔~", Toast.LENGTH_LONG).show();
						}else if(deletePersonalOrder.equals("Sucess")){
							Toast.makeText(getActivity(), "恭喜亲，你删除成功了~", Toast.LENGTH_LONG).show();
							
							try {
								new Thread().sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction tran = fragmentManager.beginTransaction();
							tran.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
							StatisticsFramentCompany sfc = new StatisticsFramentCompany();
							tran.replace(R.id.content_frame,sfc);
							tran.commit();
						}
					}
				}
			});
			dsd.show();
			
		}
		
	}
}
