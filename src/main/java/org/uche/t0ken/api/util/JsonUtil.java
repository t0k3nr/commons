package org.uche.t0ken.api.util;

import java.io.IOException;

import org.uche.t0ken.api.gdax.Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	public static String serialize(Object obj, boolean pretty) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();

	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    //mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    	
	    if (pretty) {
	        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	    } else {
	    	 return mapper.writeValueAsString(obj);
	    }

	   
	}
	
	public static Object deserialize(String json) throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.readValue(json, Data.class);
	   
	   
	}
}
