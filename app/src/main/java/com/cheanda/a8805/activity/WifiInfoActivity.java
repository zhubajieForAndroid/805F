package com.cheanda.a8805.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.adapter.MyWifiAdapter;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.StringUtil;
import com.cheanda.a8805.utils.WifiUtils;
import com.cheanda.a8805.views.PassworldDialog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WifiInfoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "WifiInfoActivity";
    private ListView mListview;
    private WifiManager mManger;
    private WifiConfiguration mConfig;
    private WifiUtils mWifiUtils;
    private List<ScanResult> mWifiList;
    private PassworldDialog mDialog;
    private int mHeightPixels;
    private int mWidthPixels;
    private int mProportion;
    private TextView mTvSsid;
    private Handler mHandler;
    private View mRefreshing;
    private ImageView mBack;
    private String mApi;
    private UserDataDao mDao;
    private Bitmap mBitmapTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_wifi_info);
        mWifiUtils = new WifiUtils(this);
        Intent data = getIntent();
        String ssid = data.getStringExtra("ssid");
        String passworld = data.getStringExtra("passworld");
        int wificipher = data.getIntExtra("WIFICIPHER", 0);

        if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(passworld)){
            mWifiUtils.addNetwork(mWifiUtils.CreateWifiInfo(passworld,ssid,wificipher));
            mWifiUtils.scalWifiConfig();
        }

        //获取屏幕的宽高
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mHeightPixels = metrics.heightPixels;
        mWidthPixels = metrics.widthPixels;
        //获取屏幕的宽高比例
        mProportion = mWidthPixels/mHeightPixels;

        //="android.net.wifi.STATE_CHANGE"  监听wifi状态的变化

        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
        initView();

        mHandler = new Handler();

    }

    private void scaleWifi() {
        mWifiUtils.startScale();
        //获取烧苗结果
        mWifiList = mWifiUtils.getWifiList();
        if (mWifiList != null){
            MyWifiAdapter myWifiAdapter = new MyWifiAdapter(this,mWifiList);
            mListview.setAdapter(myWifiAdapter);
        }
        setListViewHeight();
    }

    private void initView() {
        mRefreshing =  findViewById(R.id.refreshing);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.refreshing_animation);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        mRefreshing.startAnimation(animation);

        TextView linkIng = (TextView) findViewById(R.id.tv_linkIng);
        mListview = (ListView) findViewById(R.id.wifi_listView);
        mListview.setOnItemClickListener(this);

        linkIng.setOnClickListener(this);

        mBack = (ImageView) findViewById(R.id.wifi_back);

        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.back);
        mBack.setImageBitmap(bitmap1);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //已经链接的WiFi
        mTvSsid = (TextView) findViewById(R.id.tv_ssid);

        ImageView iv = (ImageView) findViewById(R.id.wifi_top);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.topbar_3);

        iv.setImageBitmap(mBitmapTop);

    }
    //获取listview的高度,解决现实不出来的问题
    private void setListViewHeight() {
        ListAdapter adapter = mListview.getAdapter();
        if(adapter == null){
            return ;
        }
        int temp = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, mListview);
            view.measure(0,0);
            temp += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = mListview.getLayoutParams();
        layoutParams.height =  temp+ (mListview.getDividerHeight() * (mListview.getCount() - 1));

        mListview.setLayoutParams(layoutParams);
    }
    @Override
    public void onClick(View v) {
        //添加WiFi兵链接
        Intent i = new Intent(this,AddWifiActivity.class);
        startActivityForResult(i,10);
        finish();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String ssid = mWifiList.get(position).SSID;
        mDialog = new PassworldDialog(this, R.style.dialog,ssid);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        mDialog.show();
        lp.height = 230;
        lp.width = (int) (mWidthPixels * 0.4);
        mDialog.getWindow().setAttributes(lp);
        mDialog.setOnBtnListener(new PassworldDialog.OnBtnListener() {
            @Override
            public void btnListener(String passworld) {
                if (passworld == null && passworld.length()<8){
                    Toast.makeText(WifiInfoActivity.this, "密码至少8位", Toast.LENGTH_SHORT).show();
                    return;
                }
                mWifiUtils.addNetwork(mWifiUtils.CreateWifiInfo(ssid,passworld,3));
                mWifiUtils.scalWifiConfig();
                mDialog.dismiss();
            }
        });
    }
    //监听wifi状态
    private BroadcastReceiver mReceiver = new BroadcastReceiver (){
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifiInfo.isConnected()){
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String wifiSSID = wifiManager.getConnectionInfo().getSSID();
                mTvSsid.setText(wifiSSID.substring(1,wifiSSID.length()-1));
                //设置时间
                StringUtil.testDate();
            }else {
                mTvSsid.setText("wifi已断开连接");
            }
        }
    };


    private Runnable mRunnable = new Thread() {
        @Override
        public void run() {
            scaleWifi();
            mHandler.postDelayed(this,3000);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        //扫描wifi
        scaleWifi();
        mHandler.postDelayed(mRunnable,3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWifiUtils = null;
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mHandler.removeCallbacks(mRunnable);
    }

}
