package com.msymobile.dotest.mvp.presenter;

import android.support.annotation.CallSuper;

import com.msymobile.dotest.mvp.view.BaseMVPView;


/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:
 */
public class BasePresenter<V extends BaseMVPView> {
    protected V view;

    @CallSuper
    public void attachView(V view) {
        this.view = view;
    }

    @CallSuper
    public void detachView() {
        view = null;
    }

    public V getView() {
        return view;
    }
}
