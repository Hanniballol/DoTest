package com.msymobile.dotest.mvp.contract;

import com.msymobile.dotest.entity.IconXmlEntity;
import com.msymobile.dotest.mvp.mode.BaseMode;
import com.msymobile.dotest.mvp.view.BaseMVPView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


/**
 * autour: hannibal
 * date: 2018/4/7
 * e-mail:404769122@qq.com
 * description:
 */
public interface IconConstract {
    interface IIconView extends BaseMVPView{
        void iconInfo(IconXmlEntity urlEntity);
    }
    interface IIconMode extends BaseMode{
         Observable<ResponseBody> requestIcon(String url);
    }
}
