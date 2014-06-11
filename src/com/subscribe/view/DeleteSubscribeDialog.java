package com.subscribe.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.subscribe.R;

public class DeleteSubscribeDialog extends Dialog{
	
	Context context;
	private PriorityListener listener;  
	private Button btn_cancel,btn_srue;
	
	public interface PriorityListener {  
        /** 
         * �ص�������������Dialog�ļ����¼�������ˢ��Activity��UI��ʾ 
         */  
        public void refreshPriorityUI(String string);  
    }  
	
	public DeleteSubscribeDialog(Context context,PriorityListener listener) {
		super(context);
		this.context = context;
		this.listener = listener;
	}
	
		
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.dialog_deleteorder);
	        this.setTitle("ע��");
	        btn_cancel = (Button)findViewById(R.id.deletedialog_cancel);
	        btn_srue = (Button)findViewById(R.id.deletedialog_srue);
	        
	        btn_cancel.setOnClickListener(new ButtonListener());
	        btn_srue.setOnClickListener(new ButtonListener());
    }
	 
	 private class ButtonListener implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.deletedialog_cancel){
				dismiss();
			}if(v.getId() == R.id.deletedialog_srue){
				listener.refreshPriorityUI("SRUE");
				dismiss();
			}
			
		}
		 
	 }
}
