package com.cheanda.a8805.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.cheanda.a8805.base.MyApplication;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CheckUtil;
import com.cheanda.a8805.utils.NumberToImage;
import com.cheanda.a8805.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/5/22.
 */

public class AboutActivity extends BaseActivity implements View.OnTouchListener {
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
    @Bind(R.id.HardwareVersionTen)
    ImageView mHardwareVersionTen;
    @Bind(R.id.HardwareVersionAbit)
    ImageView mHardwareVersionAbit;
    @Bind(R.id.boardVwesionTen)
    ImageView mBoardVwesionTen;
    @Bind(R.id.boardVwesionAbit)
    ImageView mBoardVwesionAbit;
    @Bind(R.id.StatisticsOne)
    ImageView mStatisticsOne;
    @Bind(R.id.StatisticsTwo)
    ImageView mStatisticsTwo;
    @Bind(R.id.StatisticsThree)
    ImageView mStatisticsThree;
    @Bind(R.id.StatisticsFour)
    ImageView mStatisticsFour;
    @Bind(R.id.StatisticsFive)
    ImageView mStatisticsFive;
    @Bind(R.id.serialNumberOne)
    ImageView mSerialNumberOne;
    @Bind(R.id.serialNumberTwo)
    ImageView mSerialNumberTwo;
    @Bind(R.id.serialNumberThree)
    ImageView mSerialNumberThree;
    @Bind(R.id.serialNumberFour)
    ImageView mSerialNumberFour;
    @Bind(R.id.serialNumberFive)
    ImageView mSerialNumberFive;
    @Bind(R.id.serialNumberSix)
    ImageView mSerialNumberSix;
    @Bind(R.id.serialNumberSeven)
    ImageView mSerialNumberSeven;
    @Bind(R.id.serialNumberEight)
    ImageView mSerialNumberEight;
    @Bind(R.id.dateOne)
    ImageView mDateOne;
    @Bind(R.id.dateTwo)
    ImageView mDateTwo;
    @Bind(R.id.dateThree)
    ImageView mDateThree;
    @Bind(R.id.dateFour)
    ImageView mDateFour;
    @Bind(R.id.dateFive)
    ImageView mDateFive;
    @Bind(R.id.dateSix)
    ImageView mDateSix;
    @Bind(R.id.dateSeven)
    ImageView mDateSeven;
    @Bind(R.id.dateEight)
    ImageView mDateEight;
    @Bind(R.id.top)
    ImageView mTop;
    @Bind(R.id.AppVersionTen)
    ImageView mAppVersionTen;
    @Bind(R.id.AppVersionAbit)
    ImageView mAppVersionAbit;
    private ImageView mImageView;
    private Bitmap mBitmap;

    private boolean isChange = true;

    private int[] yellowImages = {R.mipmap.number_yellow_zero, R.mipmap.number_yellow_on, R.mipmap.number_yellow_two, R.mipmap.number_yellow_three,
            R.mipmap.number_yellow_four, R.mipmap.number_yellow_five, R.mipmap.number_yellow_six, R.mipmap.number_yellow_seven, R.mipmap.number_yellow_eight,
            R.mipmap.number_yellow_nine,};
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private int[] yelloLetter = {R.mipmap.yellow_a, R.mipmap.yellow_b, R.mipmap.yellow_c, R.mipmap.yellow_d,
            R.mipmap.yellow_e, R.mipmap.yellow_f};

    private StringBuffer sb = new StringBuffer();
    private Handler mTimeHandler;
    int tempcount;
    private int tempDataLength;
    private byte[] arr = new byte[16];

    private byte[] startArray = new byte[22];
    private int b = 0;
    private String mS;
    private String mAStr;
    private String mOilStr;
    private static final String TAG = "AboutActivity";
    private Integer mVersionResult;
    private Integer mMainVersionResult;
    private Long mStatisticsResult;
    private Integer mProductionDateResult;
    private Integer mProductionDateResult1;
    private Integer mProductionDateResult2;
    private String mStatistics;
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mTimeHandler = new Handler();
        initView();
        initData();
    }


    @Override
    protected void onDataReceived(byte[] buffer, int index) {
        tempcount = 0;
        if (index == 16) {
            System.arraycopy(buffer, 0, arr, 0, index);
            checkData();
        }

        if (index == 22) {
            System.arraycopy(buffer, 0, startArray, 0, index);
            checkDataForStartAndStop();
        }
    }

    //解析日期数据
    private void checkDataForStartAndStop() {
        StringBuffer sp = new StringBuffer();
        for (int i = 0; i < startArray.length; i++) {
            if (String.valueOf(startArray[i]).length() == 1) {
                sp.append("0" + startArray[i]);
            } else {
                sp.append(startArray[i]);
            }
        }
        String resultData = sp.toString();
        //42 22 09 04 10 10 00 00 00 05 05 05 05 05 05 05 05 17 04 28 56 35

        //2A 16 09 04 0A 0A CC DB 00 00 00 01 00 00 00 03 07 11 04 1C 2A 23
        //
        String data = resultData.substring(8, 40);
        //硬件版本
        String version = data.substring(0, 2);
        mVersionResult = Integer.valueOf(version, 10);
        //主控板版本
        String mainVersion = data.substring(2, 4);
        mMainVersionResult = Integer.valueOf(mainVersion, 10);
        //换油统计

        //CheckUtil.getChageOilNumber(startArray);

        String statisticsOne = data.substring(4, 6);
        int ditwo = Integer.parseInt(statisticsOne, 10);
        String statisticsTwo = data.substring(6, 8);
        int dione = Integer.parseInt(statisticsTwo, 10);
        int result = (ditwo << 8) + dione;
        mStatistics = String.valueOf(result);
        //序列号
        String serialNumber = data.substring(10, 26);
        final String suone = data.substring(10, 12);
        final String sutow = data.substring(12, 14);
        final String suthree = data.substring(14, 16);
        final String sufour = data.substring(16, 18);
        final String sufive = data.substring(18, 20);
        final String suoeat = data.substring(20, 22);
        final String suninae = data.substring(22, 24);
        final String sunion = data.substring(24, 26);
        MyApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                chengeSerialNumberImage(suone, sutow, suthree, sufour, sufive, suoeat, suninae, sunion);
            }
        });
        //年
        String productionDate = data.substring(26, 28);
        //月
        String productionDate1 = data.substring(28, 30);
        //日
        String productionDate2 = data.substring(30, 32);
        mProductionDateResult = Integer.valueOf(productionDate, 10);
        mProductionDateResult1 = Integer.valueOf(productionDate1, 10);
        mProductionDateResult2 = Integer.valueOf(productionDate2, 10);
        //设置信息
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        });
    }

    private void chengeSerialNumberImage(String suone, String sutow, String suthree, String sufour, String sufive, String suoeat, String suninae, String sunion) {
        mSerialNumberOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(suone.charAt(suone.length() - 1)))));
        mSerialNumberTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(sutow.charAt(sutow.length() - 1)))));
        mSerialNumberThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(suthree.charAt(suthree.length() - 1)))));
        mSerialNumberFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(sufour.charAt(sufour.length() - 1)))));
        mSerialNumberFive.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(sufive.charAt(sufive.length() - 1)))));
        mSerialNumberSix.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(suoeat.charAt(suoeat.length() - 1)))));
        mSerialNumberSeven.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(suninae.charAt(suninae.length() - 1)))));
        mSerialNumberEight.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(sunion.charAt(sunion.length() - 1)))));
    }

    private void setData() {
        //硬件版本
        String hardwareVersion = String.valueOf(mVersionResult);
        if (hardwareVersion.length() == 1) {
            hardwareVersion = "0" + hardwareVersion;
        }
        mHardwareVersionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(hardwareVersion.substring(0, 1))));
        mHardwareVersionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(hardwareVersion.substring(1, 2))));
        //主控版本
        String boardVwesion = String.valueOf(mMainVersionResult);
        if (boardVwesion.length() == 1) {
            boardVwesion = "0" + boardVwesion;
        }
        mBoardVwesionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(boardVwesion.substring(0, 1))));
        mBoardVwesionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(boardVwesion.substring(1, 2))));
        //换油统计
        // mStatistics = "00" + String.valueOf(mStatisticsResult);

        chengeStatisticsImage(mStatistics);

        //生产日期
        String year = String.valueOf(mProductionDateResult);
        String month = "0" + String.valueOf(mProductionDateResult1);
        String day = "0" + String.valueOf(mProductionDateResult2);
        chengeProductionDateImage(year, month, day);


    }

    private void chengeProductionDateImage(String day, String month, String year) {
        if (year.length() - 1 >= 0) {
            mDateEight.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(year.charAt(year.length() - 1)))));
        }
        if (year.length() - 2 >= 0) {
            mDateSeven.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(year.charAt(year.length() - 2)))));
        }
        if (month.length() - 1 >= 0) {
            mDateSix.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(month.charAt(month.length() - 1)))));
        }
        if (month.length() - 2 >= 0) {
            mDateFive.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(month.charAt(month.length() - 2)))));
        }
        if (day.length() - 1 >= 0) {
            mDateFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(day.charAt(day.length() - 1)))));
        }
        if (day.length() - 2 >= 0) {
            mDateThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(day.charAt(day.length() - 2)))));
        }

    }


    private void chengeStatisticsImage(String statistics) {
        if (statistics.length() - 1 >= 0) {
            mStatisticsFive.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(statistics.charAt(statistics.length() - 1)))));
        }
        if (statistics.length() - 2 >= 0) {
            mStatisticsFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(statistics.charAt(statistics.length() - 2)))));
        }
        if (statistics.length() - 3 >= 0) {
            mStatisticsThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(statistics.charAt(statistics.length() - 3)))));
        }
        if (statistics.length() - 4 >= 0) {
            mStatisticsTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(statistics.charAt(statistics.length() - 4)))));
        }
        if (statistics.length() - 5 >= 0) {
            mStatisticsOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(statistics.charAt(statistics.length() - 5)))));
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
        //验证数据是失败或成功
        if (arr[1] == 16) {
            //获取数据位  42 16 09 01 0 0 13 20 -116 0 50 01 35 00 -73 35
            //        新旧油 00 00 13 20  工作电压  116  工作电流  00 50  车辆状态  01  油温度  35   工作进度 00
            //获取工作电压
            mS = CheckUtil.getWorkV(arr);
            //获取工作电流
            mAStr = CheckUtil.getWorkA(arr);
            //获取车辆状态
            String carState = CheckUtil.getCarState(arr);

            chengText(carState);
            //获取油温
            mOilStr = CheckUtil.getOilTemperature(arr);
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

                if (b < 1) {
                    b++;
                    //发送查询系统设置信息2A 06 09 04 21 23
                    int[] bytes = {0x2a, 0x06, 0x09, 0x04, 0x21, 0x23};
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
                if (i.charAt(i.length() - 1) == '1') {
                    mCarstate.setText("启动");
                } else if (i.charAt(i.length() - 1) == '0') {
                    mCarstate.setText("熄火");
                }
                if (i.substring(0, 1).equals("1")) {
                    //Toast.makeText(AboutActivity.this, "工作过程中新油出错了", Toast.LENGTH_SHORT).show();
                }
                if (i.substring(1, 2).equals("1")) {
                    //Toast.makeText(AboutActivity.this, "工作过程中旧油出错了", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.aboutBg);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setOnTouchListener(this);
    }

    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_18);
        mImageView.setImageBitmap(mBitmap);
        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_18_1);
        mTop.setImageBitmap(mBitmapTop);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            float localVersion = Float.parseFloat(info.versionName);
            String result = String.valueOf(localVersion);
            String[] split = result.split("\\.");
            //APP版本
            setAppImage(split);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setAppImage(String[] list) {
        mAppVersionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list[1])));
        mAppVersionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list[0])));
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 1150 && x < 1260 && y > 620 && y < 680) {
            finish();
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
        //2a 05 08 27 23
        int[] bytes = {0x2a, 0x05, 0x08, 0x27, 0x23};
        CheckUtil.sendMessage(bytes, mOutputStream);

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
