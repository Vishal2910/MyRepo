package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.UsersData;

public class PostApiTest extends TestBase{
	TestBase testBase;
	String url;
	String serviceURLpost;
	String uriPost;
	RestClient restClient;
	CloseableHttpResponse cloaseableHttpResponse;
	
	
	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		 url = prop.getProperty("url");
		 serviceURLpost = prop.getProperty("serviceURLpost");
		 uriPost = url+serviceURLpost;	
	}
	
	@Test
	public void postApiTest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		// Jackson Api -- used to convert Simple java class to JSON format
		ObjectMapper mapper = new ObjectMapper();
		UsersData users = new UsersData("Vishal","Leader");		//expected users object
		
		//Object to JSON file conversion -- called Mashalling
		mapper.writeValue(new File("D:\\Testing-Workspace\\restapi\\src\\main\\java\\com\\qa\\data\\usersData.json"), users); //Run code and Observe in usersData.json file
		
		//Java Object to Json in String
		String usersJSONString = mapper.writeValueAsString(users);
		System.out.println("Users JSON String :"+usersJSONString);
		
		
		
		cloaseableHttpResponse = restClient.post(uriPost, usersJSONString, headerMap);
		
		//Ststus code
		int statuscode = cloaseableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is "+statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_201);
		
		//2.JSONString
		String responseString = EntityUtils.toString(cloaseableHttpResponse.getEntity(),"UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response string is "+responseJson);
		
		//JSON to java Object : called Unmarshalling
		UsersData usersObj = mapper.readValue(responseString, UsersData.class);		//Actual users object after api response
		//System.out.println("Users Response "+usersObj.toString());
		
		Assert.assertTrue(users.getJob().equals(usersObj.getJob()));
		System.out.println(users.getJob().equals(usersObj.getJob()));
		
		Assert.assertTrue(users.getName().equals(usersObj.getName()));
		System.out.println(users.getName().equals(usersObj.getName()));
		
	}
	
	
	
	
	

}
