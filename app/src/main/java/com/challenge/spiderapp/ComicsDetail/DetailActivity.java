package com.challenge.spiderapp.ComicsDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.challenge.spiderapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eliete on 7/19/16.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comics);

        ButterKnife.bind(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
