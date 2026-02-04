package org.uche.t0ken.api.gdax;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class L2ChangeSerializer extends JsonSerializer<L2Change> {
    @Override
    public void serialize(L2Change arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
        
    	arg1.writeStartArray();
    	arg1.writeString(arg0.getSide().getName());
    	arg1.writeString(arg0.getPrice().toString());
    	arg1.writeString(arg0.getSize().toString());
    	arg1.writeEndArray();
    	
    	
    }

	
}