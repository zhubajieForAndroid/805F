package com.cheanda.a8805.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.utils.BItmapUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaintainResultActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "MaintainResultActivity";
    @Bind(R.id.result_bg)
    ImageView mResultBg;
    @Bind(R.id.shi_v)
    ImageView mShiV;
    @Bind(R.id.ge_v)
    ImageView mGeV;
    @Bind(R.id.fen_v)
    ImageView mFenV;
    @Bind(R.id.shi_a)
    ImageView mShiA;
    @Bind(R.id.ge_a)
    ImageView mGeA;
    @Bind(R.id.fen_a)
    ImageView mFenA;
    @Bind(R.id.carstate)
    TextView mCarstate;
    @Bind(R.id.fu)
    TextView mFu;
    @Bind(R.id.shi_o)
    ImageView mShiO;
    @Bind(R.id.fen_o)
    ImageView mFenO;
    @Bind(R.id.ge_o)
    ImageView mGeO;
    @Bind(R.id.message)
    TextView mMessage;
    @Bind(R.id.left_one)
    ImageView mLeftOne;
    @Bind(R.id.left_two)
    ImageView mLeftTwo;
    @Bind(R.id.left_three)
    ImageView mLeftThree;
    @Bind(R.id.left_four)
    ImageView mLeftFour;
    @Bind(R.id.right_one)
    ImageView mRightOne;
    @Bind(R.id.right_two)
    ImageView mRightTwo;
    @Bind(R.id.right_three)
    ImageView mRightThree;
    @Bind(R.id.right_four)
    ImageView mRightFour;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_result);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_23);
        mResultBg.setImageBitmap(mBitmap);
        mResultBg.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mResultBg.setImageBitmap(null);
        mBitmap.recycle();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "onTouch: x=" + x + "  y=" + y);
        if (x > 195 && x < 335 && y > 185 && y < 322) {

        }
        return false;
    }
}
