package com.msymobile.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autour: hannibal
 * date: 2018/4/14
 * e-mail:404769122@qq.com
 * description:
 */
public class PhotoAdatapter extends RecyclerView.Adapter<PhotoAdatapter.CustomViewHolder> {

    private List<String> paths;
    private LruCache<String, Bitmap> mLruCache;
    private ClickEventListener mListener;
    private int mWidthPixels;
    private static Handler mHandler = new Handler();
    ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    void setListener(ClickEventListener listener) {
        mListener = listener;
    }

    PhotoAdatapter(List<String> paths, Context ctx) {
        this.paths = paths;
        mLruCache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024 / 8));
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        mWidthPixels = (widthPixels == 0 ? 100 : widthPixels) / 3;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_adapter_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        displayImage(paths.get(position), holder.mImageView);
        holder.mImageView.setOnClickListener(mOnClickListener);
        holder.mImageView.setTag(position);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null != mListener) {
                int postion = (int) v.getTag();
                mListener.onClick(paths.get(postion), postion);

            }
        }
    };

    void notifyData(List<String> list) {
        paths.clear();
        paths.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    private void displayImage(final String imagePath, final ImageView imageView) {
        if (TextUtils.isEmpty(imagePath)) return;
        Bitmap bitmap = mLruCache.get(imagePath);

        if (null != bitmap) imageView.setImageBitmap(bitmap);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Bitmap localBitmap = PhotoAcquire.getLocalBitmap(imagePath, mWidthPixels, mWidthPixels);
                mLruCache.put(imagePath, localBitmap);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(localBitmap);
                    }
                });
            }
        };
        mExecutor.execute(runnable);
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
