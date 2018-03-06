package com.physics.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;


public class JsonConvertor {	
	private static  ObjectMapper mapper = null;
	@SuppressWarnings("deprecation")
	private static ObjectMapper getMapper(){
		if(mapper == null){
			mapper= new ObjectMapper();
		}
		mapper.getSerializationConfig().disable(Feature.FAIL_ON_EMPTY_BEANS);
		mapper.configure(Feature.WRITE_NULL_PROPERTIES,false);
		return mapper; 
	}
	
	public static final String mapJSON(Map<String,String> jsonMap){
		String result = "";
		if( jsonMap == null || jsonMap.size() == 0){
			result += "{}";
			return result;
		}
		result += "{root:[";
		for(Map.Entry<String, String> entry:jsonMap.entrySet()){
			String row = "{name:'"+entry.getKey()+"',value:'"+entry.getValue()+"'}";
			result += row+",";
		}
		result = result.substring(0, result.length()-1);
		result += "]}";
		return result;
	}
	
	public static final String obj2JSON(Object obj){
		String json = null;
		try {
			json = getMapper().writeValueAsString(obj);
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
}
