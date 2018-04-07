package com.msymobile.dotest.util;

import android.util.Log;

import com.msymobile.dotest.BuildConfig;


/**
 * autour: hannibal
 * date: 2017/12/27
 * e-mail:404769122@qq.com
 * description:
 */
public class LogUtil {

    private static final String TAG = "Hanniballol";

    public static void e(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.e(key, value);
        }
    }

    public static void d(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.d(key, value);
        }
    }

    public static void e(String value) {
        e(TAG, value);
    }

    public static void d(String value) {
        d(TAG, value);
    }
}
