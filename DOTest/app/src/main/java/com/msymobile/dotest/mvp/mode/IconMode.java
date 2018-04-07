package com.msymobile.dotest.mvp.mode;

import com.msymobile.dotest.mvp.contract.IconConstract;
import com.msymobile.dotest.net.RetrofitSington;
import com.net.retrofit.util.AddLifecycle;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * autour: hannibal
 * date: 2018/4/7
 * e-mail:404769122@qq.com
 * description:
 */
public class IconMode implements IconConstract.IIconMode {
    @Override
    public Observable<ResponseBody> requestIcon(String url) {
        return RetrofitSington
                .getInstance()
                .getApi()
                .requestIcon(url)
                .compose(AddLifecycle.toSchedulers());
    }
}
