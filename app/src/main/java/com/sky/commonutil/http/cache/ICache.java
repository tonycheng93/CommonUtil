package com.sky.commonutil.http.cache;

/**
 * Created by tonycheng on 2017/4/24.
 */

public interface ICache {
    <T> T get(String key, Class<T> clazz);

    <T> void put(String key, T t);
}
