package com.challenge.spiderapp;

/**
 * Created by eliete on 7/14/16.
 */
public class Thumbnail {

    String path;
    String extensions;

    @Override
    public String toString() {
        return path + "/portrait_small." + extensions;
    }
}