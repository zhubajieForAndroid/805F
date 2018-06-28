package com.cheanda.a8805.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Bundle;
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
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CheckUtil;
import com.cheanda.a8805.utils.FileUtil;
import com.cheanda.a8805.utils.NumberToImage;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.utils.StringUtil;
import com.cheanda.a8805.views.ActivationEquipmentDialog;
import com.cheanda.a8805.views.LockDialog;
import com.cheanda.a8805.views.MainDialog;
import com.cheanda.a8805.views.MyProgressDialog;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements View.OnTouchListener {

    private static final String TAG = "MainActivity";
    private ImageView mImageView;
    private Bitmap mBitmap;

    private String mS;
    private ImageView mFenImage;
    private ImageView mGeImage;
    private ImageView mShiImage;
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private String mAStr;
    private ImageView mFenAImage;
    private ImageView mGeAImage;
    private ImageView mShiAImage;
    private TextView mCarState;
    private String mOilStr;
    private ImageView mFenOImage;
    private ImageView mGeOImage;
    private TextView mMessageState;

    private Handler mHandler;

    private byte[] arr = new byte[16];
    private byte[] serialNumber = new byte[22];
    private byte[] startArray = new byte[8];
    private byte[] zuobiaoArray = new byte[27];

    private Boolean mIsStart = false;
    private int count;
    private int buttonSwitch;

    private StringBuffer sb = new StringBuffer();
    private StringBuffer zuobiaosb = new StringBuffer();
    private int millisecond;
    int tempDataLength = 0;
    private TextView mFu;
    private boolean isIn = false;
    private String mStatistics;
    private HashMap<String, Object> mParms;
    private Protocol mProtocol;
    private Bitmap mBitmapTop;
    private ImageView mImageViewTop;
    private String mLongitude = "0";
    private UserDataDao mDao;
    private boolean wifiIsConnect;
    private boolean equipmentIsActivation;//设备是否激活
    private ActivationEquipmentDialog mActivationDialog;//激活设备的dialog
    private boolean equipmentLockState = true;
    private LockDialog mLockDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        mActivationDialog = new ActivationEquipmentDialog(this, R.style.dialog);
        mLockDialog = new LockDialog(this, R.style.dialog);
        //初始化数据库
        mDao = new UserDataDao(this);
        initView();
        initData();
    }

    //接受串口数据
    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        millisecond = 0;
        if (size == 27) {
            System.arraycopy(buffer, 0, zuobiaoArray, 0, size);
            //解析坐标
            checkZuobiao();
        }
       /* if (size == 22) {
            System.arraycopy(buffer, 0, serialNumber, 0, size);
            checkDataForsEtting();
        }*/
        if (size == 7) {
            System.arraycopy(buffer, 0, startArray, 0, size);
            checkDataForStartAndStop();
        }
        if (size == 16) {
            System.arraycopy(buffer, 0, arr, 0, size);
            checkData();
        }
    }

    private void checkZuobiao() {
        //坐标
        mLongitude = CheckUtil.getLongitude(zuobiaoArray);
    }

    //解析换油升数和序列号
    private void checkDataForsEtting() {
        //换油统计
        int changeOilControlOne = serialNumber[6];
        int changeOilControlTwo = serialNumber[7];
        int changeOilControlResult = (changeOilControlOne << 8) + changeOilControlTwo;
        mStatistics = String.valueOf(changeOilControlResult);
        //保存的文件

        //序列号  0505050505050505
        //序列号
        final int serialNumberOne = serialNumber[9];
        final int serialNumberTeo = serialNumber[10];
        final int serialNumberThree = serialNumber[11];
        final int serialNumberFour = serialNumber[12];
        final int serialNumberFive = serialNumber[13];
        final int serialNumberSix = serialNumber[14];
        final int serialNumberSeven = serialNumber[15];
        final int serialNumberEight = serialNumber[16];
        final char i = (char) serialNumberOne;
        final char i1 = (char) serialNumberTeo;
        final char i2 = (char) serialNumberThree;
        final char i3 = (char) serialNumberFour;
        final char i4 = (char) serialNumberFive;
        final char i5 = (char) serialNumberSix;
        final char i6 = (char) serialNumberSeven;
        final char i7 = (char) serialNumberEight;
        final String resultNumber = "" + i + i1 + i2 + i3 + i4 + i5 + i6 + i7;
        if (!resultNumber.equals("00000000")) {
            SharedPreferences ap = getSharedPreferences("number", 0);
            String oilNUmber = ap.getString("oilNUmber", "0");
            if (!mStatistics.equals(oilNUmber)) {
                //上传数据
                updata(resultNumber);
            }
            File equipmentState = FileUtil.createNewFile("equipmentState");
            try {
                BufferedReader br = new BufferedReader(new FileReader(equipmentState));
                String line = br.readLine();
                br.close();
                if ("1".equals(line) && !equipmentIsActivation) {            //弹出激活画面
                    if (mActivationDialog != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivationDialog.setEquipmentID("设备ID: " + resultNumber);
                                mActivationDialog.show();
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //上传换油升数和序列号
    private void updata(String data) {
        if (!"0".equals(mLongitude)) {
            mParms = new HashMap<>();
            mParms.put("url", Constants.URLS.QUERY_UPLOAD_URL);
            mParms.put("id", data);
            mParms.put("location", mLongitude);
            mParms.put("description", mStatistics);

            //请求成功更新listview的数据
            mProtocol = new Protocol() {
                @Override
                public void errorManage(IOException e) {
                    Log.d(TAG, "errorManage: 网络异常");
                }

                @Override
                public void parseData(Gson gson, String s) {
                    Log.d(TAG, "parseData: " + s);
                }
            };
            //设置参数
            mProtocol.setParams(mParms);
            //触发请求
            mProtocol.loadDataFromNet();
            SharedPreferences number = getSharedPreferences("number", 0);
            SharedPreferences.Editor edit = number.edit();
            edit.putString("oilNUmber", mStatistics);
            edit.commit();
        }
    }

    private void checkDataForStartAndStop() {
        //解析运行指令结果数据
        mIsStart = CheckUtil.analysisStartResult(startArray);
        switch (buttonSwitch) {
            case 0:
                if (mIsStart) {
                    count = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, DepthActivity.class);
                            startActivity(intent);
                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "通讯异常,启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 1:
                if (mIsStart) {
                    count = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, AddActivity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "通讯异常,启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 3:
                //循环清洗
                if (mIsStart) {
                    count = 0;
                    Intent intent = new Intent(this, ClearActivity.class);
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "通讯异常,启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 2:
                //减少油量
                if (mIsStart) {
                    count = 0;
                    Intent intent = new Intent(this, ReduceActivity.class);
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "通讯异常,启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 6:
                //系统设置
                if (mIsStart) {
                    count = 0;
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "通讯异常,启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 4:
                //智能换油
                if (mIsStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, ChangeOilActivity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "通讯异常,启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 5:
                //获取坐标失败
                if (mIsStart) {
                    SystemClock.sleep(1000);
                    //发送查询坐标系2a 06 09 06 23 23
                    int[] bytes = {0x2a, 0x06, 0x09, 0x06, 0x23, 0x23};
                    CheckUtil.sendMessage(bytes, mOutputStream);
                }
                break;
        }
    }

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
                    mMessageState.setText("正常");
                    mMessageState.setTextColor(Color.parseColor("#00ff00"));
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
            //获取设备激活状态
            String equipmentState = CheckUtil.getEquipmentState(arr);
            String result = equipmentState.substring(1, 2);
            final String lockState = equipmentState.substring(0, 1);

            if ("0".equals(result)) {
                //设备没有激活
                equipmentIsActivation = false;
            } else {
                //设备已经激活了
                equipmentIsActivation = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("1".equals(lockState)) {
                            if (equipmentLockState) {
                                equipmentLockState = false;
                                mLockDialog.show();
                            }
                        } else {
                            equipmentLockState = true;
                            if (mLockDialog != null)
                                mLockDialog.dismiss();
                        }
                        if (mActivationDialog != null){
                            mActivationDialog.dismiss();
                        }

                    }
                });
            }
            chengText(carState);
            //获取油温
            mOilStr = CheckUtil.getOilTemperature(arr);
            chengImage();
        }
    }

    //改变车辆状态文字
    private void chengText(final String i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (i.startsWith("0")) {
                    mCarState.setText("熄火");
                } else {
                    mCarState.setText("启动");
                }
                if (i.endsWith("1")) {
                    isIn = true;
                } else {
                    isIn = false;
                }
            }
        });

    }


    private void chengImage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //电压
                if (mS.length() - 1 >= 0) {
                    mFenImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 1)))));
                }
                if (mS.length() - 2 >= 0) {
                    mGeImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 2)))));
                }
                if (mS.length() - 3 >= 0) {
                    mShiImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 3)))));
                }
                //电流
                if (mAStr.length() - 2 >= 0) {
                    mShiAImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mAStr.charAt(mAStr.length() - 2)))));
                }
                if (mAStr.length() - 3 >= 0) {
                    mGeAImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mAStr.charAt(mAStr.length() - 3)))));
                }
                if (mAStr.length() - 4 >= 0) {
                    mFenAImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mAStr.charAt(mAStr.length() - 4)))));
                }
                if (mOilStr.length() >= 2) {

                }
                if (mOilStr.startsWith("-")) {
                    mFu.setText("-");
                    //油温度
                    if (mOilStr.length() - 1 >= 0) {
                        mGeOImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 1)))));
                    }
                    if (mOilStr.length() - 2 >= 0) {
                        mFenOImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 2)))));
                    }
                } else {
                    mFu.setText("");
                    if (mOilStr.length() - 1 >= 0) {
                        mGeOImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 1)))));
                    }
                    if (mOilStr.length() - 2 >= 0) {
                        mFenOImage.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 2)))));
                    }
                }


            }
        });
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.home_bg);
        mImageViewTop = (ImageView) findViewById(R.id.top);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.home_bg_img_1);
        //读取公司名称
        //读取Api名字
        setTopBar();
        mImageView.setImageBitmap(mBitmap);
        mImageViewTop.setImageBitmap(mBitmapTop);
        View view = findViewById(R.id.main_include);
        /*电压*/
        //十位
        mFenImage = (ImageView) view.findViewById(R.id.fen);
        //个位
        mGeImage = (ImageView) view.findViewById(R.id.ge);
        //小数点位
        mShiImage = (ImageView) view.findViewById(R.id.shi);
        /*电流*/
        mFenAImage = (ImageView) view.findViewById(R.id.fen_a);
        mGeAImage = (ImageView) view.findViewById(R.id.ge_a);
        mShiAImage = (ImageView) view.findViewById(R.id.shi_a);
        /*车辆状态*/
        mCarState = (TextView) view.findViewById(R.id.tv_state);
        /*油温度*/
        mFenOImage = (ImageView) view.findViewById(R.id.fen_o);
        mGeOImage = (ImageView) view.findViewById(R.id.ge_o);
        mFu = (TextView) view.findViewById(R.id.fu);


        /*通讯状态*/
        mMessageState = (TextView) view.findViewById(R.id.tv_message_state);

        //监听wifi连接的状态
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
    }

    //监听wifi状态
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo.isConnected()) {
                wifiIsConnect = true;
                //wifi连接成功,设置时间
                StringUtil.testDate();
            } else {
                wifiIsConnect = false;
            }
        }
    };

    private void setTopBar() {
        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.home_bg_img_1_1);
    }

    private void initData() {
        mImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 195 && x < 335 && y > 185 && y < 322) {
            if (isIn) {
                SharedPreferences sp = getSharedPreferences("phoneFile", 0);
                String phone = sp.getString("phone", "");
                Bitmap bitmap = BItmapUtil.compressBItmap(MainActivity.this, R.mipmap.btn_img_popovers_14);
                if (!TextUtils.isEmpty(phone)) {
                    MainDialog dialog = new MainDialog(MainActivity.this, R.style.dialog, bitmap, phone);
                    dialog.show();
                } else {
                    MainDialog dialog = new MainDialog(MainActivity.this, R.style.dialog, bitmap, "");
                    dialog.show();
                }
            } else {
                buttonSwitch = 0;
                //发送进入深度保养的指令
                int[] bytes = {0x2a, 0x07, 0x01, 0x00, 0x00, 0x2c, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            }
        } else if (x > 440 && x < 580 && y > 185 && y < 322) {
            buttonSwitch = 1;
            //发送进入添加油量的指令2a 06 02 05 2b 23
            int[] bytes = {0x2a, 0x06, 0x02, 0x05, 0x2b, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        } else if (x > 945 && x < 1085 && y > 185 && y < 322) {
            buttonSwitch = 3;
            //进入x循环清洗2a 06 04 05 2d 23
            int[] bytes = {0x2a, 0x06, 0x04, 0x05, 0x2d, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        } else if (x > 700 && x < 832 && y > 185 && y < 322) {
            buttonSwitch = 2;
            //进入减少油量发送以0.5升为参数的数据2a 06 03 05 2a 23
            int[] bytes = {0x2a, 0x06, 0x03, 0x05, 0x2a, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        } else if (x > 181 && x < 312 && y > 460 && y < 595) {
            if (isIn) {
                SharedPreferences sp = getSharedPreferences("phoneFile", 0);
                String phone = sp.getString("phone", "");
                Bitmap bitmap = BItmapUtil.compressBItmap(MainActivity.this, R.mipmap.btn_img_popovers_14);
                if (!TextUtils.isEmpty(phone)) {
                    MainDialog dialog = new MainDialog(MainActivity.this, R.style.dialog, bitmap, phone);
                    dialog.show();
                } else {
                    MainDialog dialog = new MainDialog(MainActivity.this, R.style.dialog, bitmap, "");
                    dialog.show();
                }
            } else {
                buttonSwitch = 4;
                //进入一键智能换油发送 2a 05 05 2a 23
                int[] bytes = {0x2a, 0x05, 0x05, 0x2a, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            }
        } else if (x > 437 && x < 575 && y > 460 && y < 595) {
            buttonSwitch = 5;
            Intent intent = new Intent(this, EmptyOilActivity.class);
            startActivity(intent);
        } else if (x > 695 && x < 835 && y > 460 && y < 595) {
            buttonSwitch = 6;
            //发送进入系统设置的命令2a 05 08 27 23
            int[] bytes = {0x2a, 0x05, 0x08, 0x27, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);

        } else if (x > 950 && x < 1090 && y > 460 && y < 595) {
            Intent intent = new Intent(this, SelectIntentActivity.class);
            startActivity(intent);
           /*Intent intent = new Intent(this, DataActivity.class);
            startActivity(intent);*/

        }
        return false;
    }


    private Runnable mRunnable = new Thread() {
        @Override
        public void run() {
            millisecond++;
            if (millisecond > 3) {
                //三次没有数据显示异常
                mMessageState.setText("异常");
                mMessageState.setTextColor(Color.parseColor("#ff0000"));
            }
            mHandler.postDelayed(this, 1000);
            //发送查询
            //wifi已经连接发送0x2a, 0x06, 0x09, 0x01, 0x24, 0x23
            //wifi没有连接发送//0x2a, 0x06, 0x09, 0x06, 0x23, 0x23
           /* if (wifiIsConnect) {
                int[] bytes = {0x2a, 0x06, 0x09, 0x01, 0x24, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            } else {
                int[] bytes = {0x2a, 0x06, 0x09, 0x06, 0x23, 0x23};
                CheckUtil.sendMessage(bytes, mOutputStream);
            }*/
            int[] bytes = {0x2a, 0x06, 0x09, 0x01, 0x24, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //页面可见发送数据
        mHandler.postDelayed(mRunnable, 1000);
        sendQuery();
        SystemClock.sleep(50);
        sendQueryCoordinate();
    }

    private void sendQueryCoordinate() {
        buttonSwitch = 5;
        //发送查询坐标系2a 06 09 06 23 23
        int[] bytes = {0x2a, 0x06, 0x09, 0x06, 0x23, 0x23};
        CheckUtil.sendMessage(bytes, mOutputStream);
    }

    private void sendQuery() {
        //发送查询系统设置信息2A 06 09 04 21 23
        int[] bytes = {0x2a, 0x06, 0x09, 0x04, 0x21, 0x23};
        CheckUtil.sendMessage(bytes, mOutputStream);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面不可见移除发送数据任务
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageView.setImageBitmap(null);
        mBitmap.recycle();
        unregisterReceiver(mReceiver);
    }


}
