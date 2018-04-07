package com.net.retrofit.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class AddLifecycle {
    public static <T> ObservableTransformer<T, T> toSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
