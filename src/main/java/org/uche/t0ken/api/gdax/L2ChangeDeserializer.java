package org.uche.t0ken.api.gdax;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class L2ChangeDeserializer extends JsonDeserializer<L2Change> {
    @Override
    public L2Change deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
       ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<String>>(){});
        List<String> someClassList = objectReader.readValue(arg0);
        L2Change change = new L2Change();
        change.setSide(Side.getEnum(someClassList.get(0)));
        change.setPrice(new BigDecimal(someClassList.get(1)));
        change.setSize(new BigDecimal(someClassList.get(2)));
        return change;
    }
}