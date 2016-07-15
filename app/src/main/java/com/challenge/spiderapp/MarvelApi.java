package com.challenge.spiderapp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by eliete on 7/14/16.
 */
public interface MarvelApi {

    public static final String BASE_URL ="http://gateway.marvel.com:80";

    @GET("/v1/public/characters/1009610/comics")
    Call<Model> getJsonComics(@QueryMap Map<String, String> options);
}
