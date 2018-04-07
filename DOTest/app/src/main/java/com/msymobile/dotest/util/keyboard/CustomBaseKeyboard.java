package com.msymobile.dotest.util.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:自定义键盘面板
 */
public abstract class CustomBaseKeyboard extends Keyboard implements KeyboardView.OnKeyboardActionListener {

    private EditText etCurrent;
    private View nextFocusView;

    CustomBaseKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    void setCurEditText(EditText etCurrent) {
        this.etCurrent = etCurrent;
    }

    public EditText getCurEditText() {
        return etCurrent;
    }

    void setNextFocusView(View view) {
        this.nextFocusView = view;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (null != etCurrent && etCurrent.hasFocus() && !handleSpecialKey(etCurrent, primaryCode)) {
            Editable editable = etCurrent.getText();
            int start = etCurrent.getSelectionStart();

            if (primaryCode == -30) { //隐藏
                hideKeyboard();
            } else if (primaryCode == 46) { //小数点
                if (!editable.toString().contains(".")) {
                    editable.insert(start, Character.toString((char) primaryCode));
                }
            } else { //其他默认
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    }

    private void hideKeyboard() {
        if (null != nextFocusView) nextFocusView.requestFocus();
    }

    /**
     * 处理自定义键盘的特殊定制键
     * 注: 所有的操作要针对etCurrent来操作
     *
     * @param etCurrent   当前操作的EditText
     * @param primaryCode 选择的Key
     * @return true: 已经处理过, false: 没有被处理
     */
    public abstract boolean handleSpecialKey(EditText etCurrent, int primaryCode);

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}