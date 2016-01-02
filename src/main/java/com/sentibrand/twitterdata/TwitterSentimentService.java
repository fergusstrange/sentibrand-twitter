package com.sentibrand.twitterdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentibrand.sentimentprocessing.SentimentRequest;
import com.sentibrand.sentimentprocessing.SentimentResponse;
import org.apache.http.client.fluent.Request;
import twitter4j.Status;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import static com.sentibrand.JacksonFactory.objectMapper;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class TwitterSentimentService implements Serializable {

    private final TwitterDataFactory twitterDataFactory;
    private final ObjectMapper objectMapper;

    public TwitterSentimentService() {
        this.twitterDataFactory = new TwitterDataFactory();
        this.objectMapper = objectMapper();
    }

    public TwitterData process(Status status) {
        try {
            InputStream content = Request.Post("http://localhost:8080/sentiment")
                    .bodyString(json(status), APPLICATION_JSON)
                    .execute()
                    .returnResponse()
                    .getEntity()
                    .getContent();

            SentimentResponse sentimentResponse = objectMapper.readValue(content, SentimentResponse.class);

            return twitterDataFactory.create(status, sentimentResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String json(Status status) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new SentimentRequest(status.getText()));
    }
}
