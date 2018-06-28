package com.cheanda.a8805.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.Protocol;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartAppActivity extends AppCompatActivity {

    private static final String TAG = "StartAppActivity";
    private ImageView mIv;
    private Bitmap mBitmap;
    private Handler mHandler = new Handler();
    private File mApkFile;
    private String mServiceVersion;
    private String mUpdatecontent;
    private String mAppurl;
    private int linkTime = 0;
    private int maxLoadTimes = 2;
    private boolean isCancle = false;
    private String mApi;
    private UserDataDao mDao;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_start_app);
        mIv = (ImageView) findViewById(R.id.start_app_image);
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.start_bg_img_1);
        mIv.setScaleType(ImageView.ScaleType.FIT_XY);
        mIv.setImageBitmap(mBitmap);

        //执行网络请求
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
                        Toast.makeText(StartAppActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 30);
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
                            checkVersion();
                        } else {
                            Toast.makeText(StartAppActivity.this, "获取下载地址失败", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        };
        protocol.setParams(params);
        protocol.loadDataFromNet();
    }

    private void checkVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
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
                            //TODO 没有下载进入主页
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 30);
                        }
                    }
                });

            } else {
                //TODO 不需要更新进入主页
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 30);
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
                        Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
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
                try {
                    //请求成功
                    InputStream inputStream = response.body().byteStream();
                    int count = 0;
                    OutputStream os = new FileOutputStream(mApkFile);
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
                    e.printStackTrace();
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StartAppActivity.this, "下载失败,请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 30);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (isCancle) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StartAppActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mIv.setImageBitmap(null);
        if (mBitmap != null)
            mBitmap.recycle();
    }
}
