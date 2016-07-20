package com.challenge.spiderapp.AboutMe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.challenge.spiderapp.R;
import com.challenge.spiderapp.data.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eliete on 7/19/16.
 */
public class AboutMeActivity extends AppCompatActivity implements AboutMeContract.View {

    @BindView(R.id.textViewEmail)
    TextView emailTextView;
    @BindView(R.id.textViewVersion)
    TextView versionTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private AboutMeContract.UserActionListener userActionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        ButterKnife.bind(this);

        userActionListener = new AboutMePresenter(this, Injection.provideComicsRepository());
        userActionListener.fetchAppVersion();

    }

    @OnClick (R.id.textViewEmail) public void sendEmail(){
        userActionListener.sendEmail();

    }

    @Override
    public void showSendEmail(String message, String[] email) {
        hideProgress();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void showAppVersion(String version) {
        versionTextView.setText(version);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userActionListener.onDestroy();
    }
}
