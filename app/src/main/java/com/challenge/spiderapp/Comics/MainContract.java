package com.challenge.spiderapp.Comics;

import android.support.annotation.NonNull;

import com.challenge.spiderapp.data.Results;

import java.util.List;

/**
 * Created by eliete on 7/19/16.
 */
public interface MainContract {

    interface View{

        void showProgress();

        void hideProgress();

        void showListItens(List<Results> list);

        void showItemDetailActivity(Results item);
    }

    interface UserActionListener{

        void fetchComicsList();

        void onDestroy();

        void openItemDetails(@NonNull Results requestedNote);
    }
}
