package com.msymobile.photobox;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.msymobile.photobox.viewpager.ContentActivity;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 第一个界面有做滑动优化（线程处理），第二个界面逻辑类似，由于电脑没电，所以第二个界面没有做滑动优化
 *
 */
public class MainActivity extends AppCompatActivity implements ClickEventListener {

    private RecyclerView mRecyclerView;
    private PhotoAdatapter mPhotoAdatapter;
    private GridLayoutManager mManager;
    private List<String> mPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mPaths = PhotoAcquire.chooseFromAlbum(this);
        if (null == mPaths ||mPaths.size() < 1) return;
        mPhotoAdatapter.notifyData(mPaths);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mManager = new GridLayoutManager(this, 3);
        mPhotoAdatapter = new PhotoAdatapter(mPaths, this);
        mRecyclerView.setAdapter(mPhotoAdatapter);
        mRecyclerView.setLayoutManager(mManager);
        mPhotoAdatapter.setListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PhotoAcquire.PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPaths = PhotoAcquire.openAlbum(this);
                    mPhotoAdatapter.notifyData(mPaths);
                } else {
                    Toast.makeText(MainActivity.this, "您没有给予授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoAcquire.CHOOSE_FORM_ALBUM:
                //从相册选取照片回调
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //Android4.4及以上版本
                        handleImageOnKitkat(data);
                    } else {
                        //Android4.4以下版本
                        handleImageBeforeKitkat(data);
                    }
                    mPhotoAdatapter.notifyData(mPaths);
                }
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                mPaths = PhotoAcquire.getImagePath(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                mPaths = PhotoAcquire.getImagePath(this, contentUri);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            mPaths = PhotoAcquire.getImagePath(this, uri);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri ，直接获取图片的路径即可
        }
    }


    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        mPaths = PhotoAcquire.getImagePath(this, uri);
    }


    @Override
    public void onClick(String path, int postion) {
        ContentActivity.newInstance(this, (ArrayList<String>) mPaths, postion);
    }
}
