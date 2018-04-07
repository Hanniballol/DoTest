package com.msymobile.dotest.ui.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.msymobile.dotest.R;
import com.msymobile.dotest.entity.FixerEntity;
import com.msymobile.dotest.util.keyboard.KeyboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private List<FixerEntity.ItemInfo> mFixers;
    private Double mChangeFixer;
    private int mCurrentPostion;
    private int mCurrentEtStart;
    private EditText mCurrentEditText;

    /**
     * 添加onBind标志符，处理notifyDataSetChanged循环调用导致奔溃问题
     * 在没有调用notifyDataSetChanged时，onBind为true，将不会执行onTextChanged()
     * 里的逻辑，除非onBindViewHolder()执行完成以后
     * 循环调用： 对item而言 onBindViewHolder() -> onTextChanged() -> notifyDataSetChnanged() -> onBindViewHolder()
     */
    private boolean onBind;
    private final KeyboardManager mKeyboardManager;

    MainAdapter(List<FixerEntity.ItemInfo> list, Activity ctx) {
        this.mFixers = list;
        mKeyboardManager = KeyboardManager.getInstance(ctx);
    }

    List<FixerEntity.ItemInfo> getFixers() {
        return mFixers;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        FixerEntity.ItemInfo itemInfo = mFixers.get(position);
        onBind = true;
        holder.fixerNameTv.setText(itemInfo.getFixerName());
        holder.rateEt.setText(String.format(Locale.getDefault(), "%.2f", itemInfo.getRate()));
        if (holder.rateEt.getTag() == null) {
            holder.rateEt.addTextChangedListener(mTextWatcher);
            holder.rateEt.setOnFocusChangeListener(mOnFocusChangeListener);
            holder.rateEt.setTag(position);
        }
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return mFixers.size();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!onBind) {
                mCurrentEtStart = start;
                double v = Double.parseDouble(s.toString());
                if (mChangeFixer == v) return;
                double multiple = v / mChangeFixer;
                refreshData(multiple);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            mCurrentEditText = editText;
            if (hasFocus) {
                mKeyboardManager.attachTo(editText);
                mChangeFixer = Double.parseDouble(editText.getText().toString());
                mCurrentPostion = (int) v.getTag();
                Log.e("hannibal", "onFocusChange : " + mChangeFixer + " ：" + mCurrentPostion);
                mKeyboardManager.showSoftKeyboard();
            }
        }
    };

    private void refreshData(double multiple) {
        ArrayList<FixerEntity.ItemInfo> itemInfos = new ArrayList<>();
        for (FixerEntity.ItemInfo itemInfo : mFixers) {
            itemInfos.add(new FixerEntity.ItemInfo(itemInfo.getFixerName(), itemInfo.getRate() * multiple));
        }
        mFixers.clear();
        mFixers.addAll(itemInfos);
        notifyItemRangeChanged(0,mCurrentPostion - 1);
        notifyItemRangeChanged(mCurrentPostion + 1, mFixers.size() - 1);
    }

    void notifyData(List<FixerEntity.ItemInfo> list) {
        mFixers.clear();
        mFixers.addAll(list);
        notifyDataSetChanged();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fixer_name_tv)
        TextView fixerNameTv;
        @BindView(R.id.rate_et)
        EditText rateEt;

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
