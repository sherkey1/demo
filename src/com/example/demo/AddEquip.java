package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
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
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddEquip extends Activity {

	String id ,type, department, manufacturer, vendor, pro_time, an_time,
			an_person, contact;

	EditText e_addEquip_id;
	EditText e_addEquip_type;
	EditText e_addEquip_department;
	EditText e_addEquip_manufacturer;
	EditText e_addEquip_vendor;
	EditText e_addEquip_protime;
	EditText e_addEquip_antime;
	EditText e_addEquip_anperson;
	EditText e_addEquip_contact;

	Button b_addEquip;
	
	int mDay,mMonth,mYear;
	String IPADDRESS=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_equip);
		
		IPADDRESS=this.getIntent().getExtras().getString("IPADDRESS");
		
		//初始化时间 
        Calendar calendar;
        calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);

		e_addEquip_id = (EditText) findViewById(R.id.e_addEquip_id);
		e_addEquip_type = (EditText) findViewById(R.id.e_addEquip_type);
		e_addEquip_department = (EditText) findViewById(R.id.e_addEquip_department);
		e_addEquip_manufacturer = (EditText) findViewById(R.id.e_addEquip_manufacturer);
		e_addEquip_vendor = (EditText) findViewById(R.id.e_addEquip_vendor);
		e_addEquip_protime = (EditText) findViewById(R.id.e_addEquip_protime);
		e_addEquip_antime = (EditText) findViewById(R.id.e_addEquip_antime);
		e_addEquip_anperson = (EditText) findViewById(R.id.e_addEquip_anperson);
		e_addEquip_contact = (EditText) findViewById(R.id.e_addEquip_contact);

		b_addEquip = (Button) findViewById(R.id.B_addEquip_done);
		b_addEquip.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				getData();
				if("".equals(e_addEquip_id.getText().toString().trim()))
				{
					Toast.makeText(AddEquip.this, "请输入产品编号", Toast.LENGTH_SHORT).show();
				
				}
				else
				{
					insertIntoMysql();
				}
			}
		});
		e_addEquip_protime.setInputType(InputType.TYPE_NULL);
		e_addEquip_protime.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(0);
			}
		});
		e_addEquip_protime.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{
					showDialog(0);
				}
				
			}
		});
		e_addEquip_antime.setInputType(InputType.TYPE_NULL);
		e_addEquip_antime.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(1);
				
			}
		});
		e_addEquip_antime.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{
					showDialog(1);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_equip, menu);
		return true;
	}

	private void getData() {
		id = e_addEquip_id.getText().toString();
		type = e_addEquip_type.getText().toString();
		department = e_addEquip_department.getText().toString();
		manufacturer = e_addEquip_manufacturer.getText().toString();
		vendor = e_addEquip_vendor.getText().toString();
		pro_time = e_addEquip_protime.getText().toString();
		an_time = e_addEquip_antime.getText().toString();
		an_person = e_addEquip_anperson.getText().toString();
		contact = e_addEquip_contact.getText().toString();
	}
	private void insertIntoMysql()
	{
		String url=IPADDRESS+"/Server/servlet/newequip";
		
		
		NameValuePair value1=new BasicNameValuePair("id", id);
		NameValuePair value2=new BasicNameValuePair("type", type);
		NameValuePair value3=new BasicNameValuePair("department", department);
		NameValuePair value4=new BasicNameValuePair("manufacturer", manufacturer);
		NameValuePair value5=new BasicNameValuePair("vendor", vendor);
		NameValuePair value6=new BasicNameValuePair("pro_time", pro_time);
		NameValuePair value7=new BasicNameValuePair("an_time", an_time);
		NameValuePair value8=new BasicNameValuePair("an_person", an_person);
		NameValuePair value9=new BasicNameValuePair("contact", contact);
		
	
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(value1);
		list.add(value2);
		list.add(value3);
		list.add(value4);
		list.add(value5);
		list.add(value6);
		list.add(value7);
		list.add(value8);
		list.add(value9);
		
		try
		{
			HttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost(url);
			HttpEntity requestEntity=new UrlEncodedFormEntity(list,HTTP.UTF_8);
			post.setEntity(requestEntity);
			
			HttpResponse response=client.execute(post);
			Toast.makeText(AddEquip.this, "增加设备信息成功", Toast.LENGTH_SHORT).show();
			
		}
		catch(Exception e)
		{
			Toast.makeText(AddEquip.this, "连接不到服务器", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		 switch (id) {  
         case 0:
             return new DatePickerDialog(this, mDateSetListener_protime, mYear, mMonth, mDay);
         case 1:
             return new DatePickerDialog(this, mDateSetListener_antime, mYear, mMonth, mDay);
     }
     return null;
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener_protime = new DatePickerDialog.OnDateSetListener() {
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
             
            
                e_addEquip_protime.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
           
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener_antime = new DatePickerDialog.OnDateSetListener() {
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
             
            
                e_addEquip_antime.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
           
        }
    };
	
}
