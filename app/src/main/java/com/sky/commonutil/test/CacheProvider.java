package com.sky.commonutil.test;

import com.sky.commonutil.http.core.HttpListResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

/**
 * Created by tonycheng on 2017/4/25.
 */

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Flowable<HttpListResult<GankEntity>> getGankList(Flowable<HttpListResult<GankEntity>> resultFlowable, EvictProvider evictProvider);
}
