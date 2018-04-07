package com.msymobile.dotest.ui.icon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.msymobile.dotest.R;
import com.msymobile.dotest.entity.IconXmlEntity;
import com.msymobile.dotest.mvp.contract.IconConstract;
import com.msymobile.dotest.mvp.presenter.IconPresenter;
import com.msymobile.dotest.mvp.presenter.InstallPresenter;
import com.msymobile.dotest.mvp.view.BaseActivity;
import com.msymobile.dotest.util.RxClick;
import com.msymobile.dotest.util.ToastUtil;
import com.msymobile.dotest.util.XmlParser;

import butterknife.BindView;

/**
 * autour: hannibal
 * date: 2018/4/7
 * e-mail:404769122@qq.com
 * description:
 */

@InstallPresenter(presenterObject = IconPresenter.class)
public class IconActivity extends BaseActivity<IconPresenter> implements IconConstract.IIconView {

    @BindView(R.id.input_et)
    EditText mInputEditText;
    @BindView(R.id.analysis_btn)
    Button mAnalysisButton;
    @BindView(R.id.icon_iv)
    ImageView mIconImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_icon;
    }

    @Override
    protected void initView() {
        mInputEditText.setText("https://m.weibo.com");
        RxClick.click(mAnalysisButton, o -> {
            String url = mInputEditText.getText().toString();
            if (XmlParser.isUrl(url)) {
                IconActivity.this.getPresenter().getIcon(url);
            } else {
                ToastUtil.makeText("请输入正确的地址!");
            }
        });
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @Override
    public void iconInfo(IconXmlEntity entity) {
        hideWaitingDialog();
        String url = entity.getUrl();
        if (TextUtils.isEmpty(url)) {
            ToastUtil.makeText("没有解析到icon!");
        } else {
            Glide.with(this)
                    .load(entity.getUrl())
                    .into(mIconImageView);
        }
    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, IconActivity.class));
    }
}
