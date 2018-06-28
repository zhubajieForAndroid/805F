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
import com.cheanda.a8805.utils.FileUtil;
import com.cheanda.a8805.utils.NumberToImage;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.utils.StringUtil;
import com.cheanda.a8805.utils.ToastUtil;
import com.cheanda.a8805.views.ActivationEquipmentDialog;
import com.cheanda.a8805.views.LoginDialog;
import com.cheanda.a8805.views.OnlineDialog;
import com.cheanda.a8805.views.SaveDialog;
import com.cheanda.a8805.views.StstemSettingDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SystemSettingActivity extends BaseActivity implements View.OnTouchListener, StstemSettingDialog.OnBtnListener {

    private static final String TAG = "SystemSettingActivity";
    @Bind(R.id.SystemSettingBg)
    ImageView mSystemSettingBg;
    @Bind(R.id.HardwareVersionTen)
    ImageView mHardwareVersionTen;
    @Bind(R.id.HardwareVersionAbit)
    ImageView mHardwareVersionAbit;
    @Bind(R.id.AppVersionTen)
    ImageView mAppVersionTen;
    @Bind(R.id.AppVersionAbit)
    ImageView mAppVersionAbit;
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
    @Bind(R.id.on)
    ImageView mOn;
    @Bind(R.id.off)
    ImageView mOff;
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
    @Bind(R.id.top)
    ImageView mTop;
    private Bitmap mBitmap;
    private int[] yellowImages = {R.mipmap.number_yellow_zero, R.mipmap.number_yellow_on, R.mipmap.number_yellow_two, R.mipmap.number_yellow_three,
            R.mipmap.number_yellow_four, R.mipmap.number_yellow_five, R.mipmap.number_yellow_six, R.mipmap.number_yellow_seven, R.mipmap.number_yellow_eight,
            R.mipmap.number_yellow_nine,};
    private int[] yelloLetter = {R.mipmap.yellow_a, R.mipmap.yellow_b, R.mipmap.yellow_c, R.mipmap.yellow_d,
            R.mipmap.yellow_e, R.mipmap.yellow_f};
    private boolean isSelect = true;
    private LoginDialog mLonindialog;

    private StringBuffer sb = new StringBuffer();
    private Handler mTimeHandler;
    int tempcount;
    private int tempDataLength;
    private byte[] arr = new byte[16];
    private byte[] startArray = new byte[8];
    private byte[] serialNumber = new byte[22];
    private int b = 0;
    private String mS;
    private String mAStr;
    private String mOilStr;
    private Integer mVersionResult;
    private Integer mMainVersionResult;
    private Long mStatisticsResult;
    private Integer mProductionDateResult;
    private Integer mProductionDateResult1;
    private Integer mProductionDateResult2;
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private int onOffCount = 1;//开关值
    private String Hardwaredata = "0";//硬件版本数据
    private String mainData = "0";//主控板版本
    private String huanDagta = "0";//换油控制
    private String xuData;//序列号
    private String data;//生产日期
    private boolean ishuanDagtaCheng;
    private boolean isxuDataCheng;
    private String mStatistics;
    private Boolean isStart = false;
    private HashMap<String, Object> mParms;
    private Protocol mProtocol;
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        mTimeHandler = new Handler();
        initListener();
        initData();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int index) {
        //三次
        tempcount = 0;
        if (index == 22) {
            System.arraycopy(buffer, 0, serialNumber, 0, index);
            checkDataForStartAndStop();
        }
        if (index == 7) {
            System.arraycopy(buffer, 0, startArray, 0, index);
            checkDataForResult();
        }
        if (index == 16) {
            System.arraycopy(buffer, 0, arr, 0, index);
            checkData();
        }
    }

    private void checkDataForResult() {
        isStart = CheckUtil.analysisStartResult(startArray);
        if (isStart) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showMessage("保存成功");
                }
            });

            finish();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showMessage("保存失败");
                }
            });
        }
    }

    //解析日期数据
    private void checkDataForStartAndStop() {
        StringBuffer sp = new StringBuffer();
        for (int i = 0; i < serialNumber.length; i++) {
            if (String.valueOf(serialNumber[i]).length() == 1) {
                sp.append("0" + serialNumber[i]);
            } else {
                sp.append(serialNumber[i]);
            }
        }
        String resultData = sp.toString();
        //0a0a 82000000 0505050505050505 11041c
        String data = resultData.substring(8, 40);
        //硬件版本
        mVersionResult = (int) serialNumber[4];
        //主控版本
        mMainVersionResult = (int) serialNumber[5];
        //换油统计
        int changeOilControlOne = serialNumber[6];
        int changeOilControlTwo = serialNumber[7];
        int changeOilControlResult = (changeOilControlOne << 8) + changeOilControlTwo;
        mStatistics = String.valueOf(changeOilControlResult);
        //换油控制开关
        int changeOilControlSwitch = serialNumber[8];
        if (changeOilControlSwitch == 1) {
            onOffCount = 1;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmapOn = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_off_s_1);
                    Bitmap bitmapOff = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_on_d_1);
                    mOff.setImageBitmap(bitmapOff);
                    mOn.setImageBitmap(bitmapOn);
                }
            });
        } else {
            onOffCount = 0;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmapOn = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_off_d_1);
                    Bitmap bitmapOff = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_on_s_1);
                    mOn.setImageBitmap(bitmapOn);
                    mOff.setImageBitmap(bitmapOff);
                }
            });

        }
        //序列号  0505050505050505
        final String suone = data.substring(10, 12);
        final String sutow = data.substring(12, 14);
        final String suthree = data.substring(14, 16);
        final String sufour = data.substring(16, 18);
        final String sufive = data.substring(18, 20);
        final String suoeat = data.substring(20, 22);
        final String suninae = data.substring(22, 24);
        final String sunion = data.substring(24, 26);
        //去0
        String substring = suone.substring(1, 2);
        String substring1 = sutow.substring(1, 2);
        String substring2 = suthree.substring(1, 2);
        String substring3 = sufour.substring(1, 2);
        String substring4 = sufive.substring(1, 2);
        String substring5 = suoeat.substring(1, 2);
        String substring6 = suninae.substring(1, 2);
        String substring7 = sunion.substring(1, 2);
        xuData = substring + substring1 + substring2 + substring3 + substring4 + substring5 + substring6 + substring7;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chengeSerialNumberImage(suone, sutow, suthree, sufour, sufive, suoeat, suninae, sunion);
            }
        });

        //年月日
        int year = serialNumber[17];
        int month = serialNumber[18];
        int day = serialNumber[19];
        mProductionDateResult = year;
        mProductionDateResult1 = month;
        mProductionDateResult2 = day;
        //设置信息
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        });
    }

    private void setData() {
        //硬件版本
        String hardwareVersion = String.valueOf(mVersionResult);
        Hardwaredata = hardwareVersion;
        if (hardwareVersion.length() == 1) {
            hardwareVersion = "0" + hardwareVersion;
        }
        mHardwareVersionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(hardwareVersion.substring(0, 1))));
        mHardwareVersionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(hardwareVersion.substring(1, 2))));
        //主控版本
        String boardVwesion = String.valueOf(mMainVersionResult);
        mainData = boardVwesion;
        if (boardVwesion.length() == 1) {
            boardVwesion = "0" + boardVwesion;
        }
        mBoardVwesionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(boardVwesion.substring(0, 1))));
        mBoardVwesionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(boardVwesion.substring(1, 2))));
        //换油统计
        //mStatistics = String.valueOf(mStatisticsResult);
        huanDagta = String.valueOf(mStatistics);

        chengeStatisticsImage(mStatistics);

        //生产日期
        String year = String.valueOf(mProductionDateResult);
        if (year.length() == 1) {
            year = "0" + year;
        }
        String month = String.valueOf(mProductionDateResult1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = String.valueOf(mProductionDateResult2);
        if (day.length() == 1) {
            day = "0" + day;
        }
        chengeProductionDateImage(year, month, day);
        data = "20" + year + month + day;

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
        statistics = "0000" + statistics;
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

            }
        });

    }

    private void initListener() {
        mSystemSettingBg.setOnTouchListener(this);
        mOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOffCount = 0;
                Bitmap bitmapOn = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_off_d_1);
                Bitmap bitmapOff = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_on_s_1);
                mOn.setImageBitmap(bitmapOn);
                mOff.setImageBitmap(bitmapOff);

            }
        });
        mOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOffCount = 1;
                Bitmap bitmapOn = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_off_s_1);
                Bitmap bitmapOff = BItmapUtil.compressBItmap(SystemSettingActivity.this, R.mipmap.btn_switch_on_d_1);
                mOff.setImageBitmap(bitmapOff);
                mOn.setImageBitmap(bitmapOn);

            }
        });


    }


    private void initData() {
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_20);
        mSystemSettingBg.setImageBitmap(mBitmap);


        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_20_1);

        mTop.setImageBitmap(mBitmapTop);
        Bitmap bitmapOn = BItmapUtil.compressBItmap(this, R.mipmap.btn_switch_off_s_1);
        Bitmap bitmapOff = BItmapUtil.compressBItmap(this, R.mipmap.btn_switch_on_d_1);
        mOn.setImageBitmap(bitmapOn);
        mOff.setImageBitmap(bitmapOff);

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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x > 460 && x < 689 && y > 120 && y < 180) {
            StstemSettingDialog dialog = new StstemSettingDialog(this, R.style.dialog, "硬件版本");
            dialog.show();
            //设置输入框输入的最大长度
            dialog.setEdInputSize(2);
            dialog.setOnBtnListener(new StstemSettingDialog.OnBtnListener() {
                @Override
                public void btnListener(String data) {
                    if (data.length() < 2) {
                        ToastUtil.showMessage("硬件版本不能小于2位");
                        return;
                    }
                    Hardwaredata = data;
                    List<String> list = StringUtil.subStringToList(data);
                    //硬件版本
                    setHardwareImage(list);
                }
            });
        } else if (x > 1115 && x < 1250 && y > 120 && y < 160) {      //开启上线

            onLine();

        } else if (x > 455 && x < 684 && y > 325 && y < 375) {
            StstemSettingDialog dialog = new StstemSettingDialog(this, R.style.dialog, "主控板版本");
            dialog.show();
            //设置输入框输入的最大长度
            dialog.setEdInputSize(2);
            dialog.setOnBtnListener(new StstemSettingDialog.OnBtnListener() {
                @Override
                public void btnListener(String data) {
                    if (data.length() < 2) {
                        ToastUtil.showMessage("主控板版本不能小于2位");
                        return;
                    }
                    mainData = data;
                    List<String> list = StringUtil.subStringToList(data);
                    //主控板版本
                    setBoardImage(list);
                }
            });
        } else if (x > 455 && x < 980 && y > 420 && y < 470) {
            //弹出输入密码页面
            //模拟密码
            int intOil = Integer.parseInt(mStatistics);
            StringBuffer resultPassworld = new StringBuffer();
            if (intOil > 9) {
                String passworld = mStatistics;
                StringBuffer sb = new StringBuffer(passworld);
                //倒序
                String string = sb.reverse().toString();
                //取出每个字符
                List<String> list = StringUtil.subStringToList(string);
                //最后一位+1
                String s = (Integer.parseInt(list.get(list.size() - 1)) + 1) + "";
                //删除最后一位
                list.remove(list.size() - 1);
                list.add(list.size(), s);
                for (int i = 0; i < list.size(); i++) {
                    resultPassworld.append(list.get(i));
                }
            } else {
                resultPassworld.append(intOil + 1);
            }
            mLonindialog = new LoginDialog(this, R.style.dialog, resultPassworld.toString());
            mLonindialog.show();
            //读取换油控制的数据,设置到dialog中
            mLonindialog.setOnBtnListener(new LoginDialog.OnBtnListener() {
                @Override
                public void btnListener(Boolean b) {
                    if (b) {
                        mLonindialog.dismiss();
                        //密码正确
                        StstemSettingDialog dialog = new StstemSettingDialog(SystemSettingActivity.this, R.style.dialog, "换油控制");
                        dialog.show();
                        dialog.setEdInputSize(7);
                        dialog.setIsStyle(true);
                        dialog.setOnBtnListener(SystemSettingActivity.this);
                    }
                }
            });


        } else if (x > 1060 && x < 1163 && y > 511 && y < 575) {         //删除id号
            Bitmap bitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_popovers_02);
            final SaveDialog dialog = new SaveDialog(this, R.style.dialog, bitmap);
            dialog.show();
            dialog.setOnButtonListener(new SaveDialog.OnButtonListener() {
                @Override
                public void iscancle(boolean b) {
                    if (b) {
                        xuData = "00000000";
                        chengeSerialNumberImage("0", "0", "0", "0", "0", "0", "0", "0");
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                    }
                }
            });

        } else if (x > 460 && x < 1100 && y > 615 && y < 668) {
            StstemSettingDialog dialog = new StstemSettingDialog(this, R.style.dialog, "生产日期");
            dialog.show();
            //设置输入框输入的最大长度
            dialog.setEdInputSize(8);
            dialog.setOnBtnListener(new StstemSettingDialog.OnBtnListener() {
                @Override
                public void btnListener(String data) {
                    if (data.length() == 8) {
                        int b = Integer.parseInt(data);
                        SystemSettingActivity.this.data = String.valueOf(b);
                        List<String> list = StringUtil.subStringToList(data);
                        //生产日期
                        setDateImage(list);
                    } else {
                        ToastUtil.showMessage("正确格式如:20170526");
                    }
                }
            });
        } else if (x > 1145 && x < 1258 && y > 615 && y < 670) {
            //开关值onOffCount10进制硬件Hardwaredata16进制主板mainData16进制换油huanDagta10进制序列号xuData10进制日期data16进制
            if (!TextUtils.isEmpty(Hardwaredata) && !TextUtils.isEmpty(mainData) && !TextUtils.isEmpty(huanDagta) && !TextUtils.isEmpty(xuData)) {
                int Hardwaredatastr = Integer.parseInt(Hardwaredata, 10);//硬件版本
                int mainDatastr = Integer.parseInt(mainData, 10);//主控版本

                int huanDagtastr = Integer.parseInt(huanDagta, 10);//换油控制
                int onOffCountstr = Integer.parseInt(String.valueOf(onOffCount), 10);//开关

                // List<String> list = StringUtil.subStringToList(xuData);//序列号3位的序列号
                List<String> list = StringUtil.subStringToList(xuData);
                String dier = data.substring(2, 4); //生产日期
                int i1 = Integer.parseInt(dier);
                String gaoyi = data.substring(4, 6);
                int i2 = Integer.parseInt(gaoyi);
                String gao = data.substring(6, 8);
                int i3 = Integer.parseInt(gao);
                //低位
                int di16 = huanDagtastr & 0x000000FF;
                //2a 15 12 0a 0a   00 00   00   00 00 01 00 00 00 01 06   11 04 1c   22 23
                int gao16 = (huanDagtastr & 0x0000FF00) >> 8;

                int result = 0x2a ^ 0x15 ^ 0x12 ^ Hardwaredatastr ^ mainDatastr ^ gao16 ^ di16 ^ onOffCountstr ^ Integer.parseInt(list.get(0)) ^ Integer.parseInt(list.get(1)) ^ Integer.parseInt(list.get(2)) ^
                        Integer.parseInt(list.get(3)) ^ Integer.parseInt(list.get(4)) ^ Integer.parseInt(list.get(5)) ^ Integer.parseInt(list.get(6)) ^ Integer.parseInt(list.get(7)) ^
                        i3 ^ i1 ^ i2;
                int[] bytes = {0x2a, 0x15, 0x12, Hardwaredatastr, mainDatastr, gao16, di16, onOffCountstr, Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)),
                        Integer.parseInt(list.get(3)), Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)),
                        Integer.parseInt(list.get(6)), Integer.parseInt(list.get(7)), i1, i2, i3, result, 0x23};

                CheckUtil.sendMessage(bytes, mOutputStream);
            }
        }
        return false;
    }

    private void onLine() {
        OnlineDialog dialog = new OnlineDialog(this, R.style.dialog);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnEquipmentIDlistener(new OnlineDialog.OnEquipmentIDlistener() {
            @Override
            public void idListener(String id) {
                setNumberImage(id);
                xuData = id;
                File equipmentState = FileUtil.createNewFile("equipmentState");
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(equipmentState));
                    bw.write("1");
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSystemSettingBg.setImageBitmap(null);
        mBitmap.recycle();
    }

    @Override
    public void btnListener(String data) {
        int i = Integer.parseInt(data);
        if (i > 65535) {
            ToastUtil.showMessage("输入的数据过大");
            return;
        }
        huanDagta = "0000" + data;
        //换油控制
        setStatisticsImage(huanDagta);
    }

    private void setStatisticsImage(String data) {
        if (isNumeric(data)) {
            List<String> list = StringUtil.subStringToList(data);
            if (list.size() - 1 >= 0) {
                mStatisticsFive.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 1))));
            }
            if (list.size() - 2 >= 0) {
                mStatisticsFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 2))));
            }
            if (list.size() - 3 >= 0) {
                mStatisticsThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 3))));
            }
            if (list.size() - 4 >= 0) {
                mStatisticsTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 4))));
            }
            if (list.size() - 5 >= 0) {
                mStatisticsOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 5))));
            }
        }

    }

    public void setHardwareImage(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        if (isNumeric(sb.toString())) {
            if (list.size() - 1 >= 0) {
                mHardwareVersionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 1))));
            }
            if (list.size() - 2 >= 0) {
                mHardwareVersionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 2))));
            }
        }

    }

    public void setAppImage(String[] list) {
        mAppVersionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list[1])));
        mAppVersionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list[0])));
    }

    public void setBoardImage(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        if (isNumeric(sb.toString())) {
            if (list.size() - 1 >= 0) {
                mBoardVwesionAbit.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 1))));
            }
            if (list.size() - 2 >= 0) {
                mBoardVwesionTen.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 2))));
            }
        }

    }

    public void setNumberImage(String id) {
        switch (id.charAt(3)) {
            case 'A':
                mSerialNumberFour.setImageResource(yelloLetter[0]);
                break;
            case 'B':
                mSerialNumberFour.setImageResource(yelloLetter[1]);
                break;
            case 'C':
                mSerialNumberFour.setImageResource(yelloLetter[2]);
                break;
            case 'D':
                mSerialNumberFour.setImageResource(yelloLetter[3]);
                break;
            case 'E':
                mSerialNumberFour.setImageResource(yelloLetter[4]);
                break;
            case 'F':
                mSerialNumberFour.setImageResource(yelloLetter[5]);
                break;
            default:
                if (id.length() - 5 >= 0) {
                    mSerialNumberFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 5)))));
                }
                break;
        }
        if (id.length() - 1 >= 0) {
            mSerialNumberEight.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 1)))));
        }
        if (id.length() - 2 >= 0) {
            mSerialNumberSeven.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 2)))));
        }
        if (id.length() - 3 >= 0) {
            mSerialNumberSix.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 3)))));
        }
        if (id.length() - 4 >= 0) {
            mSerialNumberFive.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 4)))));
        }
        if (id.length() - 6 >= 0) {
            mSerialNumberThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 6)))));
        }
        if (id.length() - 7 >= 0) {
            mSerialNumberTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 7)))));
        }
        if (id.length() - 8 >= 0) {
            mSerialNumberOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.parseInt(String.valueOf(id.charAt(id.length() - 8)))));
        }

    }

    public void setDateImage(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        if (isNumeric(sb.toString())) {
            if (list.size() - 1 >= 0) {
                mDateEight.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 1))));
            }
            if (list.size() - 2 >= 0) {
                mDateSeven.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 2))));
            }
            if (list.size() - 3 >= 0) {
                mDateSix.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 3))));
            }
            if (list.size() - 4 >= 0) {
                mDateFive.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 4))));
            }
            if (list.size() - 5 >= 0) {
                mDateFour.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 5))));
            }
            if (list.size() - 6 >= 0) {
                mDateThree.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 6))));
            }
            if (list.size() - 7 >= 0) {
                mDateTwo.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 7))));
            }
            if (list.size() - 8 >= 0) {
                mDateOne.setImageResource(NumberToImage.getRedImageResId(yellowImages, Integer.valueOf(list.get(list.size() - 8))));
            }
        }

    }

    //判断是否是数字
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
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
}
