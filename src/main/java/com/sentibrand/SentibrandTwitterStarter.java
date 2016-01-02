package com.sentibrand;

import com.sentibrand.twitterdata.TwitterDataStream;

public class SentibrandTwitterStarter {
    public static void main(String... args) {
        new TwitterDataStream().openAndProcessStream(args);
    }
}
