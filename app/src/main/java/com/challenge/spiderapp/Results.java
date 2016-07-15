package com.challenge.spiderapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eliete on 7/14/16.
 */
public class Results {
    @SerializedName("thumbnail")
    Thumbnail thumbnail;

    String issue;
    String title;
    String description;
    String pageCount;

    @SerializedName("dates")
    List<Date> dateList;

    @SerializedName("prices")
    List<Price> priceList;

    @Override
    public String toString() {
        return "Results{" +
                "thumbnail=" + thumbnail +
                ", issue='" + issue + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", dateList=" + dateList +
                ", priceList=" + priceList +
                '}';
    }
}
