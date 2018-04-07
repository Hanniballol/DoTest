package com.msymobile.dotest.net;

import com.msymobile.dotest.entity.FixerEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public interface ApiInterface {
    String HOST_RELEASE = "https://api.fixer.io/";

    static String HOST() {
        return HOST_RELEASE;
    }

    @GET("latest")
    Observable<FixerEntity> requestFixer();

    @Headers({
            "Mozilla:5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X)",
            "AppleWebKit:603.1.30 (KHTML, like Gecko)",
            "Version:10.3",
            "Mobile:14E277",
            "Safari:603.1.30"
    })
    @GET()
    Observable<ResponseBody> requestIcon(@Url String url);
}

