package com.challenge.spiderapp.ComicsDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.challenge.spiderapp.R;

import butterknife.ButterKnife;

/**
 * Created by eliete on 7/19/16.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comics);

        ButterKnife.bind(this);
    }
}
