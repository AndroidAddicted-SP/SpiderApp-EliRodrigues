package com.challenge.spiderapp.data;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.challenge.spiderapp.AboutMe.AboutMeActivity;
import com.challenge.spiderapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eliete on 7/19/16.
 */
public class ComicsRepositoryImpl implements ComicsRepository {
    @Override
    public void fetchComics(getListOnFinishedListener listener) {

    }

    @Override
    public void getComic(Results result, getItemOnFinishedListener listener) {

    }

    @Override
    public String getAppVersion(AboutMeActivity activity) {
        StringBuilder sb = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());
        PackageInfo pInfo;

        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            String version = pInfo.versionName;
            sb = new StringBuilder(activity.getResources().getString(R.string.simbol_rights));
            sb.append(" ");
            sb.append(year);
            sb.append(" ");
            sb.append(activity.getResources().getString(R.string.app_name));
            sb.append(" ");
            sb.append(activity.getResources().getString(R.string.name_rights));
            sb.append(" ");
            sb.append(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

         return sb.toString();
    }
}
