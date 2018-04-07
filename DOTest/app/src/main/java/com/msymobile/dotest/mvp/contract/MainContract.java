package com.msymobile.dotest.mvp.contract;

import com.msymobile.dotest.entity.FixerEntity;
import com.msymobile.dotest.mvp.mode.BaseMode;
import com.msymobile.dotest.mvp.view.BaseMVPView;

import io.reactivex.Observable;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:
 */
public interface MainContract {

    interface IMainView extends BaseMVPView {
        void fixerInfo(FixerEntity entity);
    }

    interface IMainMode extends BaseMode {
        Observable<FixerEntity> requestFixer();
    }

}
