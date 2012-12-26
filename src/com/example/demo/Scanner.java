package com.example.demo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Scanner extends Activity {
	Button b_scan, b_query, b_addEquip, b_maintance, b_reminder,b_ftp,b_lookData;
	TextView result;
	public String text_result = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner);

		// 处理扫描按钮
		b_scan = (Button) findViewById(R.id.B_scanner);
		result = (TextView) findViewById(R.id.tx_scanResult);

		b_scan.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				startActivityForResult(intent, 0);
			}
		});

		// 处理查询按钮
		b_query = (Button) findViewById(R.id.B_showData);
		b_query.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 使用Query类
				// Intent intent =new Intent();
				// intent.setClass(Scanner.this, Query.class);
				// intent.putExtra("id", text_result);
				// startActivity(intent);
				if (text_result != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String url = "http://211.87.234.88:8080/Server/servlet/show?id=";
					url = url + text_result;
					intent.setData(Uri.parse(url));
					startActivity(intent);
				} else {
					Toast.makeText(Scanner.this, "请先进行设备扫描", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		// 处理增加设备信息按钮
		b_addEquip = (Button) findViewById(R.id.B_addEquip);
		b_addEquip.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 用网页的 形式来处理
				// Intent intent=new Intent(Intent.ACTION_VIEW);
				// String url="http://211.87.234.88:8080/Server/newEquip.jsp";
				// intent.setData(Uri.parse(url));
				// startActivity(intent);
				// 直接用手机来处理
				Intent intent = new Intent();
				intent.setClass(Scanner.this, AddEquip.class);
				startActivity(intent);
			}
		});

		// 增加日志
		b_maintance = (Button) findViewById(R.id.B_maintain);
		b_maintance.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (text_result != null) {
					Intent intent = new Intent();
					intent.setClass(Scanner.this, AddLog.class);
					intent.putExtra("id", text_result);
					startActivity(intent);
				} else {
					Toast.makeText(Scanner.this, "请先进行设备扫描", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		// 处理到期提醒
		b_reminder = (Button) findViewById(R.id.B_reminder);
		b_reminder.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (text_result != null) {
					Intent intent = new Intent();
					intent.setClass(Scanner.this, MyAlarm.class);
					intent.putExtra("id", text_result);
					startActivity(intent);
				} else {
					Toast.makeText(Scanner.this, "请先进行设备扫描", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		//处理下载资料
		b_ftp=(Button)findViewById(R.id.B_ftp);
		b_ftp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PackageManager packageManager = Scanner.this.getPackageManager(); 
				Intent intent=new Intent(); 
				    try { 
				    intent =packageManager.getLaunchIntentForPackage("lysesoft.nandftp1"); 
				} catch (Exception e) { 
				e.printStackTrace();
				} 
				startActivity(intent); 
			}
		});
		//处理查看资料
		b_lookData=(Button)findViewById(R.id.B_lookData);
		b_lookData.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PackageManager packageManager = Scanner.this.getPackageManager(); 
				Intent intent=new Intent(); 
				    try { 
				    intent =packageManager.getLaunchIntentForPackage("com.estrongs.android.pop"); 
				} catch (Exception e) { 
				e.printStackTrace();
				} 
				startActivity(intent); 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_scanner, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != 0) {
			return;
		}
		text_result = data.getStringExtra("SCAN_RESULT");
		result.setText(text_result);
	}
}
