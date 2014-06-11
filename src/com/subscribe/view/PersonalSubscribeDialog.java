package com.subscribe.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.subscribe.R;

public class PersonalSubscribeDialog extends Dialog{

	public Button btn_srue,btn_cancel;
	Context context;
	private PriorityListener listener;  
	
	public interface PriorityListener {  
        /** 
         * �ص�������������Dialog�ļ����¼�������ˢ��Activity��UI��ʾ 
         */  
        public void refreshPriorityUI(String string);  
    }  
	
	
	public PersonalSubscribeDialog(Context context,PriorityListener listener) {
		super(context);
		this.context = context;
		this.listener = listener;
	}
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.dialog_personalsubscribe);
	        btn_srue = (Button)findViewById(R.id.dialog_srue);
	        btn_cancel = (Button)findViewById(R.id.dialog_cancel);
	        this.setTitle("��ܰ��ʾ");
	        btn_cancel.setOnClickListener(new Button.OnClickListener() {  
	        	    
	        	     public void onClick(View v) { 
	        	      
	        	      dismiss();  
	        	    
	        	    } 
	        }); 
	        
	        btn_srue.setOnClickListener(new Button.OnClickListener() {  
	        	    
	        	     public void onClick(View v) {
	        	    	 listener.refreshPriorityUI("SRUE");	
	        	    	 dismiss();
	        	    } 
	    });
    }
}
