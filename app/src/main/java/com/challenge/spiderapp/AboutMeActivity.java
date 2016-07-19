package com.challenge.spiderapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by eliete on 7/19/16.
 */
public class AboutMeActivity extends AppCompatActivity {

    @BindView(R.id.imageViewEmail)
    ImageView emailImageView;
    @BindView(R.id.imageViewGithub)
    ImageView githubImageView;
    @BindView(R.id.imageViewLinkedin)
    ImageView linkedinImageView;
    @BindView(R.id.textView)
    TextView developedbyTextView;
    @BindView(R.id.textViewVersion)
    TextView versionTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());

        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            StringBuilder sb = new StringBuilder(getResources().getString(R.string.simbol_rights));
            sb.append(" ");
            sb.append(year);
            sb.append(" ");
            sb.append(getResources().getString(R.string.app_name));
            sb.append(" ");
            sb.append(getResources().getString(R.string.name_rights));
            sb.append(" ");
            sb.append(version);
            versionTextView.setText(sb.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
