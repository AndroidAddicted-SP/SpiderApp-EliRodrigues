package com.challenge.spiderapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eliete on 7/14/16.
 */
public class Data {

    @SerializedName("results")
    List<Results> resultsList;
}
