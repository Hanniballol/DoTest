package com.msymobile.dotest.util;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * autour: hannibal
 * date: 2017/12/27
 * e-mail:404769122@qq.com
 * description:
 */
public class RxClick {
    public static void click(View view, Consumer consumer) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).subscribe(consumer);
    }

    public static void longClick(View view,Consumer consumer) {
        RxView.longClicks(view).throttleFirst(1,TimeUnit.SECONDS).subscribe(consumer);
    }
}
