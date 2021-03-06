package com.cheanda.a8805.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.base.BaseActivity;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CheckUtil;
import com.cheanda.a8805.utils.NumberToImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//深度保养
public class DepthActivity extends BaseActivity implements View.OnTouchListener {

    private static final String TAG = "DepthActivity";
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
    @Bind(R.id.message)
    TextView mMessage;
    @Bind(R.id.genumber)
    ImageView mGenumber;
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
    @Bind(R.id.number1)
    ImageView mNumber1;
    @Bind(R.id.number2)
    ImageView mNumber2;
    @Bind(R.id.number3)
    ImageView mNumber3;
    @Bind(R.id.number4)
    ImageView mNumber4;
    @Bind(R.id.progress)
    TextView mProgress;
    @Bind(R.id.fu)
    TextView mFu;
    @Bind(R.id.shi_o)
    ImageView mShiO;
    @Bind(R.id.fen_o)
    ImageView mFenO;
    @Bind(R.id.ge_o)
    ImageView mGeO;
    @Bind(R.id.add_new_oil_tv)
    TextView mAddNewOilTv;
    @Bind(R.id.recovery_old_oil_tv)
    TextView mRecoveryOldOilTv;
    @Bind(R.id.error_tv)
    TextView mErrorTv;
    @Bind(R.id.work_hour_tv)
    TextView mWorkHourTv;
    @Bind(R.id.work_minute_tv)
    TextView mWorkMinuteTv;
    @Bind(R.id.work_second_tv)
    TextView mWorkSecondTv;
    @Bind(R.id.top)
    ImageView mTop;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private ImageView mStarting;
    private ImageView mPStarting;
    private ImageView mStart;
    private ImageView mStop;
    private Bitmap mStartImage;
    private Bitmap mStopImage;
    private boolean isChange = true;//是否可以改变油量
    private int count = 0;
    private int failNUmber = 0;
    private ImageView mCount;
    private ImageView mCount2;
    private ImageView mCount1;
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private int[] yellowImages = {R.mipmap.number_yellow_zero, R.mipmap.number_yellow_on, R.mipmap.number_yellow_two, R.mipmap.number_yellow_three,
            R.mipmap.number_yellow_four, R.mipmap.number_yellow_five, R.mipmap.number_yellow_six, R.mipmap.number_yellow_seven, R.mipmap.number_yellow_eight,
            R.mipmap.number_yellow_nine,};
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private int millisecond = 0;
    private int second = 0;
    private int minute = 0;
    private int hour = 0;

    private Handler mHandler;
    private ImageView mGeNumber;
    private ImageView mShiNUmber;
    private ImageView mBaiNumber;
    private ImageView mQianNumber;

    private StringBuffer sb = new StringBuffer();
    private int tempDataLength = 0;
    private byte[] arr = new byte[16];
    private byte[] array = new byte[9];
    private byte[] startArray = new byte[8];

    private String mS;
    private String mAStr;
    private String mOilStr;
    private int tempcount;
    private String mMNewOilKGStr;
    private String mOldOilKGStr;
    private boolean isStart;
    private int startCount;
    private float mNewOilKG;
    private int distinguishResult;
    private int backCount;
    private int stopCount;
    private boolean cheange = true;
    private Handler mTimeHandler;
    private boolean b = true;
    private ImageView mErrorNew;
    private ImageView mErrorOld;
    private ImageView mStop1;
    private View mComplete;
    private boolean isIn = true;
    private float mOldOilKG;
    private boolean isKeep = false;
    private float mInitNewOilKG;//新油初始值
    private float mInitOldOilKG;//旧油初始值
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_depth);
        ButterKnife.bind(this);
        mHandler = new Handler();
        mTimeHandler = new Handler();
        cheange = true;
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int index) {
        tempcount = 0;
        if (index == 7) {
            System.arraycopy(buffer, 0, startArray, 0, index);
            //开始和停止
            checkDataForStartAndStop();
        }
        if (index == 16) {
            System.arraycopy(buffer, 0, arr, 0, index);
            //新油 电压
            checkData();
        }
        if (index == 8) {
            System.arraycopy(buffer, 0, array, 0, index);
            //解析换油升数的数据
            analysisChangeOilresult();
        }

    }


    //解析换油升数指令数据
    private void analysisChangeOilresult() {
        failNUmber = 0;
        String result = CheckUtil.getChangeOilNumber(array);
        //改变升数图片
        chengNumber(result);

    }

    //改变设定保养图片
    private void changeImage() {
        String s = "00" + String.valueOf(count);
        if (s.length() - 1 >= 0) {
            mCount.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mCount1.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
        if (s.length() - 3 >= 0) {
            mCount2.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 3)))));
        }
    }

    //改变升数图片
    private void chengNumber(final String s1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (s1.length() - 1 >= 0) {
                    mNumber4.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s1.charAt(s1.length() - 1)))));
                }
                if (s1.length() - 2 >= 0) {
                    mNumber3.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s1.charAt(s1.length() - 2)))));
                }
                if (s1.length() - 3 >= 0) {
                    mNumber2.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s1.charAt(s1.length() - 3)))));
                }
                if (s1.length() - 4 >= 0) {
                    mNumber1.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s1.charAt(s1.length() - 4)))));
                }
            }
        });


    }

    //解析开始停止返回的结果
    private void checkDataForStartAndStop() {
        //解析运行指令结果数据
        isStart = CheckUtil.analysisStartResult(startArray);
        switch (distinguishResult) {
            case 1:
                //返回
                if (isStart) {
                    backCount = 0;
                    finish();
                }
                break;
            case 2:
                //开始
                if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isIn = true;//每次开始都初始化,在进度是100的时候是否进入结果
                            mErrorOld.setVisibility(View.GONE);
                            mErrorNew.setVisibility(View.GONE);
                            mHandler.removeCallbacks(mRunnable);
                            mStart.setImageResource(R.mipmap.btn_start_disabled);
                            mStop.setImageResource(R.mipmap.btn_stop_n);
                            mStarting.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.VISIBLE);
                            mPStarting.setVisibility(View.GONE);
                            //开始计时
                            mHandler.postDelayed(mRunnable, 1000);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DepthActivity.this, "运行失败,请检查设置参数是否有误", Toast.LENGTH_LONG).show();
                            mStop.setImageResource(R.mipmap.btn_stop_disabled);
                            mStart.setImageResource(R.mipmap.btn_start_n);
                            mStarting.setVisibility(View.GONE);
                            mProgress.setVisibility(View.GONE);
                            mPStarting.setVisibility(View.VISIBLE);
                            //停止计时
                            mHandler.removeCallbacks(mRunnable);
                        }
                    });
                }
                break;
            case 3:
                //停止
                if (isStart) {
                    stopCount = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStop1.setVisibility(View.VISIBLE);
                            mStop.setImageResource(R.mipmap.btn_stop_disabled);
                            mStart.setImageResource(R.mipmap.btn_start_n);
                            mStarting.setVisibility(View.GONE);
                            mProgress.setVisibility(View.GONE);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mPStarting.setVisibility(View.VISIBLE);
                                    mStop1.setVisibility(View.GONE);
                                }
                            }, 1000);

                            //停止计时
                            mHandler.removeCallbacks(mRunnable);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DepthActivity.this, "停止失败,稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 6:
                if (isStart) {

                }
                break;
            default:
                break;

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
            //新油重量参数
            mNewOilKG = CheckUtil.getNewOilWeightInt(arr);
            mOldOilKG = CheckUtil.getOldOilWeightInt(arr);
            //保存新旧油初始值
            if (isKeep) {
                isKeep = false;
                mInitNewOilKG = mNewOilKG;
                mInitOldOilKG = mOldOilKG;
            }
            //得到的新油数量赋值给要改变的数据
            if (cheange) {
                count = (int) mNewOilKG / 10;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //改变设定保养图片
                        changeImage();
                    }
                });

            }
            mMNewOilKGStr = CheckUtil.getNewOilWeight(arr);
            mOldOilKGStr = CheckUtil.getOldOilWeight(arr);
            mS = CheckUtil.getWorkV(arr);
            mAStr = CheckUtil.getWorkA(arr);
            String carState = CheckUtil.getCarState(arr);
            //改变车辆状态
            chengText(carState);
            mOilStr = CheckUtil.getOilTemperature(arr);
            final String workProgress = CheckUtil.getWorkProgress(arr);

            //工作进度
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgress.setText(workProgress);
                    int i = Integer.parseInt(workProgress);
                    if (i == 100 && isIn) {
                        isIn = false;
                        distinguishResult = 6;
                        //2a 05 0b 24 23
                        int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                        CheckUtil.sendMessage(bytes, mOutputStream);

                        //新油初始值减去新油当前值/10
                        float addNew = (mInitNewOilKG - mNewOilKG) / 100;
                        float reduceOld = (mOldOilKG - mInitOldOilKG) / 100;
                        mAddNewOilTv.setText(addNew + "");
                        mRecoveryOldOilTv.setText(reduceOld + "");
                        //误差值
                        float deviation;
                        if (reduceOld > addNew) {
                            deviation = reduceOld - addNew;
                        } else {
                            deviation = addNew - reduceOld;
                        }
                        String s = deviation + "";
                        if (s.length() > 4) {
                            String result = s.substring(0, 4);
                            mErrorTv.setText(result);
                        } else {
                            mErrorTv.setText(s);
                        }

                        //时间
                        mWorkHourTv.setText("0");

                        String minute1 = hour + "" + minute;
                        if (TextUtils.isEmpty(minute1)) {
                            mWorkMinuteTv.setText("0");
                        } else {
                            mWorkMinuteTv.setText(minute1);
                        }
                        String illisecond = second + "" + millisecond;
                        mWorkSecondTv.setText(illisecond);
                        mHandler.removeCallbacks(mRunnable);

                        mComplete.setVisibility(View.VISIBLE);
                        mStop.setImageResource(R.mipmap.btn_stop_disabled);
                        mStart.setImageResource(R.mipmap.btn_start_n);
                        //mStop.setClickable(false);
                        mStarting.setVisibility(View.GONE);
                        mProgress.setVisibility(View.GONE);
                    }
                }
            });
            //改变电压电流油温图片
            chengImage();
        }

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

                if (b) {
                    b = false;

                    //2A 06 09 03 26 23
                    int[] bytes = {0x2a, 0x06, 0x09, 0x03, 0x26, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                }

            }
        });
    }

    //改变车辆状态文字
    private void chengText(final String i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (i.startsWith("0")) {
                    mCarstate.setText("熄火");
                } else if (i.startsWith("1")) {
                    mCarstate.setText("启动");
                }
                if (i.substring(1, 2).equals("1")) {
                    mErrorNew.setVisibility(View.VISIBLE);
                    mStop1.setVisibility(View.GONE);
                    mComplete.setVisibility(View.GONE);
                    mErrorOld.setVisibility(View.GONE);

                    mStop.setImageResource(R.mipmap.btn_stop_disabled);
                    mStart.setImageResource(R.mipmap.btn_start_n);

                    mStarting.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);

                    mHandler.removeCallbacks(mRunnable);
                    //发送停止命令
                    distinguishResult = 5;
                    int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);

                }
                if (i.substring(2, 3).equals("1")) {

                    mErrorOld.setVisibility(View.VISIBLE);
                    mErrorNew.setVisibility(View.GONE);
                    mStop1.setVisibility(View.GONE);
                    mComplete.setVisibility(View.GONE);

                    mStop.setImageResource(R.mipmap.btn_stop_disabled);
                    mStart.setImageResource(R.mipmap.btn_start_n);
                    mHandler.removeCallbacks(mRunnable);
                    mStarting.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);
                    distinguishResult = 5;
                    //发送停止命令2a 05 0b 24 23
                    int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);

                }
            }
        });

    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.depth_bg);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        mErrorNew = (ImageView) findViewById(R.id.error_image_new);
        mStop1 = (ImageView) findViewById(R.id.stop);
        mComplete = findViewById(R.id.complete);
        mErrorOld = (ImageView) findViewById(R.id.error_image_old);
        mComplete.setVisibility(View.GONE);

        mStarting = (ImageView) findViewById(R.id.starting);
        mPStarting = (ImageView) findViewById(R.id.pstarting);

        mStart = (ImageView) findViewById(R.id.start_shen);
        mStop = (ImageView) findViewById(R.id.stop_shen);

        mCount1 = (ImageView) findViewById(R.id.count1);
        mCount2 = (ImageView) findViewById(R.id.count2);
        mCount = (ImageView) findViewById(R.id.count0);

        mGeNumber = (ImageView) findViewById(R.id.genumber);
        mShiNUmber = (ImageView) findViewById(R.id.shinumber);
        mBaiNumber = (ImageView) findViewById(R.id.bainumber);
        mQianNumber = (ImageView) findViewById(R.id.qiannumber);

    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_10);
        mImageView.setImageBitmap(mBitmap);
        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_10_1);
        mTop.setImageBitmap(mBitmapTop);

        mStartImage = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_popovers_1);
        mStarting.setImageBitmap(mStartImage);

        mStopImage = BItmapUtil.compressBItmap(this, R.mipmap.text_img_1);
        mPStarting.setImageBitmap(mStopImage);
    }

    private void initListener() {
        mImageView.setOnTouchListener(this);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNewOilKG > 0) {
                    isKeep = true;//点击开始按钮开始保存新旧油的初始值
                    millisecond = 0;
                    second = 0;
                    minute = 0;
                    hour = 0;
                    timeChangeImage();
                    distinguishResult = 2;
                    //点击开始按钮发送以换油升数位参数的指令
                    //2a 07 01 00 7d 51 23
                    int result = 0x2a ^ 7 ^ 1 ^ count;
                    int[] bytes1 = {0x2a, 0x07, 0x01, 0x00, count, result, 0x23};
                    CheckUtil.sendMessage(bytes1, mOutputStream);
                    SystemClock.sleep(400);
                    //发送运行的指令2a 05 0a 25 23
                    int[] bytes = {0x2a, 0x05, 0x0a, 0x25, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                }
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送停止的指令2a 05 0b 24 23
                distinguishResult = 3;
                int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float y = event.getY();
        float x = event.getX();
        Log.d(TAG, "onTouch: " + x);
        if (x > 780 && x < 850 && y > 560 && y < 640) {


            distinguishResult = 1;
            //发送退出协议2a 05 0c 23 23
            int[] bytes = {0x2a, 0x05, 0x0c, 0x23, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
        if (isChange) {
            //改变保养油量
            if (x > 835 && x < 880 && y > 425 && y < 467) {
                cheange = false;
                if (count < mNewOilKG / 10) {
                    count++;
                }
                changeImage();
            } else if (x > 935 && x < 990 && y > 425 && y < 465) {
                cheange = false;
                if (count >= 6) {
                    count--;
                }
                changeImage();
            }
        }
        return false;
    }

    //改变定时器图片
    private void timeChangeImage() {
        if (millisecond > 9) {
            second++;
            millisecond = 0;
            if (second == 6) {
                minute++;
                second = 0;
                if (minute > 9) {
                    hour++;
                    minute = 0;
                    if (hour == 6) {
                        mHandler.removeCallbacks(mRunnable);
                    }
                }
            }
        }
        mGeNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, millisecond));
        mShiNUmber.setImageResource(NumberToImage.getRedImageResId(yellowImages, second));
        mBaiNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, minute));
        mQianNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, hour));
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            millisecond++;
            mHandler.postDelayed(this, 1000);
            timeChangeImage();
        }
    };

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
        mHandler.postDelayed(mTimeRunnable, 300);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面不可见移除发送数据任务
        mHandler.removeCallbacks(mRunnable);
        mTimeHandler.removeCallbacks(mTimeRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageView.setImageBitmap(null);
        mStarting.setImageBitmap(null);
        mPStarting.setImageBitmap(null);
        mBitmap.recycle();
        mStartImage.recycle();
        mStopImage.recycle();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
            mTimeHandler.removeCallbacks(mTimeRunnable);
        }
    }
}
