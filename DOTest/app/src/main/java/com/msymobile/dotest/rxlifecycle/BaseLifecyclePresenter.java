package com.msymobile.dotest.rxlifecycle;

import com.msymobile.dotest.mvp.presenter.BasePresenter;
import com.msymobile.dotest.mvp.view.BaseMVPView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class BaseLifecyclePresenter<V extends BaseMVPView> extends BasePresenter<V> implements LifecycleProvider<Integer>{

    private final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    @Override
    public Observable<Integer> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(Integer event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject,event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifeCyclePresenter.bindPresenter(lifecycleSubject);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    @Override
    public void detachView() {
        super.detachView();
        lifecycleSubject.onNext(PresenterEvent.DETACH);
    }
}
