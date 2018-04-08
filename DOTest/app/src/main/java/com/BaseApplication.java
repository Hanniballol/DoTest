package com;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.msymobile.dotest.util.ContextUtil;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.init(this);
        Stetho.initializeWithDefaults(this);
        RxJavaPlugins.setErrorHandler(throwable -> Log.e("hannibal", "throw test" + throwable.getMessage()));
    }
}
