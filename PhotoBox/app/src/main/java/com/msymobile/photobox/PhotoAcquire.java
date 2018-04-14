package com.msymobile.photobox;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * autour: hannibal
 * date: 2018/4/14
 * e-mail:404769122@qq.com
 * description:
 */
public class PhotoAcquire {
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 3;
    public static final int CHOOSE_FORM_ALBUM = 2;

    public static List<String> chooseFromAlbum(Activity context) {
        //权限检查
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，请求权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
            return null;
        } else {
            //打开相册
            return openAlbum(context);
        }
    }

    static List<String> openAlbum(Activity context) {
        return PhotoAcquire.getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public static BitmapFactory.Options getOptions(String path, int destWidth, int destHeight) {
        if (destHeight > 0 && destWidth > 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int width = options.outWidth;
            int height = options.outHeight;
            int sampleSize = 1;
            while (height / sampleSize > destHeight || width / sampleSize > destWidth) {
                //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
                //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
                sampleSize *= 2;
            }
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            return options;
        }

        return getOptions(path, 4);
    }

    public static BitmapFactory.Options getOptions(String path, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }

    public static Bitmap getLocalBitmap(String imagePath, int widthPixels, int heightPixels) {
        return BitmapFactory.decodeFile(imagePath, PhotoAcquire.getOptions(imagePath, widthPixels, heightPixels));
    }

    //获取照片路径
    public static ArrayList<String> getImagePath(Context ctx, Uri uri) {
        ArrayList<String> paths = new ArrayList<>();
        Cursor cursor = ctx.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                paths.add(path);
            }
            cursor.close();
        }
        return paths;
    }
}
