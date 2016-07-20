package com.challenge.spiderapp.AboutMe;

import com.challenge.spiderapp.data.ComicsRepository;

/**
 * Created by eliete on 7/19/16.
 */
public class AboutMePresenter implements AboutMeContract.UserActionListener {

    private AboutMeContract.View aboutMeContract;
    private ComicsRepository comicsRepository;

    public AboutMePresenter(AboutMeActivity activity, ComicsRepository repository) {
        this.aboutMeContract = activity;
        this.comicsRepository = repository;
    }

    @Override
    public void sendEmail() {
        aboutMeContract.showProgress();
        aboutMeContract.showSendEmail("contato app Spider Man", new String[]{"dsreliete@gmail.com"});
    }

    @Override
    public void onDestroy() {
        aboutMeContract = null;

    }

    @Override
    public void fetchAppVersion() {
       String version = comicsRepository.getAppVersion((AboutMeActivity) aboutMeContract);
        aboutMeContract.showAppVersion(version);
    }


}
