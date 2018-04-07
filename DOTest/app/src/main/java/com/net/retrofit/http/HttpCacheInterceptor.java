package com.net.retrofit.http;


import com.msymobile.dotest.util.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtil.isNetworkAvailable()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkAvailable()) {
            String cacheControl = request.cacheControl().toString();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
        return response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=2419200")// int maxStale = 60 * 60 * 24 * 28;
                .removeHeader("Pragma")
                .build();
    }
}
