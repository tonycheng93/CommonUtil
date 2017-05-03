package com.sky.commonutil.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.sky.commonutil.http.core.HttpMethod;
import com.sky.commonutil.model.GankEntity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 * Created by tonycheng on 2017/4/25.
 */

public class GankHttpMethod extends HttpMethod<GankHttpService> {

    private static final String TAG = "GankHttpMethod";

    private GankHttpMethod() {
    }

    private static class Holder {
        static GankHttpMethod instance = new GankHttpMethod();
    }

    public static GankHttpMethod getInstance() {
        return Holder.instance;
    }

    @Override
    public String getBaseUrl() {
        return "http://gank.io/api/";
    }

    @Override
    public int getTimeOut() {
        return 10;
    }

    @Override
    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    @Override
    public Class<GankHttpService> getServiceClazz() {
        return GankHttpService.class;
    }

    public void getGankList(DefaultSubscriber<List<GankEntity>> subscriber, int size, int page) {
        getService().getGankList(size, page)
                .subscribeOn(Schedulers.io())
                .map(mapList(GankEntity.class))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
