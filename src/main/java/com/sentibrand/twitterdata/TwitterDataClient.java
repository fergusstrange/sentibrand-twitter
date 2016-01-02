package com.sentibrand.twitterdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static com.sentibrand.JacksonFactory.objectMapper;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class TwitterDataClient implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TwitterDataClient.class);

    private final ObjectMapper objectMapper;

    public TwitterDataClient() {
        this.objectMapper = objectMapper();
    }

    public void save(List<TwitterData> twitterDatas) {
        try {
            Request.Put("http://localhost:8888/twitterdatas")
                    .bodyString(objectMapper.writeValueAsString(twitterDatas), APPLICATION_JSON)
                    .execute()
                    .returnResponse();
        }
        catch (IOException e) {
            logger.error("Unable to save twitterDatas", e);
            throw new RuntimeException(e);
        }
    }
}
