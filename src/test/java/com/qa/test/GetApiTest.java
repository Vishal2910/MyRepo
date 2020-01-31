package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetApiTest extends TestBase{
	TestBase testBase;
	RestClient restClient;
	String url;
	String apiURL;
	String uri;
	CloseableHttpResponse cloaseableHttpResponse;
	
	
	@BeforeMethod
	public void setUp() {
	testBase = new TestBase();
	url = prop.getProperty("url");	//https://reqres.in/
	apiURL = prop.getProperty("serviceURL");	//api/users
	
	uri = url+apiURL; //https://reqres.in/api/users
	
	
	}
	
	@Test(priority=0)
	public void getAPITestwithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		cloaseableHttpResponse = restClient.get(uri);
		
		//1.Get status code
				int ststusCode = cloaseableHttpResponse.getStatusLine().getStatusCode(); 	
				System.out.println("Status code ------->" +ststusCode);
				Assert.assertEquals(ststusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
				
				//2.To get Status String
				String responseString = EntityUtils.toString(cloaseableHttpResponse.getEntity(),"UTF-8"); 
				
				JSONObject responseJson = new JSONObject(responseString); 		//String converted to JSON FORMAT
				System.out.println("Response of API in JSON ---> " +responseJson );
	
				
				//Single value assertion
				//perPage Value
				String perPageValue = TestUtil.getValueByJSONPath(responseJson, "per_page");
				System.out.println("Value of per Page "+perPageValue);
				Assert.assertEquals(perPageValue, "6");
				System.out.println("Test");

				//total value
				String total = TestUtil.getValueByJSONPath(responseJson, "total");
				System.out.println("Value of total value "+total);
				Assert.assertEquals(Integer.parseInt(total), 12);
				
				//Get value from JSON array
				String lastName = TestUtil.getValueByJSONPath(responseJson, "data[1]/last_name");
				String email = TestUtil.getValueByJSONPath(responseJson, "data[1]/email");
				String first_name = TestUtil.getValueByJSONPath(responseJson, "data[1]/first_name");
				String id = TestUtil.getValueByJSONPath(responseJson, "data[1]/id");
				String avatar = TestUtil.getValueByJSONPath(responseJson, "data[1]/avatar");
				
				System.out.println(lastName);
				System.out.println(email);
				System.out.println(first_name);
				System.out.println(id);
				System.out.println(avatar);
				
				//3.All Headers
				Header[] headerArray = cloaseableHttpResponse.getAllHeaders(); //Create an array
				HashMap<String, String> allHeaders = new  HashMap<String, String>(); //Create hashMap to store the values
				
				for (Header header : headerArray) {
					allHeaders.put(header.getName(), header.getValue());
					
				}
				System.out.println("All headers array Name and value " +allHeaders);
			}
	
	@Test(priority=1)
	public void getAPITestwithHeaders() throws ClientProtocolException, IOException{

		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
	//	headerMap.put("UserName", "test");		//Dummy
	//	headerMap.put("Pass", "test@123");
		
		
		cloaseableHttpResponse = restClient.get(uri,headerMap);
		
		//1.Get status code
				int ststusCode = cloaseableHttpResponse.getStatusLine().getStatusCode(); 	
				System.out.println("Status code ------->" +ststusCode);
				Assert.assertEquals(ststusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
				
				//2.To get Status String
				String responseString = EntityUtils.toString(cloaseableHttpResponse.getEntity(),"UTF-8"); 
				
				JSONObject responseJson = new JSONObject(responseString); 		//String converted to JSON FORMAT
				System.out.println("Response of API in JSON ---> " +responseJson );
				
				//Single value assertion
				//perPage Value
				String perPageValue = TestUtil.getValueByJSONPath(responseJson, "per_page");
				System.out.println("Value of per Page "+perPageValue);
				Assert.assertEquals(perPageValue, "6");
				
				//total value
				String total = TestUtil.getValueByJSONPath(responseJson, "total");
				System.out.println("Value of total value "+total);
				Assert.assertEquals(Integer.parseInt(total), 12);
				
				//Get value from JSON array
				String lastName = TestUtil.getValueByJSONPath(responseJson, "data[0]/last_name");
				String email = TestUtil.getValueByJSONPath(responseJson, "data[0]/email");
				String first_name = TestUtil.getValueByJSONPath(responseJson, "data[0]/first_name");
				String id = TestUtil.getValueByJSONPath(responseJson, "data[0]/id");
				String avatar = TestUtil.getValueByJSONPath(responseJson, "data[0]/avatar");
				
				System.out.println(lastName);
				System.out.println(email);
				System.out.println(first_name);
				System.out.println(id);
				System.out.println(avatar);
				
				//3.All Headers
				Header[] headerArray = cloaseableHttpResponse.getAllHeaders(); //Create an array
				HashMap<String, String> allHeaders = new  HashMap<String, String>(); //Create hashMap to store the values
				
				for (Header header : headerArray) {
					allHeaders.put(header.getName(), header.getValue());
					
				}
				System.out.println("All headers array Name and value " +allHeaders);
			
	}
	}
	