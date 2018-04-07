package com.msymobile.dotest.util;

import com.google.gson.Gson;

/**
 * autour: hannibal
 * date: 2017/12/27
 * e-mail:404769122@qq.com
 * description:
 */
public class GsonUtil {
    public static String bean2String(Object object) {
        return new Gson().toJson(object);
    }
}
