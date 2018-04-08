package com.msymobile.dotest.util.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.msymobile.dotest.R;

import java.lang.reflect.Method;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:软键盘管理者
 */
public class KeyboardManager {

    private static KeyboardManager mInstance;

    private Context mContext;
    private FrameLayout mKeyboardViewContainer;
    private View etFocusScavenger;
    private final CustomBaseKeyboard mKeyboard;
    private EditText mCurrentEt;

    public static KeyboardManager getInstance(Activity activity) {
        if (null == mInstance) {
            synchronized (KeyboardManager.class) {
                if (null == mInstance) {
                    mInstance = new KeyboardManager(activity);
                }
            }
        }
        return mInstance;
    }

    private KeyboardManager(Activity activity) {
        mContext = activity;
        ViewGroup rootView = (activity.getWindow().getDecorView().findViewById(android.R.id.content));
        mKeyboard = new CustomBaseKeyboard(activity, R.xml.soft_keyboard) {
            @Override
            public boolean handleSpecialKey(EditText etCurrent, int primaryCode) {
                if (primaryCode == CANCEL_KEY) {
                    hideSoftKeyboard();
                }
                return false;
            }
        };

        mKeyboardViewContainer = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_keyboardview, null);
        KeyboardView keyboardView = mKeyboardViewContainer.findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(mKeyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(mKeyboard);
        etFocusScavenger = mKeyboardViewContainer.findViewById(R.id.et_focus_scavenger);
        hideSystemSoftKeyboard((EditText) etFocusScavenger);

        FrameLayout.LayoutParams keyboardViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        keyboardViewLayoutParams.gravity = Gravity.BOTTOM;
        rootView.addView(mKeyboardViewContainer, keyboardViewLayoutParams);
    }

    public void attachTo(EditText editText) {
        if (mCurrentEt == editText) return;
        mCurrentEt = editText;
        hideSystemSoftKeyboard(editText);
    }

    public void showSoftKeyboard() {
        if (null == mKeyboard || null == mKeyboardViewContainer) return;
        mKeyboard.setCurEditText(mCurrentEt);
        mKeyboard.setNextFocusView(etFocusScavenger); //为键盘设置下一个焦点响应控件.
        mKeyboardViewContainer.setVisibility(View.VISIBLE);
        mKeyboardViewContainer.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.down_to_up));
    }

    public void hideSoftKeyboard() {
        if (null == mKeyboardViewContainer) return;
        mKeyboardViewContainer.setVisibility(View.GONE);
        mKeyboardViewContainer.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.up_to_hide));
    }

    /**
     * 隐藏系统键盘
     */
    public static void hideSystemSoftKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }
}
