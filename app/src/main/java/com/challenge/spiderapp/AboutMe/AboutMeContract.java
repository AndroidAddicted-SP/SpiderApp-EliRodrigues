package com.challenge.spiderapp.AboutMe;

/**
 * Created by eliete on 7/19/16.
 */
public interface AboutMeContract {

    interface View{

        void showSendEmail(String message, String[] email);

        void showAppVersion(String version);

        void showProgress();

        void hideProgress();
    }

    interface UserActionListener{

        void sendEmail();

        void onDestroy();

        void fetchAppVersion();
    }
}
