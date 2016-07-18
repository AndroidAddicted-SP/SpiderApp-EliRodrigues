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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Comics comics;
    private List<Comics> comicsList = new ArrayList<>();

    private boolean isRunning;

    @Override
    protected void onStart() {
        super.onStart();

        timeStamp = Utils.getTimeStamp();
        publicApiKey = getResources().getString(R.string.marvel_pulic_key);
        privateApiKey = BuildConfig.private_key;

        StringBuilder key = new StringBuilder();
        key.append(timeStamp);
        key.append(privateApiKey);
        key.append(publicApiKey);

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
        comics = new Comics();

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
        data.put("apikey", publicApiKey);


        Call<Model> call = service.getJsonComics(data);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                isRunning = false;
                Log.e(TAG, " eliete response raw = " + response.raw());
                if (response.isSuccessful()) {
                    try {
                        Comics.getComicListFromRetrofitJson(response);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    comicsList.addAll(Comics.getComicsList());
//                        listAdapter.notifyDataSetChanged();
                    Log.e(TAG, " eliete response list Comics = " + comicsList);
                    progressBar.setVisibility(View.GONE);
                }
            }


            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "eliete retrofit response error");
            }
        }) ;

    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
