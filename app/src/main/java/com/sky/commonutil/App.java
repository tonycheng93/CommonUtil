package com.sky.commonutil;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by tonycheng on 2017/4/24.
 */

public class App extends Application {

    private static Context mContext;

    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
