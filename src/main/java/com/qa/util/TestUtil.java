package com.qa.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {
	public static JSONObject responsejson;
	
	public static String getValueByJSONPath(JSONObject responsejson , String jsonPath) {
		
		Object obj = responsejson;
		for (String s : jsonPath.split("/")) 
			if(!(s.contains("[") || s.contains("]")))	//For single value go in if part
				obj = ((JSONObject) obj).get(s);
			else if(s.contains("[") || s.contains("]"))		//for array go in else if part
				obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
			return obj.toString();
	
	}
}
