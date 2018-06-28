package com.cheanda.a8805.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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

public class EmptyOldOil1Activity extends BaseActivity implements View.OnTouchListener {

    private static final String TAG = "EmptyNewOilActivity";
    @Bind(R.id.number1)
    ImageView mNumber1;
    @Bind(R.id.number2)
    ImageView mNumber2;
    @Bind(R.id.number3)
    ImageView mNumber3;
    @Bind(R.id.number4)
    ImageView mNumber4;
    @Bind(R.id.message)
    TextView mMessage;
    @Bind(R.id.carstate)
    TextView mCarstate;
    @Bind(R.id.qiannumber)
    ImageView mQiannumber;
    @Bind(R.id.bainumber)
    ImageView mBainumber;
    @Bind(R.id.shinumber)
    ImageView mShinumber;
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
    @Bind(R.id.pstarting)
    ImageView mPstarting;

    @Bind(R.id.progress)
    TextView mProgress;
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
    @Bind(R.id.fu)
    TextView mFu;
    @Bind(R.id.shi_o)
    ImageView mShiO;
    @Bind(R.id.fen_o)
    ImageView mFenO;
    @Bind(R.id.ge_o)
    ImageView mGeO;
    @Bind(R.id.top)
    ImageView mTop;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private ImageView mStarting;
    private ImageView mStart;
    private ImageView mStop;
    private boolean isChange = true;
    private Bitmap mStartImage;
    private int[] images = {R.mipmap.number_red_zero, R.mipmap.number_red_on, R.mipmap.number_red_two, R.mipmap.number_red_three,
            R.mipmap.number_red_four, R.mipmap.number_red_five, R.mipmap.number_red_six, R.mipmap.number_red_seven, R.mipmap.number_red_eight,
            R.mipmap.number_red_nine,};
    private int[] yellowImages = {R.mipmap.number_yellow_zero, R.mipmap.number_yellow_on, R.mipmap.number_yellow_two, R.mipmap.number_yellow_three,
            R.mipmap.number_yellow_four, R.mipmap.number_yellow_five, R.mipmap.number_yellow_six, R.mipmap.number_yellow_seven, R.mipmap.number_yellow_eight,
            R.mipmap.number_yellow_nine,};
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};

    private StringBuffer sb = new StringBuffer();
    private boolean isStart = false;
    private Handler mTimeHandler;
    int tempcount;
    private int tempDataLength;
    private byte[] arr = new byte[16];
    private byte[] array = new byte[9];
    private byte[] startArray = new byte[8];
    private int failNUmber;
    private int distinguishResult;
    private Integer mOldNuber;
    private boolean b = true;
    private String mMNewOilKGStr;
    private String mOldOilKGStr;
    private String mS;
    private String mAStr;
    private String mOilStr;
    private int millisecond;
    private Handler mHandler;
    private int second;
    private boolean c = false;
    private Integer initNewOilWeight;
    private ImageView mErrorNew;
    private ImageView mStop1;
    private ImageView mComplete;
    private ImageView mErrorOld;
    private Bitmap mBitmapTop;
    private boolean isStoping = true;//是否停止接受进度是100%
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_empty_new_oil);
        ButterKnife.bind(this);
        mTimeHandler = new Handler();
        mHandler = new Handler();
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
                //开始
                if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStop.setClickable(true);
                            mErrorOld.setVisibility(View.GONE);
                            mErrorNew.setVisibility(View.GONE);
                            mStart.setImageResource(R.mipmap.btn_start_disabled);
                            mStop.setImageResource(R.mipmap.btn_stop_n);
                            mStarting.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.VISIBLE);
                            mHandler.postDelayed(mRunnable, 1000);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EmptyOldOil1Activity.this, "运行失败,稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 3:
                //停止
                if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStop1.setVisibility(View.VISIBLE);
                            mStarting.setVisibility(View.GONE);
                            mStop.setImageResource(R.mipmap.btn_stop_disabled);
                            mStart.setImageResource(R.mipmap.btn_start_n);
                            mStarting.setVisibility(View.GONE);
                            mProgress.setVisibility(View.GONE);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mStop1.setVisibility(View.GONE);
                                    mPstarting.setVisibility(View.GONE);
                                }
                            }, 3000);
                            mHandler.removeCallbacks(mRunnable);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EmptyOldOil1Activity.this, "停止失败,稍后重试", Toast.LENGTH_SHORT).show();
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
        analysisStateResult(resultData);
    }

    //解析电压电流油温的数据
    private void analysisStateResult(String resultData) {
        //校验位成功
        if (arr[1] == 16) {
            //旧油重量参数
            mOldNuber = CheckUtil.getOldOilWeightInt(arr);
            final String workProgress = CheckUtil.getWorkProgress(arr);
            //工作进度
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgress.setText(workProgress);
                    int i = Integer.parseInt(workProgress);
                    if (i == 100 && isStoping) {
                        isStoping = false;
                        mStop.setImageResource(R.mipmap.btn_stop_disabled);
                        mStart.setImageResource(R.mipmap.btn_start_n);
                        mStarting.setVisibility(View.GONE);
                        mProgress.setVisibility(View.GONE);
                        mHandler.removeCallbacks(mRunnable);
                        mComplete.setVisibility(View.VISIBLE);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mComplete.setVisibility(View.GONE);
                            }
                        }, 3000);
                    }
                }
            });
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
                } else {
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
                    mHandler.removeCallbacks(mRunnable);
                    mStop.setClickable(false);
                    distinguishResult = 5;
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
                    mHandler.removeCallbacks(mRunnable);
                    mStop.setClickable(false);
                    distinguishResult = 5;
                    //发送停止命令2a 05 0b 24 23
                    int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);

                }
            }
        });

    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.empty_new_bg);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        mStarting = (ImageView) findViewById(R.id.starting);

        mStart = (ImageView) findViewById(R.id.start_shen);
        mStop = (ImageView) findViewById(R.id.stop_shen);


        mErrorNew = (ImageView) findViewById(R.id.error_image_new);
        mStop1 = (ImageView) findViewById(R.id.stop);
        mComplete = (ImageView) findViewById(R.id.complete);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.btn_img_popovers_1_2);
        mErrorOld = (ImageView) findViewById(R.id.error_image_old);
        mErrorOld.setImageBitmap(bitmap);
        mErrorOld.setImageBitmap(bitmap);
        mComplete.setVisibility(View.GONE);

    }

    private void initListener() {
        mImageView.setOnTouchListener(this);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOldNuber > 0) {
                    c = true;
                    millisecond = 0;
                    distinguishResult = 2;
                    timeChangeImage();
                    //mHandler.removeCallbacks(mTimeRunnable);
                    //发送运行的指令2a 05 0a 25 23
                    int[] bytes = {0x2a, 0x05, 0x0a, 0x25, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                }
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = false;
                distinguishResult = 3;
                //mHandler.removeCallbacks(mTimeRunnable);
                //发送停止的指令2a 05 0b 24 23
                int[] bytes = {0x2a, 0x05, 0x0b, 0x24, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);

            }
        });
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_15);
        mImageView.setImageBitmap(mBitmap);
        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_15_1);

        mTop.setImageBitmap(mBitmapTop);
        mStartImage = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_popovers_6);
        mStarting.setImageBitmap(mStartImage);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 780 && x < 850 && y > 560 && y < 640) {
            distinguishResult = 1;
            //发送退出协议2a 05 0c 23 23
            int[] bytes = {0x2a, 0x05, 0x0c, 0x23, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
        return false;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            millisecond++;
            mHandler.postDelayed(this, 1000);
            timeChangeImage();
        }
    };

    private void timeChangeImage() {
        String s = "0" + String.valueOf(millisecond);
        if (s.length() - 1 >= 0) {
            mGenumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)))));
        }
        if (s.length() - 2 >= 0) {
            mShinumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(s.charAt(s.length() - 2)))));
        }
        if (millisecond == 59) {
            second++;
            millisecond = 0;
            String str = "0" + String.valueOf(second);
            if (str.length() - 1 >= 0) {
                mBainumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(str.charAt(str.length() - 1)))));
            }
            if (str.length() - 2 >= 0) {
                mQiannumber.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(str.charAt(str.length() - 2)))));
            }

        }
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
        mImageView.setImageBitmap(null);
        mBitmap.recycle();

    }
}

