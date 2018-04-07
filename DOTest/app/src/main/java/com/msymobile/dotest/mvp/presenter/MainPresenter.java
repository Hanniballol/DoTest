package com.msymobile.dotest.mvp.presenter;

import android.util.Log;

import com.msymobile.dotest.entity.FixerEntity;
import com.msymobile.dotest.mvp.contract.MainContract;
import com.msymobile.dotest.mvp.mode.MainMode;
import com.msymobile.dotest.net.RetrofitSington;
import com.msymobile.dotest.rxlifecycle.BaseLifecyclePresenter;
import com.msymobile.dotest.util.SortUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:
 */
public class MainPresenter extends BaseLifecyclePresenter<MainContract.IMainView> {
    private MainMode mMainMode;

    public MainPresenter() {
        mMainMode = new MainMode();
    }

    public void getFixer() {
        getView().getContext().showWaitingDialog();
        mMainMode.requestFixer()
                .compose(bindToLifecycle())
                .doOnError(
                        throwable -> {
                            RetrofitSington.disposeFailureInfo(throwable);
                            getView().getContext().hideWaitingDialog();
                            Log.e("hannibal", throwable.getMessage());
                        }
                )
                .flatMap((Function<FixerEntity, ObservableSource<?>>) entity -> Observable.just(sortEntity(entity)))
                .subscribe(fixerEntity -> MainPresenter.this.getView().fixerInfo((FixerEntity) fixerEntity));
    }

    private FixerEntity sortEntity(FixerEntity entity) {
        //本地排序数据
        String[] localList = SortUtil.getSortData();
        //处理后数据
        List<FixerEntity.ItemInfo> newlist = new ArrayList<>();
        //网络数据
        List<FixerEntity.ItemInfo> rates = entity.getRateList();
        List<String> netList = new ArrayList<>(rates.size());
        for (FixerEntity.ItemInfo info : rates) {
            netList.add(info.getFixerName());
        }
        //网络数据计1
        Map<String, Integer> map = new HashMap<>(netList.size());
        for (String str : netList) {
            map.put(str, 1);
        }
        //相同数据计2，按本地排序添加
        for (String str : localList) {
            if (map.get(str) != null) {
                map.put(str, 2);
                newlist.add(rates.get(netList.indexOf(str)));
            }
        }
        //添加网络新增数据
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                newlist.add(rates.get(netList.indexOf(entry.getKey())));
            }
        }
        entity.setRateList(newlist);
        return entity;

    }
}
