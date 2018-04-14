package com.msymobile.photobox.viewpager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msymobile.photobox.PhotoAcquire;

import java.util.List;

/**
 * autour: hannibal
 * date: 2018/4/14
 * e-mail:404769122@qq.com
 * description:
 */
public class PhotoViewpagerAdapter extends PagerAdapter {
    private List<String> paths;
    private Context mContext;
    private final int mWidthPixels;
    private final int mHeightPixels;

    public PhotoViewpagerAdapter(List<String> paths,Context ctx) {
        this.paths = paths;
        mContext = ctx;
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        mWidthPixels = metrics.widthPixels;
        mHeightPixels = metrics.heightPixels;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        String path = paths.get(position);
        imageView.setImageBitmap(BitmapFactory.decodeFile(path, PhotoAcquire.getOptions(path,mWidthPixels,mHeightPixels)));
        container.addView(imageView);
        return imageView;
    }
}
