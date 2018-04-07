package com;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.msymobile.dotest.util.ContextUtil;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
