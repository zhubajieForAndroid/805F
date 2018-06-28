package com.cheanda.a8805.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.cheanda.a8805.R;
import com.cheanda.a8805.adapter.ResultPagerAdapter;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    private String mId;
    private String[] tabs = {"变速箱属性", "换油宝典", "养护价格"};
    private ResultPagerAdapter mAdapter;
    private DisplayMetrics mDm;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerTabs;
    private ImageView mBack;
    private String mApi;
    private UserDataDao mDao;
    private Bitmap mBitmapTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);
        mDm = getResources().getDisplayMetrics();


        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        initView();
        initTab();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);


        mPagerTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView iv = (ImageView) findViewById(R.id.resulttopbar);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.topbar_2);

        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.back);
        mBack.setImageBitmap(bitmap1);
        iv.setImageBitmap(mBitmapTop);


    }


    private void initTab() {
        mAdapter = new ResultPagerAdapter(getSupportFragmentManager(), tabs);
        //设置是否宽度铺满全屏
        mPagerTabs.setShouldExpand(true);
        //设置字体大小
        mPagerTabs.setTextSize(22);
        //设置字体颜色
        mPagerTabs.setTextColor(Color.WHITE);
        //设置分割线
        mPagerTabs.setDividerColorResource(R.color.colorAccent);
        //设置底部滚动条的高度
        mPagerTabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mDm));
        // 设置Tab Indicator的高度
        mPagerTabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mDm));
        //设置滚动条的颜色
        mPagerTabs.setIndicatorColor(getResources().getColor(R.color.color));
        mViewPager.setAdapter(mAdapter);
        mPagerTabs.setViewPager(mViewPager);
    }

    public String getID() {
        return mId;
    }

}
