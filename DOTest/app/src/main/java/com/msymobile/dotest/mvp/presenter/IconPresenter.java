package com.msymobile.dotest.mvp.presenter;

import android.util.Log;

import com.msymobile.dotest.entity.IconXmlEntity;
import com.msymobile.dotest.mvp.contract.IconConstract;
import com.msymobile.dotest.mvp.mode.IconMode;
import com.msymobile.dotest.net.RetrofitSington;
import com.msymobile.dotest.rxlifecycle.BaseLifecyclePresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

import static com.msymobile.dotest.util.XmlParser.xmlParser;

/**
 * autour: hannibal
 * date: 2018/4/7
 * e-mail:404769122@qq.com
 * description:
 */
public class IconPresenter extends BaseLifecyclePresenter<IconConstract.IIconView> {
    private IconMode mIconMode;

    public IconPresenter() {
        mIconMode = new IconMode();
    }

    public void getIcon(String url) {
        getView().getContext().showWaitingDialog();
        mIconMode.requestIcon(url)
                .compose(bindToLifecycle())
                .flatMap((Function<ResponseBody, ObservableSource<?>>) responseBody -> {
                    String s = xmlParser(responseBody);
                    return Observable.just(new IconXmlEntity(s));
                })
                .doOnError(throwable -> {
                    RetrofitSington.disposeFailureInfo(throwable);
                    getView().getContext().hideWaitingDialog();
                    Log.e("hannibal", throwable.getMessage());
                })
                .subscribe(o -> getView().iconInfo((IconXmlEntity)o));
    }
}
