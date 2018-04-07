package com.msymobile.dotest.util;

import android.content.Context;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class ContextUtil {
    private static Context sContext;

    private ContextUtil() {
        throw new UnsupportedOperationException("you can't instantiate ContextUtil");
    }

    public static void init(Context context) {
        ContextUtil.sContext = context.getApplicationContext();
    }

    public static Context getContext() {
        if (null == sContext) throw new NullPointerException("ContextUtil should init first");
        return sContext;
    }
}
