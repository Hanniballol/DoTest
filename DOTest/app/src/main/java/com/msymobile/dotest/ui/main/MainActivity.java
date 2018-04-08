package com.msymobile.dotest.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;
import android.widget.TextView;

import com.msymobile.dotest.R;
import com.msymobile.dotest.entity.FixerEntity;
import com.msymobile.dotest.mvp.contract.MainContract;
import com.msymobile.dotest.mvp.presenter.InstallPresenter;
import com.msymobile.dotest.mvp.presenter.MainPresenter;
import com.msymobile.dotest.mvp.view.BaseActivity;
import com.msymobile.dotest.util.RxClick;
import com.msymobile.dotest.util.keyboard.KeyboardManager;

import java.util.ArrayList;

import butterknife.BindView;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

@InstallPresenter(presenterObject = MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView {

    @BindView(R.id.fixer_tv)
    TextView mFixerText;

    @BindView(R.id.refresh_btn)
    Button mRefreshBtn;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MainAdapter mFixerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        RxClick.click(mRefreshBtn, o -> getPresenter().getFixer());
        mFixerAdapter = new MainAdapter(new ArrayList<>(),this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mFixerAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(new MainItemTouchHelper(mFixerAdapter));
        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != SCROLL_STATE_IDLE) {
                    KeyboardManager.getInstance(MainActivity.this).hideSoftKeyboard();
                }
            }
        });
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        getPresenter().getFixer();
    }

    @Override
    public void fixerInfo(FixerEntity entity) {
        hideWaitingDialog();
        mFixerAdapter.notifyData(entity.getRateList());
    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity,MainActivity.class));
    }
}
