package com.cheanda.a8805.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.base.BaseActivity;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CheckUtil;
import com.cheanda.a8805.utils.NumberToImage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/5/22.
 */

public class ChangeResultActivity extends BaseActivity implements View.OnTouchListener {

    @Bind(R.id.result_change_image)
    ImageView mResultChangeImage;
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
    @Bind(R.id.number1)
    ImageView mNumber1;
    @Bind(R.id.number2)
    ImageView mNumber2;
    @Bind(R.id.number3)
    ImageView mNumber3;
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
    @Bind(R.id.old_one)
    ImageView mOldOne;
    @Bind(R.id.oldTwo)
    ImageView mOldTwo;
    @Bind(R.id.oldThree)
    ImageView mOldThree;
    @Bind(R.id.errorOne)
    ImageView mErrorOne;
    @Bind(R.id.errorTwo)
    ImageView mErrorTwo;
    @Bind(R.id.errorThree)
    ImageView mErrorThree;
    @Bind(R.id.hourOne)
    ImageView mHourOne;
    @Bind(R.id.hourTwo)
    ImageView mHourTwo;
    @Bind(R.id.MinuteOne)
    ImageView mMinuteOne;
    @Bind(R.id.MinuteThree)
    ImageView mMinuteThree;
    @Bind(R.id.secondOne)
    ImageView mSecondOne;
    @Bind(R.id.secondThree)
    ImageView mSecondThree;
    @Bind(R.id.number4)
    ImageView mNumber4;
    @Bind(R.id.oldFour)
    ImageView mOldFour;
    @Bind(R.id.errorFour)
    ImageView mErrorFour;
    @Bind(R.id.top)
    ImageView mTop;
    private ImageView mImageView;
    private Bitmap mBitmap;

    private boolean isChange = true;

    private int[] yellowImages = {R.mipmap.number_yellow_zero, R.mipmap.number_yellow_on, R.mipmap.number_yellow_two, R.mipmap.number_yellow_three,
            R.mipmap.number_yellow_four, R.mipmap.number_yellow_five, R.mipmap.number_yellow_six, R.mipmap.number_yellow_seven, R.mipmap.number_yellow_eight,
            R.mipmap.number_yellow_nine,};
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};

    private StringBuffer sb = new StringBuffer();
    private Handler mTimeHandler;
    int tempcount;
    private int tempDataLength;
    private byte[] arr = new byte[16];

    private int b = 0;
    private String mS;
    private String mAStr;
    private String mOilStr;
    private static final String TAG = "ChangeResultActivity";
    private String mMNewOilKGStr;
    private String mOldOilKGStr;
    private int mHour;
    private int mMinute;
    private int mSecond;
    private int mInitnewOil;
    private int mInitoldOil;
    private int mNewOilKG;
    private int mOldOilKG;
    private boolean c = true;
    private int mNewOilWeight;
    private int mOldOilWeight;
    private int mResultNew;
    private int mResultOld;
    private int mError;
    private String mResultData;
    private boolean isStart;
    private int distinguishResult;
    private byte[] array = new byte[8];
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_change_result);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //时间秒
        long time = intent.getLongExtra("time", 0);
        mInitnewOil = intent.getIntExtra("newOil", 0);
        mInitoldOil = intent.getIntExtra("oldOil", 0);

        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");//这里想要只保留分秒可以写成"mm:ss"
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        mResultData = formatter.format(time);

        mTimeHandler = new Handler();
        initData();
    }


    @Override
    protected void onDataReceived(byte[] buffer, int index) {
        tempcount = 0;
        if (index == 7) {
            System.arraycopy(buffer, 0, array, 0, index);
            //开始和停止
            checkDataForStartAndStop();
        }
        if (index == 16) {
            System.arraycopy(buffer, 0, arr, 0, index);
            //新油 电压
            checkData();
        }
    }

    //解析开始停止返回的结果
    private void checkDataForStartAndStop() {
        //解析运行指令结果数据
        isStart = CheckUtil.analysisStartResult(array);
        //返回
        if (isStart) {
            finish();
        }
    }


    //解析电压电流数据和通讯状态
    private void checkData() {
        for (byte anArr : arr) {
            sb.append(anArr);
        }
        String resultData = sb.toString();
        sb.delete(0, sb.length());
        if (!TextUtils.isEmpty(resultData)) {
            //有数据表示通讯正常
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessage.setText("正常");
                    mMessage.setTextColor(Color.parseColor("#00ff00"));
                }
            });
        }
        //解析电压电流数据
        analysisStateResult();
    }

    //解析电压电流油温的数据
    private void analysisStateResult() {
        //校验位成功
        if (arr[1] == 16) {
            mNewOilKG = CheckUtil.getNewOilWeightInt(arr);
            mOldOilKG = CheckUtil.getOldOilWeightInt(arr);
            if (c) {
                c = false;
                mNewOilWeight = mNewOilKG;
                mOldOilWeight = mOldOilKG;

                //设置新旧油的值
                setNewAndOldOil();
            }
            mMNewOilKGStr = CheckUtil.getNewOilWeight(arr);
            mOldOilKGStr = CheckUtil.getOldOilWeight(arr);
            mS = CheckUtil.getWorkV(arr);
            mAStr = CheckUtil.getWorkA(arr);
            String carState = CheckUtil.getCarState(arr);
            //改变车辆状态
            chengText(carState);
            mOilStr = CheckUtil.getOilTemperature(arr);
            //改变电压电流油温图片
            chengImage();
        }

    }

    private void setNewAndOldOil() {
        if (mInitnewOil >= 0) {
            //加注的新油
            mResultNew = (mInitnewOil - mNewOilWeight);
        }

        if (mInitoldOil >= 0) {
            //减少的旧油
            mResultOld = (mOldOilWeight - mInitoldOil);
        }
        mResultNew = Math.abs(mResultNew);
        mResultOld = Math.abs(mResultOld);

        //误差值
        if (mResultNew > mResultOld) {
            mError = mResultNew - mResultOld;
        } else {
            mError = mResultOld - mResultNew;
        }

        //变化图片
        changeImage();
    }

    private void changeImage() {
        final String ne = String.valueOf(mResultNew);
        final String old = String.valueOf(mResultOld);
        final String error = String.valueOf(mError);
        Log.d(TAG, "changeImage: mResultNew" + ne + "mResultOld" + mResultOld + "mError" + mError);
        final String hour = mResultData.substring(0, 2);
        final String minute = mResultData.substring(2, 4);
        final String second = mResultData.substring(4, 6);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //新油
                if (ne.length() - 1 >= 0) {
                    mNumber4.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(ne.charAt(ne.length() - 1)))));
                }
                if (ne.length() - 2 >= 0) {
                    mNumber3.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(ne.charAt(ne.length() - 2)))));
                }
                if (ne.length() - 3 >= 0) {
                    mNumber2.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(ne.charAt(ne.length() - 3)))));
                }
                if (ne.length() - 4 >= 0) {
                    mNumber1.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(ne.charAt(ne.length() - 4)))));
                }
                //旧油
                if (old.length() - 1 >= 0) {
                    mOldFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(old.charAt(old.length() - 1)))));
                }
                if (old.length() - 2 >= 0) {
                    mOldThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(old.charAt(old.length() - 2)))));
                }
                if (old.length() - 3 >= 0) {
                    mOldTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(old.charAt(old.length() - 3)))));
                }
                if (old.length() - 4 >= 0) {
                    mOldOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(old.charAt(old.length() - 4)))));
                }

                //误差
                if (error.length() - 1 >= 0) {
                    mErrorFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(error.charAt(error.length() - 1)))));
                }
                if (error.length() - 2 >= 0) {
                    mErrorThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(error.charAt(error.length() - 2)))));
                }
                if (error.length() - 3 >= 0) {
                    mErrorTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(error.charAt(error.length() - 3)))));
                }
                if (error.length() - 4 >= 0) {
                    mErrorOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(error.charAt(error.length() - 4)))));
                }


                //小时
                if (hour.length() - 1 >= 0) {
                    mHourTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(hour.charAt(hour.length() - 1)))));
                }
                if (hour.length() - 2 >= 0) {
                    mHourOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(hour.charAt(hour.length() - 2)))));
                }
                //分
                if (minute.length() - 1 >= 0) {
                    mMinuteThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(minute.charAt(minute.length() - 1)))));
                }
                if (minute.length() - 2 >= 0) {
                    mMinuteOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(minute.charAt(minute.length() - 2)))));
                }
                //秒
                if (second.length() - 1 >= 0) {
                    mSecondThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(second.charAt(second.length() - 1)))));
                }
                if (second.length() - 2 >= 0) {
                    mSecondOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(second.charAt(second.length() - 2)))));
                }
            }
        });

    }

    //改变电压电流油温图片
    private void chengImage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //电压
                if (mS.length() - 1 >= 0) {
                    mFenV.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 1)))));
                }
                if (mS.length() - 2 >= 0) {
                    mGeV.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 2)))));
                }
                if (mS.length() - 3 >= 0) {
                    mShiV.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 3)))));
                }
                //电流
                if (mAStr.length() - 2 >= 0) {
                    mShiA.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mAStr.charAt(mAStr.length() - 2)))));
                }
                if (mAStr.length() - 3 >= 0) {
                    mGeA.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mAStr.charAt(mAStr.length() - 3)))));
                }
                if (mAStr.length() - 4 >= 0) {
                    mFenA.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mAStr.charAt(mAStr.length() - 4)))));
                }

                //新油油量
                if (mMNewOilKGStr.length() - 1 >= 0) {
                    mLeftFour.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mMNewOilKGStr.charAt(mMNewOilKGStr.length() - 1)))));
                }
                if (mMNewOilKGStr.length() - 2 >= 0) {
                    mLeftThree.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mMNewOilKGStr.charAt(mMNewOilKGStr.length() - 2)))));
                }
                if (mMNewOilKGStr.length() - 3 >= 0) {
                    mLeftTwo.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mMNewOilKGStr.charAt(mMNewOilKGStr.length() - 3)))));
                }
                if (mMNewOilKGStr.length() - 4 >= 0) {
                    mLeftOne.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mMNewOilKGStr.charAt(mMNewOilKGStr.length() - 4)))));
                }

                //旧油油量
                if (mOldOilKGStr.length() - 1 >= 0) {
                    mRightFour.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOldOilKGStr.charAt(mOldOilKGStr.length() - 1)))));
                }
                if (mOldOilKGStr.length() - 2 >= 0) {
                    mRightThree.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOldOilKGStr.charAt(mOldOilKGStr.length() - 2)))));
                }
                if (mOldOilKGStr.length() - 3 >= 0) {
                    mRightTwo.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOldOilKGStr.charAt(mOldOilKGStr.length() - 3)))));
                }
                if (mOldOilKGStr.length() - 4 >= 0) {
                    mRightOne.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOldOilKGStr.charAt(mOldOilKGStr.length() - 4)))));
                }
                //油温度
                if (mOilStr.startsWith("-")) {
                    mFu.setText("-");
                    if (mOilStr.length() - 1 >= 0) {
                        mGeO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 1)))));
                    }
                    if (mOilStr.length() - 2 >= 0) {
                        mFenO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 2)))));
                    }
                    if (mOilStr.length() - 3 >= 0) {
                        mShiO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 3)))));
                    }
                } else {
                    mFu.setText("");
                    if (mOilStr.length() - 1 >= 0) {
                        mGeO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 1)))));
                    }
                    if (mOilStr.length() - 2 >= 0) {
                        mFenO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 2)))));
                    }
                    if (mOilStr.length() - 3 >= 0) {
                        mShiO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 3)))));
                    }
                }


            }
        });
    }

    //改变车辆状态文字
    private void chengText(final String i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (i.charAt(i.length() - 1) == '1') {
                    mCarstate.setText("启动");
                } else if (i.charAt(i.length() - 1) == '0') {
                    mCarstate.setText("熄火");
                }

            }
        });

    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_23);
        mResultChangeImage.setImageBitmap(mBitmap);


        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_23_1);

        mTop.setImageBitmap(mBitmapTop);
        mResultChangeImage.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 560 && x < 720 && y > 660 && y < 700) {
            //发送返回2a 05 0c 23 23
            int[] bytes = {0x2a, 0x05, 0x0c, 0x23, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
        return false;
    }

    private Runnable mTimeRunnable = new Thread() {
        @Override
        public void run() {
            tempcount++;
            if (tempcount > 3) {
                //三次没有数据显示异常
                mMessage.setText("异常");
                mMessage.setTextColor(Color.parseColor("#ff0000"));
            }
            mTimeHandler.postDelayed(this, 300);
            //发送查询
            int[] bytes = {0x2a, 0x06, 0x09, 0x01, 0x24, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mTimeHandler.postDelayed(mTimeRunnable, 300);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTimeHandler.removeCallbacks(mTimeRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mResultChangeImage.setImageBitmap(null);
        mBitmap.recycle();

    }
}
