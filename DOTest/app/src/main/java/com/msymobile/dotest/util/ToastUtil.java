package com.msymobile.dotest.util;

import android.widget.Toast;

/**
 * autour: hannibal
 * date: 2017/12/27
 * e-mail:404769122@qq.com
 * description:
 */
public class ToastUtil {
    public static void makeText(String message) {
        Toast.makeText(ContextUtil.getContext(),message,Toast.LENGTH_SHORT).show();
    }

    public static void makeTextLong(String message) {
        Toast.makeText(ContextUtil.getContext(),message,Toast.LENGTH_LONG).show();
    }
}
