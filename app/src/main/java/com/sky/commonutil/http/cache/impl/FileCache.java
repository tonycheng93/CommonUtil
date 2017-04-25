package com.sky.commonutil.http.cache.impl;

import com.google.gson.Gson;

import android.text.TextUtils;

import com.sky.commonutil.App;
import com.sky.commonutil.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import timber.log.Timber;

/**
 * Created by tonycheng on 2017/4/24.
 */

public class FileCache extends Cache {

    private static final String TAG = "FileCache";

    public static final String CACHE_DIR = App.getInstance().getFilesDir() + File.separator + "http_cache/";

    public FileCache() {
        File file = new File(CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public synchronized <T> T get(String key, Class<T> clazz) {
        T t = super.get(key, clazz);
        if (t != null) {
            return t;
        }

        try {
            long start = System.currentTimeMillis();
            File file = new File(CACHE_DIR + key);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder builder = new StringBuilder();
                String txt = "";
                while ((txt = reader.readLine()) != null) {
                    builder.append(txt);
                }
                String value = builder.toString();
                Timber.d(TAG, "get data time " + key + " " + (System.currentTimeMillis() - start));
                if (!TextUtils.isEmpty(value)) {
                    final T data = new Gson().fromJson(value, clazz);
                    super.put(key, data);
                    return data;
                }
            }
        } catch (FileNotFoundException e) {
            Timber.e(TAG, "FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            Timber.e(TAG, "IOException: " + e.toString());
        } catch (NullPointerException e) {
            Timber.e(TAG, "NullPointerException: " + e.toString());
        }
        return null;
    }

    @Override
    public synchronized <T> void put(String key, T t) {
        try {
            FileUtils.stringToFile(CACHE_DIR + key, new Gson().toJson(t));
        } catch (IOException e) {
            Timber.e(TAG, "IOException: " + e.toString());
        }
        super.put(key, t);
    }
}
