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

public class OrderDeserializer extends JsonDeserializer<Order> {
    @Override
    public Order deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
       ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<String>>(){});
        List<String> someClassList = objectReader.readValue(arg0);
        Order order = new Order();
        order.setPrice(new BigDecimal(someClassList.get(0)));
        order.setSize(new BigDecimal(someClassList.get(1)));
        return order;
    }
}