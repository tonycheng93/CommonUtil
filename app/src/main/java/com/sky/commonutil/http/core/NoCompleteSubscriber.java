package com.sky.commonutil.http.core;

import io.reactivex.subscribers.DefaultSubscriber;

/**
 * Created by tonycheng on 2017/4/23.
 */

public abstract class NoCompleteSubscriber<T> extends DefaultSubscriber<T> {
    @Override
    public void onComplete() {

    }
}
