package com.cheanda.a8805.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
import com.cheanda.a8805.utils.NumberToImage;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.views.LoginDialog;
import com.cheanda.a8805.views.MyProgressDialog;
import com.cheanda.a8805.views.PhoneDialog;
import com.cheanda.a8805.views.UpdataDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SettingActivity extends BaseActivity implements View.OnTouchListener {

    private static final String TAG = "SettingActivity";
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
    private int count = 0;
    private long mStartTime;


    private StringBuffer sb = new StringBuffer();
    int tempDataLength = 0;
    private byte[] arr = new byte[16];
    private byte[] startArray = new byte[8];
    private boolean isStart;
    private int distinguishResult;
    private int backCount;
    private Handler mHandler;
    private int tempcount;
    private String mS;
    private String mAStr;
    private String mOilStr;
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private int mServerVersion;
    private String mUpdataContent;
    private String mApkUrl;
    private File mApkFile;
    private String mServiceVersion;
    private String mUpdatecontent;
    private String mAppurl;
    private int linkTime = 0;
    private int maxLoadTimes = 2;

    private boolean isCancle = false;
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mHandler = new Handler();
        //Apk下载路径
        mApkFile = new File(Environment.getExternalStorageDirectory(), "车安达.apk");
        initView();
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
    }

    //解析开始停止返回的结果
    private void checkDataForStartAndStop() {
        //解析运行指令结果数据
        isStart = CheckUtil.analysisStartResult(startArray);

        switch (distinguishResult) {
            case 5:
                //返回
                if (isStart) {
                    finish();
                }
                break;
            case 1:
                //新油电子秤
                if (isStart) {
                    Intent intent = new Intent(this, NewOIlCalibrationOneActivity.class);
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingActivity.this, "启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 2:
                //旧油电子秤
                if (isStart) {
                    Intent intent = new Intent(this, OldCalibrationOneActivity.class);
                    startActivity(intent);
                } else {
                    count++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingActivity.this, "启动失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 3:
                //一键清零新油
                if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap newSuccess = BItmapUtil.compressBItmap(SettingActivity.this, R.mipmap.popovers_new_1);
                            final MyProgressDialog dialog = new MyProgressDialog(SettingActivity.this, R.style.dialog, newSuccess);
                            dialog.show();

                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap newFail = BItmapUtil.compressBItmap(SettingActivity.this, R.mipmap.popovers_new_2);
                            MyProgressDialog dialog = new MyProgressDialog(SettingActivity.this, R.style.dialog, newFail);
                            dialog.show();

                        }
                    });

                }
                break;
            case 4:
                //一键清零旧油
                if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap oldSuccess = BItmapUtil.compressBItmap(SettingActivity.this, R.mipmap.popovers_old_1);
                            MyProgressDialog dialog = new MyProgressDialog(SettingActivity.this, R.style.dialog, oldSuccess);
                            dialog.show();

                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap oldFail = BItmapUtil.compressBItmap(SettingActivity.this, R.mipmap.popovers_old_2);
                            MyProgressDialog dialog = new MyProgressDialog(SettingActivity.this, R.style.dialog, oldFail);
                            dialog.show();

                        }
                    });

                }
                break;
            case 6:
                if (isStart) {
                    Intent intent = new Intent(SettingActivity.this, SystemSettingActivity.class);
                    startActivity(intent);
                }
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

    //解析电压电流数据
    private void analysisStateResult() {
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
            }
        });

    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.setting_bg);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_settings_1);
        mImageView.setImageBitmap(mBitmap);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_settings_1_1);

        mTop.setImageBitmap(mBitmapTop);
    }

    private void initData() {
        mImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 70 && x < 207 && y > 182 && y < 305) {
            distinguishResult = 1;
            //发送进入新油电子秤校准2a 05 0d 22 23
            int[] bytes = {0x2a, 0x05, 0x0d, 0x22, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);

        } else if (x > 325 && x < 465 && y > 180 && y < 295) {
            distinguishResult = 2;
            //发送进入旧油秤校准2a 05 0e 21 23
            int[] bytes = {0x2a, 0x05, 0x0e, 0x21, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        } else if (x > 590 && x < 725 && y > 175 && y < 305) {
            Bitmap bitmap = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_popovers_18);
            MyProgressDialog dialog = new MyProgressDialog(SettingActivity.this, R.style.dialog, bitmap);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            dialog.setOnButtonListener(new MyProgressDialog.OnButtonListener() {
                @Override
                public void iscancle(boolean b) {
                    if (!b) {
                        distinguishResult = 3;
                        //发送一键清零新油2a 05 0f 20 23
                        int[] bytes1 = {0x2a, 0x05, 0x0f, 0x20, 0x23};
                        CheckUtil.sendMessage(bytes1, mOutputStream);
                    }
                }
            });


        } else if (x > 845 && x < 970 && y > 180 && y < 296) {
            Bitmap bitmap = BItmapUtil.compressBItmap(this, R.mipmap.btn_img_popovers_19);
            MyProgressDialog dialog = new MyProgressDialog(SettingActivity.this, R.style.dialog, bitmap);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            dialog.setOnButtonListener(new MyProgressDialog.OnButtonListener() {
                @Override
                public void iscancle(boolean b) {
                    if (!b) {
                        distinguishResult = 4;
                        //发送一键清零旧油 2a 05 10 3f 23
                        int[] bytes = {0x2a, 0x05, 0x10, 0x3f, 0x23};
                        CheckUtil.sendMessage(bytes, mOutputStream);
                    }
                }
            });


        } else if (x > 1085 && x < 1219 && y > 182 && y < 290) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (x > 75 && x < 210 && y > 430 && y < 545) {
            Bitmap bitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_normal_22);
            PhoneDialog dialog = new PhoneDialog(this, R.style.dialog, bitmap);
            dialog.show();

        } else if (x > 835 && x < 970 && y > 423 && y < 542) {

            distinguishResult = 5;
            //发送返回2a 05 0c 23 23
            int[] bytes = {0x2a, 0x05, 0x0c, 0x23, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        } else if (x > 324 && x < 465 && y > 424 && y < 550) {
            //启动wifi链接
            Intent intent = new Intent(this, WifiInfoActivity.class);
            startActivity(intent);
        } else if (x > 587 && x < 720 && y > 420 && y < 545) {
            //检查更新
            checkVersion();
        } else if (x > 1000 && x < 1078 && y > 730 && y < 792) {
            if (count == 0) {
                mStartTime = System.currentTimeMillis();
            }
            count++;
            if (count == 5) {
                count = 0;
                long stopTime = System.currentTimeMillis();
                if ((stopTime - mStartTime) < 2000) {
                    final LoginDialog systemDialog = new LoginDialog(this, R.style.dialog, "");
                    systemDialog.show();
                    systemDialog.setOnLoninnListener(new LoginDialog.OnLonInListener() {
                        @Override
                        public void loginListener(Boolean b) {
                            if (b) {
                                distinguishResult = 6;
                                //隐藏系统设置命令2a 05 23 0c 23
                                int[] bytes1 = {0x2a, 0x05, 0x23, 0x0c, 0x23};
                                CheckUtil.sendMessage(bytes1, mOutputStream);

                            }
                        }
                    });
                }
            }
        }
        return false;
    }

    //检查版本升级
    private void checkVersion() {
        //网络请求版本信息
        performNetRequest();
    }

    //执行网络请求
    private void performNetRequest() {
        Map<String, Object> params = new HashMap<>();
        params.put("appname", "805F中性");
        params.put("url", Constants.URLS.CHECKAPK_URL);
        //okhttp发起网络请求
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                try {
                    JSONArray jsonArray = new JSONObject(s).getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mServiceVersion = jsonObject.getString("appver");
                        mUpdatecontent = jsonObject.getString("updatecontent");
                        mAppurl = jsonObject.getString("appurl");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(mAppurl)) {
                            contrastVersion();
                        } else {
                            Toast.makeText(SettingActivity.this, "获取下载地址失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        };
        protocol.setParams(params);
        protocol.loadDataFromNet();
    }

    //对比服务器和本地版本
    private void contrastVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            float localVersion = Float.parseFloat(info.versionName);
            float mServerVersionInt = Float.parseFloat(mServiceVersion);
            if (mServerVersionInt > localVersion) {
                //需要更新,弹出更新对话框
                final UpdataDialog dialog = new UpdataDialog(this, R.style.dialog, mUpdatecontent);
                dialog.setCanceledOnTouchOutside(false);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                dialog.show();
                dialog.getWindow().setAttributes(lp);
                dialog.setDownLaodButtonListener(new UpdataDialog.OnDownloadButtonListener() {
                    @Override
                    public void isDownload(boolean b) {
                        if (b) {
                            dialog.dismiss();
                            //下载Apk
                            downloadApk();
                        } else {
                            dialog.dismiss();

                        }
                    }
                });

            } else {
                Toast.makeText(SettingActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //下载Apk
    private void downloadApk() {
        final ProgressDialog dialog = new ProgressDialog(this);
        //设置进度条风格，风格为圆形，旋转的
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final OkHttpClient.Builder client = new OkHttpClient.Builder().readTimeout(100, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
        final OkHttpClient build = client.build();
        Request request = new Request.Builder().get().url(mAppurl).build();
        build.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SettingActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 30);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File path = new File(Environment.getExternalStorageDirectory(), "cad");
                if (!path.exists()) {
                    path.mkdir();
                }
                //Apk下载路径
                mApkFile = new File(path, "cheanda.apk");
                long length = response.body().contentLength();
                float resultKB = length / 1024;
                dialog.setMax((int) resultKB);
                //请求成功
                InputStream inputStream = response.body().byteStream();
                OutputStream os = null;
                try {
                    int count = 0;
                    os = new FileOutputStream(mApkFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        count += len;
                        os.write(buffer, 0, len);
                        float result = count / 1024;
                        dialog.setProgress((int) result);
                        if (dialog.getProgress() == resultKB) {
                            isCancle = true;
                        }
                    }
                    inputStream.close();
                    os.close();
                    if (isCancle) {
                        dialog.dismiss();
                        //下载完成,调用安装
                        installApk();
                    }

                } catch (IOException e) {
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingActivity.this, "下载失败,请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void installApk() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(mApkFile), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private Runnable mTimeRunnable = new Runnable() {
        @Override
        public void run() {
            tempcount++;
            if (tempcount > 3) {
                //三次没有数据显示异常
                mMessage.setText("异常");
                mMessage.setTextColor(Color.parseColor("#ff0000"));
            }
            mHandler.postDelayed(this, 300);
            //发送查询
            int[] bytes = {0x2a, 0x06, 0x09, 0x01, 0x24, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //发送查询
        mHandler.postDelayed(mTimeRunnable, 300);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面不可见移除发送数据任务
        mHandler.removeCallbacks(mTimeRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageView.setImageBitmap(null);
        mBitmap.recycle();
    }
}
