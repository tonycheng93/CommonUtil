package com.sky.commonutil.test;

import com.sky.commonutil.http.core.HttpListResult;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tonycheng on 2017/4/25.
 */

public interface GankHttpService {

    @GET("data/Android/{size}/{page}")
    Flowable<HttpListResult<GankEntity>> getGankList(@Path("size") int size, @Path("page") int page);
}
