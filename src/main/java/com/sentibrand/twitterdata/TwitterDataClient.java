package com.sentibrand.twitterdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static com.sentibrand.JacksonFactory.objectMapper;
import static java.lang.System.getProperty;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class TwitterDataClient implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TwitterDataClient.class);

    private final ObjectMapper objectMapper;
    private final String elasticHost;

    public TwitterDataClient() {
        this.objectMapper = objectMapper();
        this.elasticHost = getProperty("elastic.host", "localhost:8888");
    }

    public void save(List<TwitterData> twitterDatas) {
        try {
            Request.Put(elasticHost())
                    .bodyString(objectMapper.writeValueAsString(twitterDatas), APPLICATION_JSON)
                    .execute()
                    .returnResponse();
        }
        catch (IOException e) {
            logger.error("Unable to save twitterDatas", e);
            throw new RuntimeException(e);
        }
    }

    private String elasticHost() {
        return String.format("http://%s/twitterdatas", elasticHost);
    }
}
