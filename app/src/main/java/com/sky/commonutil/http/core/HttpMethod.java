package com.sky.commonutil.http.core;

import com.google.gson.Gson;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sky.commonutil.http.cache.ICache;
import com.sky.commonutil.http.cache.impl.FileCache;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tonycheng on 2017/4/24.
 */

public abstract class HttpMethod<T> {

    private static final String TAG = "HttpMethod";

    private T mService = null;

    public abstract String getBaseUrl();//基地址

    public abstract int getTimeOut();//超时时间

    public abstract Class<T> getServiceClazz();

    //头信息
    public Map<String, String> getHeaders() {
        return null;
    }

    public ICache mDiskCache = new FileCache();

    //支持自定义Gson
    public Gson getGson() {
        return null;
    }

    //支持自定义OkHttpClient
    public OkHttpClient getOkHttpClient() {
        return null;
    }

    protected HttpMethod() {
        Retrofit retrofit = null;
        if (getGson() == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient() == null ? getClient() : getOkHttpClient())
                    .baseUrl(getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient() == null ? getClient() : getOkHttpClient())
                    .baseUrl(getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }


        mService = retrofit.create(getServiceClazz());
    }

    private OkHttpClient getClient() {
        final Map<String, String> headers = getHeaders();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (headers != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request.Builder requestBuilder = chain.request().newBuilder();
                    final Iterator iterator = headers.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        requestBuilder.addHeader((String) entry.getKey(), (String) entry.getValue());
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
            HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
            logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logginInterceptor);
        }
        builder.connectTimeout(getTimeOut(), TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    protected T getService() {
        return mService;
    }

    protected <E> Function<HttpResult<E>, E> map(Class<E> clazz) {
        return new Function<HttpResult<E>, E>() {
            @Override
            public E apply(@NonNull HttpResult<E> eHttpResult) throws Exception {
                if (eHttpResult != null) {
                    if (eHttpResult.error) {
                        if (eHttpResult.results != null) {
                            return eHttpResult.results;
                        }
                    } else {
                        throw new HttpException("there is something wrong !");
                    }
                }
                return null;
            }
        };
    }

    protected <E> Function<HttpListResult<E>, List<E>> mapList(Class<E> clazz) {
        return new Function<HttpListResult<E>, List<E>>() {
            @Override
            public List<E> apply(@NonNull HttpListResult<E> eHttpListResult) throws Exception {
                if (eHttpListResult != null) {
                    if (eHttpListResult.results != null) {
                        return eHttpListResult.results;
                    }
                }
                return null;
            }
        };
    }

    protected <E> Function<HttpResult<E>, HttpResult<E>> cache(final Class<E> clazz, final String key) {
        return new Function<HttpResult<E>, HttpResult<E>>() {
            @Override
            public HttpResult<E> apply(@NonNull HttpResult<E> eHttpResult) throws Exception {
                if (eHttpResult != null) {
                    if (eHttpResult.results != null) {
                        E data = mDiskCache.get(key, clazz);
                        if (eHttpResult.results instanceof Comparable) {
                            if (((Comparable) eHttpResult.results).compareTo(data) != 0) {
                                mDiskCache.put(key, eHttpResult.results);
                            } else {
                                return null;
                            }
                        } else {
                            if (!eHttpResult.results.equals(data)) {
                                mDiskCache.put(key, eHttpResult.results);
                            } else {
                                return null;
                            }
                        }
                    }
                }
                return eHttpResult;
            }
        };
    }

    public <E> Flowable<HttpResult<E>> diskCache(final Class<E> clazz, final String key) {
        return Flowable.create(new FlowableOnSubscribe<HttpResult<E>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<HttpResult<E>> e) throws Exception {
                HttpResult<E> result = new HttpResult<E>();
                result.results = mDiskCache.get(key, clazz);
                e.onNext(result);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    public <T> T getCache(String key, Class<T> clazz) {
        T data = mDiskCache.get(key, clazz);
        return data;
    }
}
