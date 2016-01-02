package com.sentibrand.twitterdata;

import com.sentibrand.sentimentprocessing.SentimentResponse;
import twitter4j.Status;

import java.io.Serializable;

import static java.time.ZonedDateTime.now;

public class TwitterDataFactory implements Serializable {

    public TwitterData create(Status status, SentimentResponse sentimentResponse) {
        return new TwitterData(status.getId(),
                sentimentResponse.getText(),
                sentimentResponse.getSentiment(),
                now());
    }
}
