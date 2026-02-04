package org.uche.t0ken.api.gdax;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class OrderSerializer extends JsonSerializer<Order> {
    @Override
    public void serialize(Order arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
        
    	arg1.writeStartArray();
    	arg1.writeString(arg0.getPrice().toString());
    	arg1.writeString(arg0.getSize().toString());
    	arg1.writeEndArray();
    	
    	/*arg1.writeUTF8String(" [\"" + arg0.getSide().getIndex() + "\",\"" + arg0.getPrice().toString() +
    			 "\",\"" + arg0.getSize() + "\"]", 0, 10);
    	*/
        
        
    }

	
}