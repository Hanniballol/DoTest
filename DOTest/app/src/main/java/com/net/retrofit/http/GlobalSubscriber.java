package com.net.retrofit.http;

import com.msymobile.dotest.util.ToastUtil;
import com.net.retrofit.exception.ExceptionHandle;

import io.reactivex.Observer;

/**
 * autour: hannibal
 * date: 2017/12/27
 * e-mail:404769122@qq.com
 * description:
 */
public abstract class GlobalSubscriber<T> implements Observer<T> {

    public GlobalSubscriber() {
    }

    @Override
    public void onError(Throwable e) {
        ExceptionHandle.ResponseThrowable responseThrowable = ExceptionHandle.handleException(e);
        ToastUtil.makeText(responseThrowable.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
