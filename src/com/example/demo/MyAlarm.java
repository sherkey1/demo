package com.example.demo;



import java.util.Calendar;




import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MyAlarm extends Activity {
	
	Spinner spinner_year,spinner_month,spinner_day;
	String id;
	int year=0,month=0,day=0;
	
	Button disableReminder,enableReminder;
	public AlarmManager alarmManager;
	public PendingIntent pi_1,pi_2;
	public static final long INTERVAL_DAY = 86400000L;
	public static final long INTERVAL_MONTH = 86400000L*30;
	public static final long INTERVAL_YEAR = 86400000L*30*12;
	public static long time=0L;
	long triggerAtTime = SystemClock.elapsedRealtime() + 30 * 1000;  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alarm);
        
        id=this.getIntent().getExtras().getString("id");
        Toast.makeText(MyAlarm.this, "型号为"+id+"的到期提醒设置", Toast.LENGTH_SHORT).show();
        
        //设置年
        spinner_year=(Spinner)findViewById(R.id.spinner_year);
        ArrayAdapter<CharSequence> adapter_year=
        		ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter_year);
        spinner_year.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				year=Integer.parseInt(arg0.getItemAtPosition(arg2).toString());	
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				year=0;
			}
		});
        //设置月
        spinner_month=(Spinner)findViewById(R.id.spinner_month);
        ArrayAdapter<CharSequence> adapter_month=
        		ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter_month);
        spinner_month.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				month=Integer.parseInt(arg0.getItemAtPosition(arg2).toString());	
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				month=0;
			}
		});
        //设置天
        spinner_day=(Spinner)findViewById(R.id.spinner_Day);
        ArrayAdapter<CharSequence> adapter_day=
        		ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter_day);
        spinner_day.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				day=Integer.parseInt(arg0.getItemAtPosition(arg2).toString());	
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				day=0;
			}
		});
        //设置提醒按钮
        enableReminder=(Button)findViewById(R.id.B_enableReminder);
        enableReminder.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
//				triggerAtTime=triggerAtTime+year*INTERVAL_YEAR+month*INTERVAL_MONTH+day*INTERVAL_DAY;
				
				setReminder(true);
				Toast.makeText(MyAlarm.this, "您已设置对该设备的提醒于"+year+"年"+month+"月"+day+"天之后", Toast.LENGTH_SHORT).show();
				
			}
		});
        //关闭提醒
        disableReminder=(Button)findViewById(R.id.B_disableReminder);
        disableReminder.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setReminder(false);
//				Toast.makeText(MyAlarm.this, "您已取消了这个设备的提醒", Toast.LENGTH_SHORT).show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_alarm, menu);
        return true;
    }
    private int StringToInt(String s)
    {
//    	int result=0;
//    	for(int i=0;i<s.length();i++)
//    	{
//    		char ch=s.charAt(i);
//    		result=result+(int)ch;
//    	}
    	int result=s.hashCode();
    	return result;
    }
    private void setReminder(boolean b)
    {
    	alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
//    	pi_1=PendingIntent.getBroadcast(MyAlarm.this, 0, new Intent(this,MyReceiver.class), 0);
//    	String s1,s2;
//    	s1="第一个";
//    	s2="第二个";
//    	Intent intent1=new Intent(this,MyReceiver.class);
//    	intent1.putExtra("text", s1);
    	Intent intent2=new Intent(this,MyReceiver.class);
    	intent2.putExtra("text", id);
//    	pi_1=PendingIntent.getBroadcast(MyAlarm.this, StringToInt(s1),intent1, 0);
    	pi_2=PendingIntent.getBroadcast(MyAlarm.this,id.hashCode(),intent2, 0);
    	if(b)
    	{
    		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime, pi_2);
//    		alarmManager.setRepeating(alarmManager.RTC_WAKEUP, 0, 10000, pi_1);
//    		alarmManager.setRepeating(alarmManager.RTC_WAKEUP, 0, 1000*30, pi_2);
    		
    	}
    	else
    	{
//    		alarmManager.cancel(pi_1);
    		alarmManager.cancel(pi_2);
    		
    	}
    }
}
