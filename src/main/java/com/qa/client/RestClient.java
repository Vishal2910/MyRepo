package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	//Get Method without Headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); 	//http get request
		System.out.println("Going for execution");
		CloseableHttpResponse cloaseableHttpResponse = httpClient.execute(httpGet); 		//Hit the API get URL
		return cloaseableHttpResponse;
	}
		/*//1.Get status code
		int ststusCode = cloaseableHttpResponse.getStatusLine().getStatusCode(); 	
		System.out.println("Status code ------->" +ststusCode);
		
		//2.To get Status String
		String responseString = EntityUtils.toString(cloaseableHttpResponse.getEntity(),"UTF-8"); 
		
		JSONObject responseJson = new JSONObject(responseString); 		//String converted to JSON FORMAT
		System.out.println("Response of API in JSON ---> " +responseJson );
		
		//3.All Headers
		Header[] headerArray = cloaseableHttpResponse.getAllHeaders(); //Create an array to store the header response
		HashMap<String, String> allHeaders = new  HashMap<String, String>(); //Create hashMap to store the values
		
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("All headers array Name and value " +allHeaders);
	}*/
	//Get method with Headers
	public CloseableHttpResponse get(String url, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); 	//http get request
		
		for(Map.Entry<String, String> entry : headerMap.entrySet())
		{
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse cloaseableHttpResponse = httpClient.execute(httpGet); 		//Hit the API get URL
		return cloaseableHttpResponse;
	}
	
	//POST method
	public CloseableHttpResponse post(String url , String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(entityString));		//payLoad
		
		//for Headers
		for(Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(),entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost); //Hit request
		return closeableHttpResponse;
	}

}
