package com.sentibrand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.Serializable;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.Objects.isNull;

public class JacksonFactory implements Serializable {

    private static ObjectMapper objectMapper;

    public synchronized static ObjectMapper objectMapper() {
        if(isNull(objectMapper)) {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JSR310Module());
            objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        }
        return objectMapper;
    }
}
