package com.msymobile.dotest.util;

import java.io.File;

import static com.Constants.CACHE;
import static com.Constants.RESOURCE;

/**
 * autour: hannibal
 * date: 2018/4/5
 * e-mail:404769122@qq.com
 * description:
 */
public class FileUtil {

    public static File getExternalResouceDir() {
        if (existSDCard()) {
            return ContextUtil.getContext().getExternalFilesDir(RESOURCE);
        }
        return null;
    }

    public static File getNetCahceDir() {
        File cacheDir;
        if (existSDCard() && ContextUtil.getContext().getExternalCacheDir() != null) {
            cacheDir = ContextUtil.getContext().getExternalCacheDir();
        } else {
            cacheDir = ContextUtil.getContext().getCacheDir();
        }
        return new File(cacheDir, CACHE);
    }

    private static boolean existSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
