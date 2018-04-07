package com.msymobile.dotest.mvp.mode;

import com.msymobile.dotest.entity.FixerEntity;
import com.msymobile.dotest.mvp.contract.MainContract;
import com.msymobile.dotest.net.RetrofitSington;
import com.net.retrofit.util.AddLifecycle;

import io.reactivex.Observable;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:
 */
public class MainMode implements MainContract.IMainMode {


    @Override
    public Observable<FixerEntity> requestFixer() {
        return RetrofitSington.getInstance()
                .getApi()
                .requestFixer()
                .compose(AddLifecycle.toSchedulers());
    }
}
