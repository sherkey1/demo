package com.example.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HandleAlarm extends Activity {

	TextView tx_notification;
	Button b_cancel;
	String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_alarm);
        
        //获取id
        id=this.getIntent().getExtras().getString("text");
        
        tx_notification=(TextView)findViewById(R.id.tx_notification);
        String text=id+"该维修了";
        tx_notification.setText(text);
        
        b_cancel=(Button)findViewById(R.id.B_cancel);
        b_cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_handle_alarm, menu);
        return true;
    }
}
