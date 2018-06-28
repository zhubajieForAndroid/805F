package com.cheanda.a8805.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.VINInfoBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.views.SpacingTextView;
import com.cheanda.a8805.views.TakePhotoPopWin;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VINInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VINInfoActivity";
    @Bind(R.id.vin_topbar)
    ImageView mVinTopbar;
    @Bind(R.id.spacing_tv)
    SpacingTextView mSpacingTv;
    @Bind(R.id.manufactor_tv)
    TextView mManufactorTv;
    @Bind(R.id.brand_tv)
    TextView mBrandTv;
    @Bind(R.id.car_type_tv)
    TextView mCarTypeTv;
    @Bind(R.id.car_xi_tv)
    TextView mCarXiTv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.car_year_tv)
    TextView mCarYearTv;
    @Bind(R.id.car_belowoff_tv)
    TextView mCarBelowoffTv;
    @Bind(R.id.car_consume_tv)
    TextView mCarConsumeTv;
    @Bind(R.id.car_engine_tv)
    TextView mCarEngineTv;
    @Bind(R.id.car_box_tv)
    TextView mCarBoxTv;
    @Bind(R.id.car_drive_mode_tv)
    TextView mCarDriveModeTv;
    @Bind(R.id.car_style_tv)
    TextView mCarStyleTv;
    @Bind(R.id.car_top_tyre_tv)
    TextView mCarTopTyreTv;
    @Bind(R.id.car_end_tyre_tv)
    TextView mCarEndTyreTv;
    @Bind(R.id.car_number_tv)
    TextView mCarNumberTv;
    @Bind(R.id.car_info_tv)
    TextView mCarInfoTv;
    @Bind(R.id.car_three_tv)
    TextView mCarThreeTv;
    @Bind(R.id.car_dang_tv)
    TextView mCarDangTv;
    @Bind(R.id.car_fatype_tv)
    TextView mCarFatypeTv;
    @Bind(R.id.changjia_tv)
    TextView mChangjiaTv;
    @Bind(R.id.info_tv)
    TextView mInfoTv;
    @Bind(R.id.oiitypre_tv)
    TextView mOiitypreTv;
    @Bind(R.id.oil_number_tv)
    TextView mOilNumberTv;
    @Bind(R.id.car_qigang_number_tv)
    TextView mCarQigangNumberTv;
    @Bind(R.id.qigang_number_tv)
    TextView mQigangNumberTv;
    @Bind(R.id.qigang_type_tv)
    TextView mQigangTypeTv;
    @Bind(R.id.qigang_jing_tv)
    TextView mQigangJingTv;
    @Bind(R.id.qigang_cailiao_tv)
    TextView mQigangCailiaoTv;
    @Bind(R.id.qigang_cgai_tv)
    TextView mQigangCgaiTv;
    @Bind(R.id.qigang_peiqi_tv)
    TextView mQigangPeiqiTv;
    @Bind(R.id.proform_result)
    Button mProformResult;
    @Bind(R.id.activity_vininfo)
    RelativeLayout mActivityVininfo;

    private String mVin;
    private HashMap<String, Object> mParms;
    private String mFaname;
    private String mYearType = "";//年款和配置
    private String resultID = "";
    private String mCartype;
    private String mBrand;
    private String mYearandconfiginfo;
    private ArrayList<String> mDataList = new ArrayList<>();
    private View mMainView;
    private View mInclude;
    private ImageView mBack;
    private String mApi;
    private UserDataDao mDao;
    private Bitmap mBitmapTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mMainView = View.inflate(this, R.layout.activity_vininfo, null);
        setContentView(mMainView);
        ButterKnife.bind(this);

        for (int i = 0; i < 23; i++) {
            if (i >= 10) {
                mDataList.add("20" + i);
            } else {
                mDataList.add("200" + i);
            }
        }
        Intent intent = getIntent();
        mVin = intent.getStringExtra("VIN");
        mVin = mVin.toUpperCase();
        mFaname = intent.getStringExtra("faname");
        initView();
        initData();
    }

    private void initView() {
        mSpacingTv.setSpacing(12);
        mSpacingTv.setText(mVin);
        mProformResult.setOnClickListener(this);
        mInclude = findViewById(R.id.include);
        mBack = (ImageView) findViewById(R.id.back);

        ImageView ivBar = (ImageView) findViewById(R.id.vin_topbar);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_top_1_2);

        ivBar.setImageBitmap(mBitmapTop);
    }

    private void initData() {
        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.back);
        mBack.setImageBitmap(bitmap1);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //请求网络数据
        loadDataFormNet();
    }

    private void loadDataFormNet() {
        mParms = new HashMap<>();
        mParms.put("url", Constants.URLS.QUERY_VIN_URL);
        mParms.put("vinCode", mVin);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInclude.setVisibility(View.GONE);
                        Toast.makeText(VINInfoActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    VINInfoBean vinInfoBean = gson.fromJson(s, VINInfoBean.class);
                    int code = vinInfoBean.getCode();
                    if (code == 0) {
                        List<VINInfoBean.ResponseBean> response = vinInfoBean.getResponse();
                        for (int i = 0; i < response.size(); i++) {
                            final VINInfoBean.ResponseBean bean = response.get(i);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mInclude.setVisibility(View.GONE);
                                    mManufactorTv.setText(bean.getManufacturers());
                                    mBrandTv.setText(bean.getBrand());
                                    mCarTypeTv.setText(bean.getModels());
                                    mCarXiTv.setText(bean.getSeries());
                                    mNameTv.setText(bean.getSalesName());
                                    mCarYearTv.setText("");//销售版本
                                    mCarBelowoffTv.setText(bean.getYear());
                                    mCarConsumeTv.setText(bean.getListingYear());
                                    mCarEngineTv.setText(bean.getListingMonth());
                                    mCarBoxTv.setText(bean.getProducedYear());
                                    mCarDriveModeTv.setText(bean.getIdlingYear());
                                    mCarStyleTv.setText(bean.getCountry());
                                    mCarTopTyreTv.setText(bean.getVehicleAttributes());
                                    mCarEndTyreTv.setText(bean.getDisplacement());
                                    mCarNumberTv.setText(bean.getTransmissionType());
                                    mCarInfoTv.setText(bean.getTransmissionDescription());
                                    mCarThreeTv.setText("");//三元催化器
                                    mCarDangTv.setText(bean.getGearNumber());
                                    mCarFatypeTv.setText("");//发动机型号
                                    mChangjiaTv.setText("");//发动机生产厂家
                                    mInfoTv.setText("");//发动机描述
                                    mOiitypreTv.setText(bean.getFuelType());
                                    mOilNumberTv.setText(bean.getFuelGrade());
                                    mCarQigangNumberTv.setText(bean.getCylinders());
                                    mQigangNumberTv.setText(bean.getValvesPerCylinder());
                                    mQigangTypeTv.setText(bean.getCylinderArrangement());
                                    mQigangJingTv.setText("");//缸径
                                    mQigangCailiaoTv.setText("");//缸体材料
                                    mQigangCgaiTv.setText("");//缸盖材料
                                    mQigangPeiqiTv.setText("");//气缸配气
                                    mCartype = bean.getModels();
                                    mBrand = bean.getBrand();
                                    mYearType = bean.getSalesName();
                                    //检查年款是否为空
                                    checkYear();
                                }
                            });

                        }
                    }
                }
            }
        };
        protocol.setParams(mParms);
        protocol.loadDataFromNet();
    }

    private void checkYear() {
        String resultYear = (String) mCarBelowoffTv.getText();
        if (TextUtils.isEmpty(resultYear)) {
            mCarBelowoffTv.setText("点击选择");
            mCarBelowoffTv.setTextColor(Color.parseColor("#21bfe7"));
            mCarBelowoffTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TakePhotoPopWin photoPopWin = new TakePhotoPopWin(VINInfoActivity.this);
                    photoPopWin.showAtLocation(mMainView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    photoPopWin.setOnLoopViewItemINdexListener(new TakePhotoPopWin.OnLoopViewItemINdexListener() {
                        @Override
                        public void itemIndex(int index) {
                            mCarBelowoffTv.setText(mDataList.get(index));
                        }
                    });
                }
            });
        }

    }

    private void getID(String brand, final String cartype) {
        String trim = mCarBelowoffTv.getText().toString().trim();
        HashMap<String, Object> parms = new HashMap<>();
        parms.put("brand", brand);
        parms.put("type", cartype);
        parms.put("year", trim);
        parms.put("configinfo", mYearType);
        parms.put("faccount", mFaname);
        parms.put("url", Constants.URLS.MODEYEAR_URL);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {

            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    try {
                        JSONArray jsonArray = new JSONObject(s).getJSONArray("response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            if (!TextUtils.isEmpty(id)) {
                                Intent intent = new Intent(VINInfoActivity.this, ResultActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("brand", mBrand);
                                intent.putExtra("type", cartype);
                                intent.putExtra("carInfo", mYearandconfiginfo);
                                startActivity(intent);
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(VINInfoActivity.this, "没有该车辆信息", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VINInfoActivity.this, "查询次数已到上限", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        protocol.setParams(parms);
        protocol.loadDataFromNet();
    }


    @Override
    public void onClick(View v) {
        String trim = mCarBelowoffTv.getText().toString().trim();

        if (!"点击选择".equals(trim) && !TextUtils.isEmpty(mBrand) && !TextUtils.isEmpty(mCartype)) {
            //在根据品牌年款去请求 车型id
            getID(mBrand, mCartype);
        } else {
            Toast.makeText(this, "年款不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
