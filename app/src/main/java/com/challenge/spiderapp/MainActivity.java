package com.challenge.spiderapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.challenge.spiderapp.Utils.Utils;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Response.Listener<String>, Response.ErrorListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://gateway.marvel.com:80/v1/public/characters/1009610/comics";

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.gridView)
    GridView gridView;

    private String md5Hash;
    private Long timeStamp;
    private String publicApiKey;
    private String privateApiKey;
    private List<Results> resultsList = new ArrayList<>();
    private boolean isRunning;
    private ResultsGridAdapter comicsGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        comicsGridAdapter = new ResultsGridAdapter(this, resultsList);
        gridView.setAdapter(comicsGridAdapter);

        timeStamp = Utils.getTimeStamp();
        publicApiKey = getResources().getString(R.string.marvel_pulic_key);
        privateApiKey = BuildConfig.private_key;

        StringBuilder key = new StringBuilder();
        key.append(timeStamp);
        key.append(privateApiKey);
        key.append(publicApiKey);

        md5Hash = Utils.createMd5Hash(key.toString());

        if (MainActivity.hasConnection(this)) {
            if (!isRunning){
                progressBar.setVisibility(View.VISIBLE);
                initDownloadRetrofit();
            }
        }
    }

    private void initDownloadRetrofit() {
        isRunning = true;
        progressBar.setVisibility(View.VISIBLE);
        String param = String.valueOf(timeStamp);

        StringBuilder url = new StringBuilder();
        url.append(BASE_URL);
        url.append("?ts=");
        url.append(param);
        url.append("&apikey=");
        url.append(publicApiKey);
        url.append("&hash=");
        url.append(md5Hash);

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), this, this);
        request.setRetryPolicy(VolleySingleton.getDefaultRetryPolicy());
        queue.add(request);

    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }

    public void showSnackBar(String message) {
        Snackbar.make(this.findViewById(android.R.id.content), message,
                Snackbar.LENGTH_SHORT)
                .setActionTextColor(Color.WHITE)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectDrawerItem(item);
        return true;
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Intent intent = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.nav_aboutme:

                break;
        }

        if (intent != null)
            startActivity(intent);

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        isRunning = false;
        Log.e(TAG, "volley response error");
        showSnackBar(getResources().getString(R.string.error_msg));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(String response) {
        isRunning = true;
        try {
            Log.e(TAG, "eliete" + response);
            Results.getResultsListFromJson(response);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException j) {
            j.printStackTrace();
        }
        resultsList.addAll(Results.getResultsList());
        comicsGridAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}
