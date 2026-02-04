package org.uche.t0ken.api.gdax;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class InstantDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
        String date = arg0.getText();
        date = date.replace(" ", "T");
        date = date.replace("NaN", "00");
        Instant result = Instant.parse(date);
    	return result;
    }
}