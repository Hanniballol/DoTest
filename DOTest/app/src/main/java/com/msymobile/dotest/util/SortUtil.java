package com.msymobile.dotest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.msymobile.dotest.entity.FixerEntity;

import java.util.List;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:
 */
public class SortUtil {
    private static final String CONFIG_SP = "config_sp";
    private static final String SORT = "sort";
    private static final String SPLIT = ",";

    /**
     * 保存排序信息到本地
     */
    public static void saveSortData(List<FixerEntity.ItemInfo> list) {
        SharedPreferences sharedPreferences = ContextUtil.getContext().getSharedPreferences(CONFIG_SP, Context.MODE_PRIVATE);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            FixerEntity.ItemInfo info = list.get(i);
            if (i < list.size() - 1) {
                stringBuilder.append(info.getFixerName()).append(SPLIT);
            }
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(SORT, stringBuilder.toString());
        edit.apply();
    }

    /**
     * 获取排序信息
     */
    public static String[] getSortData() {
        SharedPreferences sharedPreferences = ContextUtil.getContext().getSharedPreferences(CONFIG_SP, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(SORT, "");
        return string.split(SPLIT);
    }
}
