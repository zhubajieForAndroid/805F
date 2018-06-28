package com.cheanda.a8805.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.cheanda.a8805.views.StstemSettingDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OldCalibrationFourActivity extends BaseActivity implements View.OnTouchListener {
    @Bind(R.id.new_Four_oil_bg)
    ImageView mNewFourOilBg;
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
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private byte[] startArray = new byte[8];
    private Boolean mIsStart = false;
    private float xishu;
    private int intxishi;
    private static final String TAG = "NewOilCalibrationFourActivity";
    private String mS;
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_new_oil_calibration_four);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences("famaFile", 0);
        String fama = sp.getString("fama", "");
        String kongpan = sp.getString("kongpan", "");
        String hejizhongliang = sp.getString("hejizhongliang", "");
        if (!TextUtils.isEmpty(fama) && !fama.equals("null") && !TextUtils.isEmpty(kongpan) && !kongpan.equals("null") && !TextUtils.isEmpty(hejizhongliang) && !hejizhongliang.equals("null")) {
            float famaInt = Integer.parseInt(fama);
            float kongpanInt = Integer.parseInt(kongpan);
            float hejizhongliangInt = Integer.parseInt(hejizhongliang);
            if (hejizhongliangInt > kongpanInt) {
                xishu = famaInt / (hejizhongliangInt - kongpanInt);
                xishu *= 10000;
                intxishi = (int) xishu;
            }
        }
        mS = "000" + String.valueOf(intxishi);
        chengImage(mS);
        initListener();
        initData();
    }

    private void chengImage(String s) {
        s = "0000" + s;
        if (s.length() - 1 >= 0) {
            mNumber5.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mNumber4.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
        if (mS.length() - 3 >= 0) {
            mNumber3.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 3)))));
        }
        if (s.length() - 4 >= 0) {
            mNumber2.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 4)))));
        }
        if (s.length() - 5 >= 0) {
            mNumber1.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 5)))));
        }

    }

    @Override
    protected void onDataReceived(byte[] buffer, int index) {
        if (index == 7) {
            System.arraycopy(buffer, 0, startArray, 0, index);
            //开始和停止
            checkDataForStartAndStop();
        }
    }

    private void checkDataForStartAndStop() {
        mIsStart = CheckUtil.analysisStartResult(startArray);
        if (mIsStart) {
            Intent intent = new Intent(this, OldCalibrationFiveActivity.class);
            startActivity(intent);
        }
    }

    private void initListener() {
        mNewFourOilBg.setOnTouchListener(this);
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_old_3_4);
        mNewFourOilBg.setImageBitmap(mBitmap);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_old_3_1_1);

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
            int[] bytes = {0x2a, 0x06, 0x09, 0x02, 0x27, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
            SystemClock.sleep(200);
            int gaob = (intxishi & 0x0000FF00) >> 8;
            int dib = (intxishi & 0x000000FF);
            int result = 0x2a ^ 0x07 ^ 0x18 ^ gaob ^ dib;
            //2a 07 18 48 44 39 23
            int[] arr = {0x2a, 0x07, 0x18, gaob, dib, result, 0x23};
            CheckUtil.sendMessage(arr, mOutputStream);
        } else if (x > 703 && x < 900 && y > 385 && y < 432) {
            StstemSettingDialog dialog = new StstemSettingDialog(this, R.style.dialog, "系数校准(手动输入)");
            dialog.show();
            dialog.setOnBtnListener(new StstemSettingDialog.OnBtnListener() {
                @Override
                public void btnListener(String data) {
                    intxishi = Integer.parseInt(data);
                    chengImage(data);
                }
            });
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewFourOilBg.setImageBitmap(null);
        mBitmap.recycle();
        unregisterReceiver(receiver);
    }
}
