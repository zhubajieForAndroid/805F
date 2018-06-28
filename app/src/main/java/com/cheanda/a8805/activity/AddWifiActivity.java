package com.cheanda.a8805.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.WifiUtils;
import com.cheanda.a8805.views.SecurityDialog;

import java.util.List;

public class AddWifiActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AddWifiActivity";
    //R.layout.activity_add_wifi

    private EditText mPassworld;
    private EditText mName;
    private WifiUtils mWifiUtils;
    private ProgressDialog mDialog;
    private boolean isPassworld = false;
    private TextView mSecurity;
    //WiFi的加密方式
    private int WIFICIPHER_NOPASS = 1;
    private int WIFICIPHER_WEP = 2;
    private int WIFICIPHER_WPA = 3;

    private int WIFICIPHER;
    private String mApi;
    private UserDataDao mDao;
    private Bitmap mBitmapTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_add_wifi);


        initView();
        initData();
    }

    private void initData() {
        ImageView iv = (ImageView) findViewById(R.id.add_top);
        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.topbar_3);
        iv.setImageBitmap(mBitmapTop);
    }

    private void initView() {
        mWifiUtils = new WifiUtils(this);
        final View con = findViewById(R.id.conter_passworld);
        mSecurity = (TextView) findViewById(R.id.security);
        findViewById(R.id.content_security).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecurityDialog dialog = new SecurityDialog(AddWifiActivity.this, R.style.dialog);
                dialog.show();
                dialog.setOnRedioButtpmnListener(new SecurityDialog.RedioButtpmnListener() {
                    @Override
                    public void onButtonListener(String text) {
                        mSecurity.setText(text);
                        if ("WEP".equals(text) | "PA/WPA2 PSK".equals(text)) {
                            isPassworld = true;
                            con.setVisibility(View.VISIBLE);
                        }
                        if ("WEP".equals(text)) {
                            WIFICIPHER = WIFICIPHER_WEP;
                        }
                        if ("PA/WPA2 PSK".equals(text)) {
                            WIFICIPHER = WIFICIPHER_WPA;
                        }
                        if ("开放".equals(text)) {
                            WIFICIPHER = WIFICIPHER_NOPASS;
                        }
                    }
                });
            }
        });

        mPassworld = (EditText) findViewById(R.id.passworld);
        mName = (EditText) findViewById(R.id.userName);

        Button cancle = (Button) findViewById(R.id.cancle);
        Button link = (Button) findViewById(R.id.link);
        cancle.setOnClickListener(this);
        link.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                finish();
                break;
            case R.id.link:
                String ssid = mName.getText().toString().trim();
                String passworld = mPassworld.getText().toString().trim();
                if (isPassworld) {
                    if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(passworld)) {
                        Intent data = new Intent(AddWifiActivity.this, WifiInfoActivity.class);
                        data.putExtra("ssid", ssid);
                        data.putExtra("passworld", passworld);
                        data.putExtra("WIFICIPHER", WIFICIPHER);
                        startActivity(data);
                        finish();

                    } else {
                        Toast.makeText(this, "名称或密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }
}
