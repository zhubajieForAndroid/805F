package com.cheanda.a8805.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.adapter.MyWifiAdapter;
import com.cheanda.a8805.adapter.NavigationAdapter;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.StringUtil;
import com.cheanda.a8805.utils.WifiUtils;
import com.cheanda.a8805.views.PassworldDialog;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "NavigationActivity";
    private List<View> mList = new ArrayList<>();
    private int currentItem = 0; // 当前图片的位置
    private int mFlaggingWidth;
    private WifiUtils mWifiUtils;
    private int mHeightPixels;
    private int mWidthPixels;
    private int mProportion;
    private Handler mHandler;
    private View mRefreshing;
    private ListView mListview;
    private ImageView mBack;
    private TextView mTvSsid;
    private Bitmap mBitmapTop;
    private List<ScanResult> mWifiList;
    private PassworldDialog mDialog;
    private boolean wifiIsConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_navigation);
        initView();
    }

    private void initView() {
        final ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        final ImageView iv3 = (ImageView) findViewById(R.id.iv3);


        View viewOne = View.inflate(this,R.layout.view_naivgation_one,null);
        View viewTwo = View.inflate(this,R.layout.view_naivgation_two,null);
        View viewThree = View.inflate(this,R.layout.activity_wifi_info,null);
        mList.add(viewOne);
        mList.add(viewTwo);
        mList.add(viewThree);
        initWifiData(viewThree);//初始化wifi信息
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        NavigationAdapter adapter = new NavigationAdapter(mList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentItem = position;
            }
            //选中
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    iv2.setImageResource(R.mipmap.open_select);
                    iv3.setImageResource(R.mipmap.open_select);
                }
                if (position == 1) {
                    iv2.setImageResource(R.mipmap.open);
                    iv3.setImageResource(R.mipmap.open_select);
                }
                if (position == 2){
                    iv3.setImageResource(R.mipmap.open);
                }
            }

            //状态改变
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
            }
        });


    }

    private void initWifiData(View view) {
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
        initViews(view);

        mHandler = new Handler();
        //扫描wifi
        scaleWifi();
        mHandler.postDelayed(mRunnable,3000);
    }

    private void initViews(View view) {
        mRefreshing =  view.findViewById(R.id.refreshing);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.refreshing_animation);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        mRefreshing.startAnimation(animation);

        TextView linkIng = (TextView) view.findViewById(R.id.tv_linkIng);
        mListview = (ListView)view.findViewById(R.id.wifi_listView);
        mListview.setOnItemClickListener(this);

        linkIng.setOnClickListener(this);

        mBack = (ImageView)view.findViewById(R.id.wifi_back);

        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.home_tab_right_btn_01);
        mBack.setImageBitmap(bitmap1);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(NavigationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        });
        //已经链接的WiFi
        mTvSsid = (TextView)view.findViewById(R.id.tv_ssid);

        ImageView iv = (ImageView) view.findViewById(R.id.wifi_top);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.topbar_3);

        iv.setImageBitmap(mBitmapTop);
    }
    @Override
    public void onClick(View v) {
        /*//添加WiFi兵链接
        Intent i = new Intent(this,AddWifiActivity.class);
        startActivityForResult(i,10);*/
    }
    //监听wifi状态
    private BroadcastReceiver mReceiver = new BroadcastReceiver (){
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifiInfo.isConnected()){
                wifiIsConnect = true;
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String wifiSSID = wifiManager.getConnectionInfo().getSSID();
                mTvSsid.setText(wifiSSID.substring(1,wifiSSID.length()-1));
                //设置时间
                StringUtil.testDate();
            }else {
                wifiIsConnect = false;
                mTvSsid.setText("wifi已断开连接");
            }
        }
    };

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
                    Toast.makeText(NavigationActivity.this, "密码至少8位", Toast.LENGTH_SHORT).show();
                    return;
                }
                mWifiUtils.addNetwork(mWifiUtils.CreateWifiInfo(ssid,passworld,3));
                mWifiUtils.scalWifiConfig();
                mDialog.dismiss();
            }
        });
    }
    private Runnable mRunnable = new Thread() {
        @Override
        public void run() {
            scaleWifi();
            mHandler.postDelayed(this,3000);
        }
    };
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mHandler.removeCallbacks(mRunnable);
    }
}
