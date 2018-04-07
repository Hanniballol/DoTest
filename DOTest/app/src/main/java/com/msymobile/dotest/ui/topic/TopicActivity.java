package com.msymobile.dotest.ui.topic;

import android.os.Bundle;
import android.widget.Button;

import com.msymobile.dotest.R;
import com.msymobile.dotest.mvp.contract.TopicContract;
import com.msymobile.dotest.mvp.presenter.InstallPresenter;
import com.msymobile.dotest.mvp.presenter.TopicPresenter;
import com.msymobile.dotest.mvp.view.BaseActivity;
import com.msymobile.dotest.ui.icon.IconActivity;
import com.msymobile.dotest.ui.main.MainActivity;
import com.msymobile.dotest.util.RxClick;

import butterknife.BindView;

/**
 * autour: hannibal
 * date: 2018/4/7
 * e-mail:404769122@qq.com
 * description:
 */

@InstallPresenter(presenterObject = TopicPresenter.class)
public class TopicActivity extends BaseActivity<TopicPresenter> implements TopicContract.ITopicView {

    @BindView(R.id.request_fixer_bt)
    Button fixBtn;
    @BindView(R.id.request_icon_bt)
    Button iconBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic;
    }

    @Override
    protected void initView() {
        RxClick.click(fixBtn, o -> MainActivity.newInstance(this));
        RxClick.click(iconBtn, o -> IconActivity.newInstance(this));
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }
}
