package com.msymobile.dotest.rxlifecycle;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description: presenterLifecycle
 */
public class RxLifeCyclePresenter {
    private static final Function<Integer,Integer> PRESENTER_LIFECYCLE = integer -> {
        switch (integer){
            //会在activity destroy,并发送了DETACH事件时 发射
            case PresenterEvent.ATTACH:
                return PresenterEvent.DETACH;
            case PresenterEvent.DETACH:
                throw new OutsideLifecycleException("Cannot bind to Presenter lifecycle when outside of it.");
            default:
                throw new UnsupportedOperationException("Binding to " + integer + " not yet implemented");
        }
    };

    private RxLifeCyclePresenter() {
        throw new AssertionError("RxLifeCyclePresenter can't be instances");
    }

    @NonNull
    public static <T>LifecycleTransformer<T> bindPresenter(@NonNull final Observable<Integer> lifecycle){
        return RxLifecycle.bind(lifecycle,PRESENTER_LIFECYCLE);
    }
}
