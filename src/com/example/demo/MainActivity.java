package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.R.bool;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint({ "ShowToast", "ShowToast" })
public class MainActivity extends Activity {
	WifiManager wifimanager;
	Button login,wifiOn,wifiOff;
	EditText e_user,e_pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        e_user=(EditText)findViewById(R.id.e_username);
        e_pwd=(EditText)findViewById(R.id.e_pwd);
        
        
        
        login=(Button)findViewById(R.id.B_login);
        login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//调用isCorrect()连服务器
				if(true)
				{
					Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT).show();
					openScanner();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "请输入正确的用户名和密码", Toast.LENGTH_SHORT).show();
				}
			}

			private boolean isCorrect() {
				// TODO Auto-generated method stub
				String url="http://211.87.234.88:8080/Server/servlet/login";
				String out = null;
				//拿到用户名和密码
		        String name=e_user.getText().toString();
		        String pwd=e_pwd.getText().toString();
				NameValuePair value1=new BasicNameValuePair("name",name);
				NameValuePair value2=new BasicNameValuePair("pwd",pwd);
				List<NameValuePair> pairs=new ArrayList<NameValuePair>();
				pairs.add(value1);
				pairs.add(value2);
				
				//开始传输
				try
				{
					HttpClient client=new DefaultHttpClient();
					HttpPost post=new HttpPost(url);
					HttpEntity requestEntity=new UrlEncodedFormEntity(pairs);
					post.setEntity(requestEntity);
					
					HttpResponse response=client.execute(post);
					HttpEntity entity=response.getEntity();
					out=EntityUtils.toString(entity);	
					//new AlertDialog.Builder(MainActivity.this).setMessage(out).create().show();
				}
				catch(Exception e )
				{
					Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				return(out.equals("success"));
			}

			private void openScanner() {
				// TODO Auto-generated method stub
				String actionName="android.intent.action.Scanner";
				Intent intent=new Intent(actionName);
				MainActivity.this.startActivity(intent);
				
			}
		});
        
        wifiOn=(Button)findViewById(R.id.B_wifiOn);
        wifiOn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				wifimanager=(WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
				wifimanager.setWifiEnabled(true);
				String state = null;
				switch (wifimanager.getWifiState()) {
				case 1:
					state="WIFI网卡不可用";
					break;
				case 0:
					state="WIFI正在关闭";
					break;
				case 3:
					state="WIFI网卡可用";
					break;
				case 2:
					state="WIFI网卡正在打开";
					break;
				case 4:
					state="未知网络状态";
					break;

				default:
					break;
				}
				Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
			}
		});
        
        wifiOff=(Button)findViewById(R.id.B_wifiOff);
        wifiOff.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				wifimanager=(WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
				wifimanager.setWifiEnabled(false);
				String state = null;
				switch (wifimanager.getWifiState()) {
				case 1:
					state="WIFI网卡不可用";
					break;
				case 0:
					state="WIFI正在关闭";
					break;
				case 3:
					state="WIFI网卡可用";
					break;
				case 2:
					state="WIFI网卡正在打开";
					break;
				case 4:
					state="未知网络状态";
					break;

				default:
					break;
				}
				Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
