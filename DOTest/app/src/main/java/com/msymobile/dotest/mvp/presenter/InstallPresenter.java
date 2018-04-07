package com.msymobile.dotest.mvp.presenter;


import com.msymobile.dotest.rxlifecycle.BaseLifecyclePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
@Inherited //表示可以被继承
@Retention(RetentionPolicy.RUNTIME)  //可以在运行时通过反射拿到
public @interface InstallPresenter {
    Class<? extends BaseLifecyclePresenter> presenterObject();
}
