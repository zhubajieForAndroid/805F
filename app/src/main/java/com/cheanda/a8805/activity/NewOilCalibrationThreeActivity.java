package com.cheanda.a8805.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.base.BaseActivity;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CheckUtil;
import com.cheanda.a8805.utils.NumberToImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewOilCalibrationThreeActivity extends BaseActivity implements View.OnTouchListener {

    @Bind(R.id.new_Three_oil_bg)
    ImageView mNewThreeOilBg;
    @Bind(R.id.number1)
    ImageView mNumber1;
    @Bind(R.id.number2)
    ImageView mNumber2;
    @Bind(R.id.number3)
    ImageView mNumber3;
    @Bind(R.id.number4)
    ImageView mNumber4;
    @Bind(R.id.number5)
    ImageView mNumber5;
    @Bind(R.id.top)
    ImageView mTop;
    private Bitmap mBitmap;
    private StringBuffer sb = new StringBuffer();
    int tempDataLength = 0;
    private Handler mHandler;
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private int index = 0;
    private byte[] arr = new byte[10];
    private Bitmap mBitmapTop;
    private String mResult;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_new_oil_calibration_three);
        ButterKnife.bind(this);
        mHandler = new Handler();
        initListener();
        initData();
    }

    //接受串口数据
    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        if (size == 10) {
            System.arraycopy(buffer, 0, arr, 0, size);
            checkData();
        }
    }

    private void checkData() {

        mResult = CheckUtil.getNewOilKG(arr);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chengImage(mResult);
            }
        });
    }

    private void chengImage(String s) {
        if (s.length() - 1 >= 0) {
            mNumber5.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mNumber4.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
        if (s.length() - 3 >= 0) {
            mNumber3.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 3)))));
        }
        if (s.length() - 4 >= 0) {
            mNumber2.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 4)))));
        }
        if (s.length() - 5 >= 0) {
            mNumber1.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 5)))));
        }

    }

    private void initListener() {
        mNewThreeOilBg.setOnTouchListener(this);
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_new_3_3);
        mNewThreeOilBg.setImageBitmap(mBitmap);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_new_3_1_1);

        mTop.setImageBitmap(mBitmapTop);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTIVITY_STATE);
        registerReceiver(receiver, intentFilter);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 65 && x < 300 && y > 580 && y < 639) {
            finish();
        } else if (x > 985 && x < 1220 && y > 579 && y < 640) {
            Intent intent = new Intent(this, NewOilCalibrationFourActivity.class);
            startActivity(intent);
            SharedPreferences sp = getSharedPreferences("famaFile", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("hejizhongliang", mResult);
            editor.commit();

        }
        return false;
    }

    private Runnable mTimeRunnable = new Thread() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 300);
            //发送查询2a 06 09 02 27 23
            int[] bytes = {0x2a, 0x06, 0x09, 0x02, 0x27, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mTimeRunnable, 300);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面不可见移除发送数据任务
        mHandler.removeCallbacks(mTimeRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewThreeOilBg.setImageBitmap(null);
        mBitmap.recycle();
        unregisterReceiver(receiver);
    }
}
