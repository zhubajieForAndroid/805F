package com.cheanda.a8805.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/*旧校准第一步*/
public class OldCalibrationOneActivity extends BaseActivity implements View.OnTouchListener {
    private static final String TAG = "NewOIlCalibrationOneActivity";
    @Bind(R.id.new_oil_bg)
    ImageView mNewOilBg;
    @Bind(R.id.qian_one)
    ImageView mQianOne;
    @Bind(R.id.bai_one)
    ImageView mBaiOne;
    @Bind(R.id.shi_one)
    ImageView mShiOne;
    @Bind(R.id.ge_one)
    ImageView mGeOne;
    @Bind(R.id.top)
    ImageView mTop;
    private Bitmap mBitmap;
    private int count = 1000;
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_new_oil_calibration);
        ButterKnife.bind(this);
        chengImage();
        initListener();
        initData();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int index) {

    }


    private void initListener() {
        mNewOilBg.setOnTouchListener(this);
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_old_3_1);
        mNewOilBg.setImageBitmap(mBitmap);

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
            int[] bytes = {0x2a, 0x06, 0x09, 0x08,0x2D, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
            finish();
        } else if (x > 888 && x < 937 && y > 380 && y < 423) {
            if (count <= 1500) {
                count++;
                chengImage();
            }

        } else if (x > 992 && x < 1145 && y > 372 && y < 423) {
            if (count > 101) {
                count--;
                chengImage();
            }
        } else if (x > 985 && x < 1220 && y > 579 && y < 640) {
            Intent intent = new Intent(this, OldCalibrationTwoActivity.class);
            startActivity(intent);
            SharedPreferences sp = getSharedPreferences("famaFile", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("fama", String.valueOf(count * 10));
            editor.commit();
        }
        return false;
    }

    private void chengImage() {
        String s = "000" + String.valueOf(count);
        if (s.length() == 1) {
            mShiOne.setImageResource(NumberToImage.getRedImageResId(images, 0));
        }
        if (s.length() == 2) {
            mBaiOne.setImageResource(NumberToImage.getRedImageResId(images, 0));
        }
        if (s.length() == 3) {
            mQianOne.setImageResource(NumberToImage.getRedImageResId(images, 0));
        }
        if (s.length() - 1 >= 0) {
            mGeOne.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mShiOne.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
        if (s.length() - 3 >= 0) {
            mBaiOne.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 3)))));
        }
        if (s.length() - 4 >= 0) {
            mQianOne.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 4)))));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewOilBg.setImageBitmap(null);
        mBitmap.recycle();
        unregisterReceiver(receiver);
    }
}
