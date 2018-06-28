package com.cheanda.a8805.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.CountDataBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.views.HintDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SelectIntentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SelectIntentActivity";
    @Bind(R.id.carType_base_data)
    TextView mCarTypeBaseData;
    @Bind(R.id.box_curing_data)
    TextView mBoxCuringData;
    @Bind(R.id.box_curing_price_data)
    TextView mBoxCuringPriceData;
    @Bind(R.id.vin_query_number)
    TextView mVinQueryNumber;
    @Bind(R.id.law)
    LinearLayout mLaw;
    @Bind(R.id.service_car)
    TextView mServiceCar;
    @Bind(R.id.lae)
    LinearLayout mLae;
    @Bind(R.id.activity_select_intent)
    RelativeLayout mActivitySelectIntent;
    private EditText mVin_et;
    private static boolean isShowDialog = true;
    private ImageView mBack;
    private String mApi;
    private UserDataDao mDao;
    private Bitmap mBitmapTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_intent);
        ButterKnife.bind(this);
        if (isShowDialog) {
            isShowDialog = false;
            HintDialog dialog = new HintDialog(this, R.style.dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        initView();
    }

    private void initView() {
        Button query = (Button) findViewById(R.id.query_btn);
        View box = findViewById(R.id.query_box);
        query.setOnClickListener(this);
        box.setOnClickListener(this);

        mVin_et = (EditText) findViewById(R.id.vin_et);

        ImageView ivBar = (ImageView) findViewById(R.id.select_topbar);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_top_1_1);

        ivBar.setImageBitmap(mBitmapTop);
        mBack = (ImageView) findViewById(R.id.back);
        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.back);
        mBack.setImageBitmap(bitmap1);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.activity_select_intent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        requestCountData();

    }

    private void requestCountData() {
        Map<String, Object> pames = new HashMap<>();
        pames.put("url", Constants.URLS.QUERY_COUNT_DATA_URL);
        pames.put("faccount", Constants.USER_NAME);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SelectIntentActivity.this, "网络异常,获取统计数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    CountDataBean countDataBean = gson.fromJson(s, CountDataBean.class);
                    int code = countDataBean.getCode();
                    if (code == 0) {
                        List<CountDataBean.ResponseBean> response = countDataBean.getResponse();
                        for (int i = 0; i < response.size(); i++) {
                            final CountDataBean.ResponseBean bean = response.get(i);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCarTypeBaseData.setText(bean.getVEHICLETYPECOUNT() + "条");//车型基础基础数据信息
                                    mBoxCuringData.setText(bean.getGEARBOXCOUNT() + "条");//变速箱养护数据信息
                                    mBoxCuringPriceData.setText(bean.getPRICECOUNT() + "条");//变速箱养护报价信息
                                    mVinQueryNumber.setText(bean.getVINQUERY() + "次");//当日VIN输入查询
                                    mServiceCar.setText(bean.getALLUSERCOUNT() + "辆");////目前已服务车辆总数
                                }
                            });
                        }
                    }
                }
            }
        };
        protocol.setParams(pames);
        protocol.loadDataFromNet();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_btn:
                String resultVin = mVin_et.getText().toString().trim();
                //启动VIN查询结果页面
                if (!TextUtils.isEmpty(resultVin)) {
                    if (resultVin.length() == 17) {
                        Intent i = new Intent(this, VINInfoActivity.class);
                        i.putExtra("faname", Constants.USER_NAME);
                        i.putExtra("VIN", resultVin);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "VIN码不合法", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "VIN码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.query_box:
                Intent intent = new Intent(this, DataQueryActivity.class);
                intent.putExtra("faname", Constants.USER_NAME);
                intent.putExtra("isFinish", false);
                startActivity(intent);
                break;
        }
    }
}
