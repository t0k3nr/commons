package org.uche.t0ken.commons.util;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class InstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
        
        String result = arg0.toString();
        /*if (result.length()==24) {
        	result = result.replace("Z", "000Z");
        } else if (result.length()==25) {
        	result = result.replace("Z", "00Z");
        } else if (result.length()==26) {
        	result = result.replace("Z", "0Z");
        }*/
      
        arg1.writeString(result);
        
    }

	
}