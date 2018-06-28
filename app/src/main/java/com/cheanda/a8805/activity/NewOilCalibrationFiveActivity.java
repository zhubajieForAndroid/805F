package com.cheanda.a8805.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

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

public class NewOilCalibrationFiveActivity extends BaseActivity implements View.OnTouchListener {

    private static final String TAG = "NewOilCalibrationFiveActivity";
    @Bind(R.id.new_Five_oil_bg)
    ImageView mNewFiveOilBg;
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
    private byte[] arr = new byte[10];

    private int index = 0;

    private StringBuffer sb = new StringBuffer();
    int tempDataLength = 0;
    private Handler mHandler;
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private Integer mInteger;
    private byte[] startArray = new byte[8];
    private Boolean mIsStart = false;
    private Bitmap mBitmapTop;
    private String mNewOilKG;
    private String mResult;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_new_oil_calibration_five);
        ButterKnife.bind(this);
        mHandler = new Handler();
        initListener();
        initData();
    }

    //接受串口数据
    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        if (size == 7) {
            System.arraycopy(buffer, 0, startArray, 0, size);
            //开始和停止
            checkDataForStartAndStop();
        }
        if (size == 10) {
            System.arraycopy(buffer, 0, arr, 0, size);
            //新油 电压
            checkData();
        }

    }

    private void checkDataForStartAndStop() {
        mIsStart = CheckUtil.analysisStartResult(startArray);
        if (mIsStart) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NewOilCalibrationFiveActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

                }
            });
            Intent intent = new Intent();
            intent.setAction(Constants.ACTIVITY_STATE);
            sendBroadcast(intent);
            finish();
        }
    }

    private void checkData() {
        //2A 0A 09 02   36 64   3F FF   B9 23
        mNewOilKG = CheckUtil.getNewOilKG(arr);
        mResult = mNewOilKG.substring(4);
        if (mResult.length() == 3) {
            mResult = "0" + mResult;
        }
        if (mResult.length() == 2) {
            mResult = "00" + mResult;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chengImage(mNewOilKG);
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
        mNewFiveOilBg.setOnTouchListener(this);
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_new_3_5);
        mNewFiveOilBg.setImageBitmap(mBitmap);
        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_new_3_1_1);
        mTop.setImageBitmap(mBitmapTop);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 65 && x < 300 && y > 580 && y < 639) {
            finish();
        } else if (x > 985 && x < 1220 && y > 579 && y < 640) {
            int gaob = Integer.parseInt(mResult.substring(0, 2), 10);
            int dib = Integer.parseInt(mResult.substring(2, 4), 10);
            int result = 0x2a ^ 0x07 ^ 0x19 ^ gaob ^ dib;
            //2a 07 19 0b b8 87 23
            int[] arr = {0x2a, 0x07, 0x19, gaob, dib, result, 0x23};
            CheckUtil.sendMessage(arr, mOutputStream);

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
        mNewFiveOilBg.setImageBitmap(null);
        mBitmap.recycle();
    }
}
