package com.sentibrand.brandsentimentslice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

import static com.sentibrand.JacksonFactory.objectMapper;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class BrandSentimentSliceClient implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BrandSentimentSliceClient.class);

    private final ObjectMapper objectMapper;

    public BrandSentimentSliceClient() {
        this.objectMapper = objectMapper();
    }

    public void save(BrandSentimentSlice brandSentimentSlice) {
        try {
            Request.Put("http://localhost:8888/brandsentimentslice")
                    .bodyString(objectMapper.writeValueAsString(brandSentimentSlice), APPLICATION_JSON)
                    .execute()
                    .returnResponse();
        }
        catch (IOException e) {
            logger.error("Unable to save brand sentiment slice", e);
            throw new RuntimeException(e);
        }
    }
}
