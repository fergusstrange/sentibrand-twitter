package com.sentibrand.brandsentimentslice;

import java.io.Serializable;
import java.time.ZonedDateTime;

import static java.util.Arrays.asList;

public class BrandSentimentSliceFactory  implements Serializable {
    public BrandSentimentSlice create(double averageSentiment, String... keys) {
        return new BrandSentimentSlice(ZonedDateTime.now(),
                averageSentiment,
                asList(keys));
    }
}
