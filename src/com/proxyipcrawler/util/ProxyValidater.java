package com.proxyipcrawler.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class ProxyValidater {

	
	public static boolean check(String host, String port) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://web.chacuo.net/netproxycheck");
		List<NameValuePair> nvps= new ArrayList<NameValuePair>();
	
		nvps.add(new BasicNameValuePair("data", host));
		nvps.add(new BasicNameValuePair("type","proxycheck"));
		
		nvps.add(new BasicNameValuePair("arg","p="+port+"_t=1_o=5"));
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		HttpResponse re = null;
		try {
			re = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(re == null ) return false;
		
		boolean isValid = false;
		if(re.getStatusLine().getStatusCode() == 200 ) {
			String content = null;
			try {
				content = EntityUtils.toString(re.getEntity());
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String str = FileUtils.chinaToUnicode("属于");
			if(content != null && content.indexOf(str) != -1 )
				isValid =  true;
		}else{
			System.err.println("请求失败:"+host+":"+port);
		}
		
		return isValid;
	}
}
