package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddLog extends Activity {
	String id,log,time,person,contact,result;
	TextView tx_productId;
	EditText e_log,e_time,e_person,e_contact;
	Button b_addLog;
	int mDay,mMonth,mYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);
        
        id=this.getIntent().getExtras().getString("id");
        tx_productId=(TextView)findViewById(R.id.tx_productId);
        String title="型号"+id+"维修日志";
        tx_productId.setText(title);
        
        e_log=(EditText)findViewById(R.id.e_log);
        e_time=(EditText)findViewById(R.id.e_time);
        e_person=(EditText)findViewById(R.id.e_person);
        e_contact=(EditText)findViewById(R.id.e_contact);
        
        //初始化时间 
        Calendar calendar;
        calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        
        
        b_addLog=(Button)findViewById(R.id.B_addLog);
        b_addLog.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getData();
				if(isAllowed())
				{
					insertToMysql();
					showSuccess();
				}
				else
				{
					showFailed();
				}
			}
		});
        e_time.setInputType(InputType.TYPE_NULL);
        e_time.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(0);
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_log, menu);
        return true;
    }
    private void getData()
    {
    	log=e_log.getText().toString();
    	time=e_time.getText().toString();
    	person=e_person.getText().toString();
    	contact=e_contact.getText().toString();
    }
    private boolean isAllowed()
    {
    	try
		{
			Socket socket=new Socket("211.87.234.88",8888);
			PrintStream out=new PrintStream(socket.getOutputStream());
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			String msg="型号为"+id+"请求上传维修日志，是否允许？？";
			out.println(msg);
			result=reader.readLine();
			out.close();
			reader.close();
			socket.close();
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
    	return (result.equals("allow"));
    }
    private void insertToMysql()
    {
    	String url="http://211.87.234.88:8080/Server/servlet/addlog";
		
		
		NameValuePair value1=new BasicNameValuePair("product", id);
		NameValuePair value2=new BasicNameValuePair("time", time);
		NameValuePair value3=new BasicNameValuePair("person", person);
		NameValuePair value4=new BasicNameValuePair("contact", contact);
		NameValuePair value5=new BasicNameValuePair("log", log);
	
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(value1);
		list.add(value2);
		list.add(value3);
		list.add(value4);
		list.add(value5);
		try
		{
			HttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost(url);
			HttpEntity requestEntity=new UrlEncodedFormEntity(list,HTTP.UTF_8);
			post.setEntity(requestEntity);
			
			HttpResponse response=client.execute(post);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    private void showSuccess()
    {
    	Toast.makeText(getApplicationContext(), "上传日志成功", Toast.LENGTH_SHORT).show();
    }
    private void showFailed()
    {
    	Toast.makeText(getApplicationContext(), "上传被拒绝，请联系管理员", Toast.LENGTH_SHORT).show();
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {  
            case 0:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case 1:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            String mm;
            String dd;
             
            if (monthOfYear < 9) {
                mMonth = monthOfYear + 1;
                mm = "0" + mMonth;
            }
            else {
                mMonth = monthOfYear + 1;
                mm = String.valueOf(mMonth);
            }
             
            if (dayOfMonth <= 9) {
                mDay = dayOfMonth;
                dd = "0" + mDay;
            }
            else{
                mDay = dayOfMonth;
                dd = String.valueOf(mDay);
            }
             
            mDay = dayOfMonth;
             
            
                e_time.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
           
        }
    };
}
