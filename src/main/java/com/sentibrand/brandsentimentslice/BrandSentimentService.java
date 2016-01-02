package com.sentibrand.brandsentimentslice;

import com.sentibrand.twitterdata.TwitterData;
import com.sentibrand.twitterdata.TwitterDataClient;

import java.io.Serializable;
import java.util.List;

public class BrandSentimentService implements Serializable {

    private final BrandSentimentSliceFactory brandSentimentSliceFactory;
    private final BrandSentimentSliceClient brandSentimentSliceClient;
    private final TwitterDataClient twitterDataClient;

    public BrandSentimentService() {
        this.brandSentimentSliceFactory = new BrandSentimentSliceFactory();
        this.brandSentimentSliceClient = new BrandSentimentSliceClient();
        this.twitterDataClient = new TwitterDataClient();
    }

    public void createAndSave(List<TwitterData> twitterDatas, String... keys) {
        twitterDatas.stream()
                .mapToDouble(TwitterData::getSentiment)
                .average()
                .ifPresent(averageSentiment -> {
                    BrandSentimentSlice brandSentimentSlice = brandSentimentSliceFactory.create(averageSentiment, keys);
                    brandSentimentSliceClient.save(brandSentimentSlice);
                    twitterDataClient.save(twitterDatas);
                });
    }
}
