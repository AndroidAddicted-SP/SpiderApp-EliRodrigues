package com.challenge.spiderapp.data;

import com.challenge.spiderapp.AboutMe.AboutMeActivity;

import java.util.List;

/**
 * Created by eliete on 7/19/16.
 */
public interface ComicsRepository {

    interface getListOnFinishedListener {
        void onFinishedList(List<Results> result);
    }

    interface getItemOnFinishedListener{
        void onFinishedItem(Results result);
    }

    void fetchComics(getListOnFinishedListener listener);

    void getComic(Results result, getItemOnFinishedListener listener);

    String getAppVersion(AboutMeActivity activity);
}

