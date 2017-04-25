package com.sky.commonutil.http.cache.impl;

import com.sky.commonutil.http.cache.ICache;

import java.util.HashMap;

/**
 * Created by tonycheng on 2017/4/24.
 */

public abstract class Cache implements ICache {

    private HashMap<String, Object> cache = new HashMap<>();

    @Override
    public synchronized <T> T get(String key, Class<T> clazz) {
        try {
            return (T) cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized <T> void put(String key, T t) {
        cache.put(key, t);
    }
}
