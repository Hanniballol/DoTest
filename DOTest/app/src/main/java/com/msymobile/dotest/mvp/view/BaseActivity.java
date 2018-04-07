package com.msymobile.dotest.mvp.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.msymobile.dotest.mvp.presenter.InstallPresenter;
import com.msymobile.dotest.rxbus.RxBus;
import com.msymobile.dotest.rxlifecycle.BaseLifecyclePresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public abstract class BaseActivity<P extends BaseLifecyclePresenter> extends RxAppCompatActivity implements BaseMVPView {

    private P mPresenter;

    private MaterialDialog mMaterialDialog;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData(Bundle saveInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData(savedInstanceState);
        new Thread(() -> {
            //放到子线程去做方法读取操作和订阅操作，因为在类方法多的时候，反射会存在效率问题，在主线程做的话
            RxBus.get().register(BaseActivity.this);
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(BaseActivity.this);
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    public P getPresenter() {
        if (null == mPresenter) {
            InstallPresenter annotation = getClass().getAnnotation(InstallPresenter.class);
            if (null == annotation)
                throw new RuntimeException("@InstallPresenter(presenterObject =  xx.class)注解,没有声明");
            Class<P> aClass = (Class<P>) annotation.presenterObject();
            mPresenter = createPresenter(aClass);
        }
        return mPresenter;
    }

    private P createPresenter(Class<P> presenterClass) {
        try {
            mPresenter = presenterClass.newInstance();
            mPresenter.attachView(this);
            return mPresenter;
        } catch (Exception e) {
            throw new RuntimeException("@InstallPresenter(presenterObject = xx.class)注解,没有声明");
        }
    }

    @Override
    public BaseActivity<P> getContext() {
        return this;
    }

    public void showWaitingDialog() {
        hideWaitingDialog();
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setMessage("正在请求数据...");
        mMaterialDialog.show();
    }

    /**
     * 隐藏等待提示框
     */
    public void hideWaitingDialog() {
        if (null != mMaterialDialog) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }
}
