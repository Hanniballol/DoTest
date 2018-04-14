package com.msymobile.photobox.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.msymobile.photobox.R;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

    private static final String PATH = "path";
    public static final String POSTION = "postion";
    private List<String> mPath;
    private int mPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initData();
        initView();
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        PhotoViewpagerAdapter adapter = new PhotoViewpagerAdapter(mPath,this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mPostion);
    }

    private void initData() {
        mPath = getIntent().getStringArrayListExtra(PATH);
        mPostion = getIntent().getIntExtra(POSTION,0);
    }

    public static void newInstance(Activity ctx,ArrayList<String> paths,int postion) {
        Intent intent = new Intent(ctx, ContentActivity.class);
        intent.putStringArrayListExtra(PATH,paths);
        intent.putExtra(POSTION,postion);
        ctx.startActivity(intent);
    }
}
