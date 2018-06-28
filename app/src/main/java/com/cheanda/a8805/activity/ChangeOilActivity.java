package com.cheanda.a8805.activity;

import android.content.Intent;
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
import android.widget.LinearLayout;
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
public class ChangeOilActivity extends BaseActivity implements View.OnTouchListener {

    private static final String TAG = "ChangeOilActivity";
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
    @Bind(R.id.qiannumber)
    ImageView mQiannumber;
    @Bind(R.id.time)
    ImageView mTime;
    @Bind(R.id.time1)
    ImageView mTime1;
    @Bind(R.id.Surplus1)
    ImageView mSurplus1;
    @Bind(R.id.Surplus2)
    ImageView mSurplus2;
    @Bind(R.id.result_image)
    ImageView mResultImage;
    @Bind(R.id.addOne)
    ImageView mAddOne;
    @Bind(R.id.addTwo)
    ImageView mAddTwo;
    @Bind(R.id.addThree)
    ImageView mAddThree;
    @Bind(R.id.contter)
    LinearLayout mContter;
    @Bind(R.id.top)
    ImageView mTop;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private ImageView mStarting;
    private View mPStarting;
    private ImageView mStart;
    private ImageView mStop;
    private Bitmap mStartImage;
    private Bitmap mStopImage;
    private boolean isChange = true;//是否可以改变油量

    private int failNUmber = 0;
    private ImageView mCount;
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private int[] yellowImages = {R.mipmap.number_yellow_zero, R.mipmap.number_yellow_on, R.mipmap.number_yellow_two, R.mipmap.number_yellow_three,
            R.mipmap.number_yellow_four, R.mipmap.number_yellow_five, R.mipmap.number_yellow_six, R.mipmap.number_yellow_seven, R.mipmap.number_yellow_eight,
            R.mipmap.number_yellow_nine,};
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private int millisecond = 0;//毫秒时间
    private int second;

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


    private int mNewOilKG;
    private int distinguishResult;
    private int backCount;
    private int stopCount;
    private boolean cheange = true;
    private Handler mTimeHandler;
    private boolean b = true;
    private int count = 1;
    private int clearTime = 2;
    private int surplusOil = 5;
    private ImageView mPStartingImage;
    private int workcount = 1;
    private boolean isNext = false;
    private long mTimeMillis;
    boolean c = true;
    private int initNewOilWeight;
    private Integer mOldOilKG;
    private int initOldOilWeight;
    private int mWork;
    private boolean isStartResult = false;
    private boolean isData = false;
    private boolean isChangeOilResult = false;
    private ImageView mErrorNew;
    private ImageView mStop1;
    private ImageView mErrorOld;
    private ImageView mComplete;
    private Bitmap mBitmapTop;
    private boolean isStoping = true;//是否停止接受进度
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_dchangeoil);
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
                    finish();
                }
                break;
            case 2:
                if (isStart) {
                    //运行
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isData = true;
                            isStoping = true;//每次开始都初始化,在进度是100的时候是否进入结果
                            mErrorOld.setVisibility(View.GONE);
                            mErrorNew.setVisibility(View.GONE);
                            mStarting.setImageBitmap(mStartImage);//每次开始都把图片换成第一步的图片
                            mStart.setImageResource(R.mipmap.btn_start_disabled);
                            mStop.setImageResource(R.mipmap.btn_stop_n);
                            mStarting.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.VISIBLE);
                            mPStarting.setVisibility(View.GONE);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangeOilActivity.this, "运行失败,请检查设定油量参数", Toast.LENGTH_LONG).show();
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
                            millisecond = 0;//点击停止 置0清洗时间
                            second = 0;
                            timeChangeImage();//改变时间图片
                            mProgress.setText("0");
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
            case 4:
                //清洗时间,自己算进度
                if (isStart) {
                    mHandler.postDelayed(mRunnable, 1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BItmapUtil.compressBItmap(ChangeOilActivity.this, R.mipmap.btn_img_popovers_13);
                            mStarting.setImageBitmap(bitmap);
                        }
                    });

                }
                break;
            case 5:
                isNext = true;
                mHandler.removeCallbacks(mRunnable);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStop.setImageResource(R.mipmap.btn_stop_disabled);//正在等量换油不能停止
                        mStop.setClickable(false);
                        mStart.setClickable(false);
                        mProgress.setText("0");
                        Bitmap bitmap = BItmapUtil.compressBItmap(ChangeOilActivity.this, R.mipmap.btn_img_popovers_12);
                        mStarting.setImageBitmap(bitmap);
                    }
                });
                break;
            case 7:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mContter.setVisibility(View.VISIBLE);
                    }
                });
                break;

            case 9:
                if (isStart) {
                    //获取结束的时间
                    long l = System.currentTimeMillis();
                    long resultCount = l - mTimeMillis;
                    finish();
                    Intent intent = new Intent(this, ChangeResultActivity.class);
                    intent.putExtra("time", resultCount);
                    intent.putExtra("newOil", initNewOilWeight);
                    intent.putExtra("oldOil", initOldOilWeight);
                    startActivity(intent);
                }
                break;
            case 10:
                if (isStart) {
                    isChangeOilResult = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContter.setVisibility(View.GONE);
                            Bitmap bitmap = BItmapUtil.compressBItmap(ChangeOilActivity.this, R.mipmap.btn_img_popovers_12);
                            mStarting.setImageBitmap(bitmap);
                        }
                    });

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
            if (isData) {
                isData = false;
                initNewOilWeight = mNewOilKG;
                initOldOilWeight = mOldOilKG;
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

            mWork = Integer.parseInt(workProgress);
            if (mWork != 0) {
                //工作进度
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setText(workProgress);
                    }
                });
            }
            if (mWork == 100) {
                //发送下一步2a 05 15 3A 23收到成功之后自己算清洗时间进度,清洗时间结束后再发送下一步命令,再收到进度100的时候
                //再发送下一步命令2a 05 15 3A 23
                int[] bytes1 = {0x2a, 0x05, 0x15, 0x3a, 0x23};
                CheckUtil.sendMessage(bytes1, mOutputStream);
                distinguishResult = 4;
                if (isNext) {
                    distinguishResult = 7;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.setText("0");
                        }
                    });
                    //发送进入下一步
                    int[] bytes = {0x2a, 0x05, 0x15, 0x3a, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                    if (isChangeOilResult) {
                        //启动最终结果页面2a 05 22 0f 23
                        distinguishResult = 9;
                        //2a 05 0b 24 23
                        int[] bytes2 = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                        CheckUtil.sendMessage(bytes2, mOutputStream);
                    }

                }
                if (isStartResult) {
                    //获取结束的时间
                    long l = System.currentTimeMillis();
                    long resultCount = l - mTimeMillis;
                    finish();
                    Intent intent = new Intent(this, ChangeResultActivity.class);
                    intent.putExtra("time", resultCount);
                    intent.putExtra("newOil", initNewOilWeight);
                    intent.putExtra("oldOil", initOldOilWeight);
                    startActivity(intent);

                }
            }
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
                    mStarting.setVisibility(View.GONE);
                    mStop.setImageResource(R.mipmap.btn_stop_disabled);
                    mStart.setImageResource(R.mipmap.btn_start_n);
                    mStarting.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);
                    distinguishResult = 11;
                    //发送停止命令2a 05 0b 24 23
                    int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                }
                if (i.substring(2, 3).equals("1")) {
                    mErrorOld.setVisibility(View.VISIBLE);
                    mErrorNew.setVisibility(View.GONE);
                    mStop1.setVisibility(View.GONE);
                    mComplete.setVisibility(View.GONE);
                    mStarting.setVisibility(View.GONE);
                    mStop.setImageResource(R.mipmap.btn_stop_disabled);
                    mStart.setImageResource(R.mipmap.btn_start_n);
                    mStarting.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);
                    distinguishResult = 11;
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
        mStarting = (ImageView) findViewById(R.id.starting);
        mPStartingImage = (ImageView) findViewById(R.id.pstarting_image);
        mPStarting = findViewById(R.id.pstarting);
        mStart = (ImageView) findViewById(R.id.start_shen);
        mStop = (ImageView) findViewById(R.id.stop_shen);

        mCount = (ImageView) findViewById(R.id.count);
        mGeNumber = (ImageView) findViewById(R.id.genumber);
        mShiNUmber = (ImageView) findViewById(R.id.shinumber);
        mBaiNumber = (ImageView) findViewById(R.id.bainumber);
        mQianNumber = (ImageView) findViewById(R.id.qiannumber);

        View view = findViewById(R.id.include_one);
        mErrorNew = (ImageView) view.findViewById(R.id.error_image_new);
        mStop1 = (ImageView) view.findViewById(R.id.stop);
        mComplete = (ImageView) view.findViewById(R.id.complete);
        mErrorOld = (ImageView) view.findViewById(R.id.error_image_old);

    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_24);
        mImageView.setImageBitmap(mBitmap);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_23_1);

        mTop.setImageBitmap(mBitmapTop);

        mStartImage = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_popovers_11);
        mStarting.setImageBitmap(mStartImage);

        mStopImage = BItmapUtil.compressBItmap(this, R.mipmap.text_img_5);
        mPStartingImage.setImageBitmap(mStopImage);

        Bitmap bitmap = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_popovers_15);
        mResultImage.setImageBitmap(bitmap);
        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_1_1);
        mAddOne.setImageBitmap(bitmap1);
        Bitmap bitmap2 = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_1_2);
        mAddTwo.setImageBitmap(bitmap2);
        Bitmap bitmap3 = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_1_3);
        mAddThree.setImageBitmap(bitmap3);

        mAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加0.2升2a 05 20 0f 23
                int[] bytes = {0x2a, 0x05, 0x20, 0x0f, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            }
        });
        mAddTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //减少0.2升2a 05 20 0c 23
                int[] bytes = {0x2a, 0x05, 0x21, 0x0e, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            }
        });
        mAddThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击继续,启动等量交换剩余油量
                //发送 2a 05 22 0e 23
                if (mNewOilKG >= 20) {
                    isStartResult = true;
                    distinguishResult = 10;
                    int[] bytes1 = {0x2a, 0x05, 0x22, 0x0d, 0x23};
                    CheckUtil.sendMessage(bytes1, mOutputStream);
                } else {
                    //启动最终结果页面2a 05 22 0f 23
                    distinguishResult = 9;
                    //2a 05 0b 24 23
                    int[] bytes2 = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                    CheckUtil.sendMessage(bytes2, mOutputStream);
                }
            }
        });
    }

    private void initListener() {
        mImageView.setOnTouchListener(this);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((count * 100 + surplusOil * 10) > mNewOilKG) {
                    Toast.makeText(ChangeOilActivity.this, "设置参数有无", Toast.LENGTH_SHORT).show();
                } else {
                    //获取当前时间
                    mTimeMillis = System.currentTimeMillis();

                    distinguishResult = 2;
                    //点击开始按钮发送以清洗油量和剩余油量下发
                    //2a 07 05 01 05 2c 23
                    int clearOilresult = count;
                    int surplusOilNUmberResult = surplusOil;
                    int result = 0x2a ^ 7 ^ 5 ^ clearOilresult ^ surplusOilNUmberResult;
                    int[] bytes = {0x2a, 0x07, 0x05, clearOilresult, surplusOilNUmberResult, result, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                    SystemClock.sleep(400);
                    //发送运行的指令2a 05 0a 25 23
                    int[] bytes1 = {0x2a, 0x05, 0x0a, 0x25, 0x23};
                    CheckUtil.sendMessage(bytes1, mOutputStream);
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
        if (x > 780 && x < 855 && y > 610 && y < 680) {
            distinguishResult = 1;
            //发送退出协议2a 05 0c 23 23
            int[] bytes = {0x2a, 0x05, 0x0c, 0x23, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
        if (isChange) {
            //改变保养油量
            if (x > 785 && x < 830 && y > 348 && y < 395) {
                if (count == 1) {
                    count++;
                }
                changeImage();
            } else if (x > 880 && x < 927 && y > 344 && y < 394) {
                if (count == 2) {
                    count--;
                }
                changeImage();
            } else if (x > 779 && x < 825 && y > 428 && y < 475) {
                if (clearTime < 10) {
                    clearTime++;
                }
                changeTimeImage();
            } else if (x > 875 && x < 925 && y > 430 && y < 470) {
                if (clearTime > 1) {
                    clearTime--;
                }
                changeTimeImage();
            } else if (x > 780 && x < 830 && y > 513 && y < 555) {
                if (surplusOil < 10) {
                    surplusOil++;
                }
                changeSurplusImage();
            } else if (x > 880 && x < 925 && y > 515 && y < 562) {
                if (surplusOil > 5) {
                    surplusOil--;
                }
                changeSurplusImage();
            }
        }
        return false;
    }

    //改变剩余油量图片
    private void changeSurplusImage() {
        String s = "0" + String.valueOf(surplusOil);
        if (s.length() - 1 >= 0) {
            mSurplus2.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mSurplus1.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
    }

    //改变清洗时间图片
    private void changeTimeImage() {
        String s = "0" + String.valueOf(clearTime);
        if (s.length() - 1 >= 0) {
            mTime1.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mTime.setImageResource(NumberToImage.getRedImageResId(images, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
    }

    //定时器改变图片
    private void timeChangeImage() {
        String s = "0" + String.valueOf(millisecond);
        if (s.length() - 1 >= 0) {
            mGeNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mShiNUmber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
        if (millisecond == 60) {
            second++;
            millisecond = 0;
            String str = "0" + String.valueOf(second);
            if (str.length() - 1 >= 0) {
                mBaiNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(str.charAt(str.length() - 1)))));
            }
            if (str.length() - 2 >= 0) {
                mQianNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(str.charAt(str.length() - 2)))));
            }
            mGeNumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, 0));
            mShiNUmber.setImageResource(NumberToImage.getRedImageResId(yellowImages, 0));
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!(second == clearTime)) {
                millisecond++;
            }
            float c = millisecond + second * 60;
            c = (c / (clearTime * 60));
            int v = (int) (c * 100);
            mProgress.setText("" + v);
            if (v == 100) {
                distinguishResult = 5;
                //发送下一步2a 05 15 3A 23
                int[] bytes = {0x2a, 0x05, 0x15, 0x3a, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);

            }
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
        mPStartingImage.setImageBitmap(null);
        mBitmap.recycle();
        mStartImage.recycle();
        mStopImage.recycle();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
            mTimeHandler.removeCallbacks(mTimeRunnable);
        }
    }
}
