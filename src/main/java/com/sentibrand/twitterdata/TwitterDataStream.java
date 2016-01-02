package com.sentibrand.twitterdata;

import com.sentibrand.brandsentimentslice.BrandSentimentService;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.apache.spark.streaming.Durations.seconds;
import static org.apache.spark.streaming.twitter.TwitterUtils.createStream;

public class TwitterDataStream {

    private static final Logger logger = LoggerFactory.getLogger(TwitterDataStream.class);

    public void openAndProcessStream(String... keys) {
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(
                new SparkConf()
                        .setMaster("local[4]")
                        .setAppName("TwitterTest"), seconds(1L));

        createStream(javaStreamingContext, authorization(), keys)
                .window(seconds(60L), seconds(60L))
                .mapPartitions(statusIterator -> {
                    TwitterSentimentService twitterSentimentService = new TwitterSentimentService();
                    return StreamSupport.stream(((Iterable<Status>) () -> statusIterator).spliterator(), true)
                            .map(twitterSentimentService::process)
                            .collect(toList());
                })
                .foreachRDD(twitterDataJavaRDD -> {
                    try {
                        new BrandSentimentService().createAndSave(twitterDataJavaRDD.collect(), keys);
                    } catch (Exception e) {
                        logger.error("Unable to process RDD", e);
                    }
                });

        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }

    private Authorization authorization() {
        Configuration configuration = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .build();
        return new OAuthAuthorization(configuration);
    }
}
