package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;


public class Query extends Activity {

	TextView tx_showData;
	String id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        
        id=this.getIntent().getExtras().getString("id");
        
        tx_showData=(TextView)findViewById(R.id.tx_queryData);
        tx_showData.setText(id);
        try
        {
        	StringBuffer sb=new StringBuffer();
        	String body=getContent(); 
//        	JSONArray array=new JSONArray(body);
//        	for(int i=0;i<array.length();i++)
//        	{
//        		JSONObject obj=array.getJSONObject(i);
//        		sb.append("��Ʒ�ͺţ�").append(obj.getString("��Ʒ�ͺ�")).append("\n");
//        		sb.append("�������ң�").append(obj.get("��������")).append("\n");
//        		sb.append("���۳���:").append(obj.get("���۳���")).append("\n");
//        		sb.append("��������:").append(obj.get("��������")).append("\n");
//        		sb.append("��װ����:").append(obj.get("��װ����")).append("\n");
//        		sb.append("��װ��Ա:").append(obj.get("��װ��Ա")).append("\n");
//        		sb.append("��ϵ��ʽ:").append(obj.get("��ϵ��ʽ")).append("\n");
//        	}
//        	tx_showData.setText(sb.toString());   
        	tx_showData.setText(body);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }

    private String getContent() {
		// TODO Auto-generated method stub
    	StringBuilder sb=new StringBuilder();
    	String url="http://10.0.2.2:8080/Server/servlet/query";
    	NameValuePair value=new BasicNameValuePair("id", "2");
    	List<NameValuePair> pairs=new ArrayList<NameValuePair>();
    	pairs.add(value);
    	
    	try
    	{
    		HttpClient client=new DefaultHttpClient();
    		HttpPost post=new HttpPost(url);
    		HttpEntity requestEntity=new UrlEncodedFormEntity(pairs);
    		post.setEntity(requestEntity);
    		
    		HttpResponse response=client.execute(post);
    		HttpEntity entity=response.getEntity();
    		
    		if(entity!=null)
    		{
    			BufferedReader reader=new BufferedReader(new InputStreamReader(
    					entity.getContent(),"UTF-8"),8192);
    			String line=null;
    			
    			while((line=reader.readLine())!=null)
    			{
    				sb.append(line+"\n");
    			}
    			reader.close();
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
		return sb.toString();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_query, menu);
        return true;
    }
}
