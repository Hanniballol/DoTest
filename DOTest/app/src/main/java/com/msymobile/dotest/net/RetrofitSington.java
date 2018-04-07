package com.msymobile.dotest.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.msymobile.dotest.util.FileUtil;
import com.net.retrofit.exception.ExceptionHandle;
import com.net.retrofit.http.HttpCacheInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public class RetrofitSington {

    private static final String NET_CACHE = "cache";
    private static OkHttpClient sOkHttpClient;
    private static Retrofit sRetrofit;
    private static ApiInterface sApiServie;

    private RetrofitSington() {
        initOkHttp();
        initRetrofit();
        sApiServie = sRetrofit.create(ApiInterface.class);
    }

    private static class SingletonHolder {

        private static final RetrofitSington INSTANCE = new RetrofitSington();
    }

    public static RetrofitSington getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void initRetrofit() {
        sRetrofit = new Retrofit.Builder()
                .client(sOkHttpClient)
                .baseUrl(ApiInterface.HOST())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void initOkHttp() {
        sOkHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(FileUtil.getNetCahceDir(), 1024 * 1024 * 100))
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .build();
    }

    public ApiInterface getApi() {
        return sApiServie;
    }

    public static Consumer<Throwable> disposeFailureInfo(Throwable t) {
        return throwable -> ExceptionHandle.handleException(t);
    }
}
