package com.challenge.spiderapp.data;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by eliete on 4/7/16.
 */
public class VolleySingleton {

    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;
    private static RetryPolicy mRetryPolicy;

    private VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);

        int memoryClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        //int cacheSize = 1024 * 1024 * memoryClass / 8;
        mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapCache(50));

        // Default timeout
        mRetryPolicy = new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static RetryPolicy getDefaultRetryPolicy() {
        return mRetryPolicy;
    }

    public class BitmapCache extends LruCache implements ImageLoader.ImageCache {
        public BitmapCache(int maxSize) {
            super(maxSize);
        }
        @Override
        public Bitmap getBitmap(String url) {
            return (Bitmap)get(url);
        }
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }
}
