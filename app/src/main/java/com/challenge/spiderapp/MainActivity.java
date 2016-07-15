package com.challenge.spiderapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.challenge.spiderapp.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String md5Hash;
    private Long timeStamp;
    private String publicApiKey;
    private String privateApiKey;

    private boolean isRunning;

    @Override
    protected void onStart() {
        super.onStart();

        timeStamp = Utils.getTimeStamp();
        publicApiKey = getResources().getString(R.string.marvel_pulic_key);
        privateApiKey = BuildConfig.private_key;

        StringBuilder key = new StringBuilder();
        key.append(timeStamp);
        key.append(privateApiKey); //private
        key.append(publicApiKey); //public

       md5Hash = Utils.createMd5Hash(key.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MainActivity.hasConnection(this)) {
            if (!isRunning){
                progressBar.setVisibility(View.VISIBLE);
                initDownloadRetrofit();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    private void initDownloadRetrofit() {
        isRunning = true;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MarvelApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarvelApi service = retrofit.create(MarvelApi.class);

        Map<String, String> data = new HashMap<>();
        data.put("ts", String.valueOf(timeStamp));
        data.put("hash", md5Hash);
        data.put("apikey", "ea36b99d73d53acd825eb59d5a0b7231");


        Call<Model> call = service.getJsonComics(data);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                isRunning = false;

                Log.e(TAG, " eliete response raw = " + response.raw());
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Comics comics = new Comics();
                    comics.setAttributionTest(response.body().attributionTest);
                    comics.setResults(response.body().data.resultsList);

                    Log.e(TAG, "eliete comics " + comics.toString());

                }
            }


            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "eliete retrofit response error");
            }
        });

    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
