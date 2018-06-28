package com.cheanda.a8805.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.adapter.BrandListAdapter;
import com.cheanda.a8805.adapter.ModelYearAdapter;
import com.cheanda.a8805.adapter.TypeListAdapter;
import com.cheanda.a8805.bean.BandBean;
import com.cheanda.a8805.bean.CarStyleBean;
import com.cheanda.a8805.bean.ModelBean;
import com.cheanda.a8805.bean.NUmberBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CharacterParser;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.utils.SortModel;
import com.cheanda.a8805.utils.StringUtil;
import com.cheanda.a8805.utils.ToastUtil;
import com.cheanda.a8805.views.SideBar;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataQueryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "DataQueryActivity";

    private ListView mListView;
    private List<BandBean.ResponseBean> mList;
    private CharacterParser mCharacterParser;
    private List<Integer> resList;
    private Map<String, Object> mParms;
    private Protocol mProtocol;
    private List<SortModel> SourceDateList;
    private BrandListAdapter mAdapter;
    private ImageView mTop;
    private List<CarStyleBean.ResponseBean> mTypeList;
    private List<ModelBean.ResponseBean> mYearList;
    private ListView mListViewType;
    private TypeListAdapter mTypeAdapter;
    private ListView mListViewYear;
    private String mBrand;
    private ModelYearAdapter mYearAdaptr;
    private String mInfoId;
    private DisplayMetrics mMetrics;
    private FragmentManager mManager;
    private ImageView mBack;
    private int count = 0;
    private String mApi;
    private UserDataDao mDao;
    private Bitmap mBitmapTop;
    private SideBar mSideBar;
    private ProgressBar mProgressBrand;
    private ProgressBar mProgressType;
    private ProgressBar mProgressYeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data_query);
        mTypeList = new ArrayList<>();
        mYearList = new ArrayList<>();
        mList = new ArrayList<>();
        resList = new ArrayList<>();
        SourceDateList = new ArrayList<>();

        mManager = getSupportFragmentManager();

        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        initView();
        initLIstener();
        initData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mBack = (ImageView) findViewById(R.id.back);
        Button isDispaly = (Button) findViewById(R.id.display_btn);
        isDispaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                ToastUtil.showMessage("点击" + count);
                if (count == 2) {
                    try {
                        //关闭优品型号的显示
                        File file = new File(Environment.getExternalStorageDirectory(), "catch.txt");
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("false");
                        bw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mListViewType = (ListView) findViewById(R.id.listviewtype);
        mListViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView style = (TextView) view.findViewById(R.id.styletype);
                String string = style.getText().toString();
                mProgressYeat.setVisibility(View.VISIBLE);
                loadDataForType(string);
            }
        });
        mListViewYear = (ListView) findViewById(R.id.listviewyear);
        mListViewYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelBean.ResponseBean responseBean = mYearList.get(position);
                mInfoId = responseBean.getId();
                String yearandconfiginfo = responseBean.getId();
                //查询次数
                queryNumber(yearandconfiginfo);
            }
        });

        //实例化汉字转拼音类
        mCharacterParser = CharacterParser.getInstance();


        mTop = (ImageView) findViewById(R.id.topbar);
        //监听右边的导航
        mSideBar = (SideBar) findViewById(R.id.sidrbar);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }

        });

    }


    private void initLIstener() {
        mListView.setOnItemClickListener(this);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        //品牌
        mAdapter = new BrandListAdapter(this, SourceDateList, mList);
        mListView.setAdapter(mAdapter);
        //车型
        mTypeAdapter = new TypeListAdapter(this, mTypeList);
        mListViewType.setAdapter(mTypeAdapter);
        //年款
        mYearAdaptr = new ModelYearAdapter(this, mYearList);
        mListViewYear.setAdapter(mYearAdaptr);


        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.topbar_1);

        mTop.setImageBitmap(mBitmapTop);
        Bitmap bitmap1 = BItmapUtil.compressBItmap(this, R.mipmap.back);
        mBack.setImageBitmap(bitmap1);

        mProgressBrand = (ProgressBar) findViewById(R.id.progress_brand);
        mProgressType = (ProgressBar) findViewById(R.id.progress_type);
        mProgressYeat = (ProgressBar) findViewById(R.id.progress_year);

        //网络请求
        loadData();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTypeList.clear();
        mTypeAdapter.updataAdapter(mTypeList);
        TextView title = (TextView) view.findViewById(R.id.title);
        mBrand = title.getText().toString();
        mProgressType.setVisibility(View.VISIBLE);
        loadDataForBrand(mBrand);
    }

    //请求品牌
    private void loadData() {
        mParms = new HashMap<>();
        mParms.put("url", Constants.URLS.BRAND_URL);
        mParms.put("faccount", Constants.USER_NAME);
        //请求成功更新listview的数据
        mProtocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showMessage("网络异常");
                        mProgressBrand.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    BandBean bandBean = gson.fromJson(s, BandBean.class);
                    if (bandBean.getCode() == 0) {
                        mList = bandBean.getResponse();
                        List<String> sortLetterList = StringUtil.getSortList(mList);
                        SourceDateList = StringUtil.filledData(sortLetterList);
                        //请求成功更新listview的数据
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBrand.setVisibility(View.GONE);
                                mSideBar.setVisibility(View.VISIBLE);
                                mAdapter.updateListView(SourceDateList, mList);
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showMessage("没有数据");
                                mProgressBrand.setVisibility(View.GONE);
                            }
                        });
                    }
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMessage("数据有误");
                            mProgressBrand.setVisibility(View.GONE);
                        }
                    });
                }
            }
        };
        //设置参数
        mProtocol.setParams(mParms);
        //触发请求
        mProtocol.loadDataFromNet();
    }

    //请求车系
    private void loadDataForBrand(String string) {
        mTypeList.clear();
        Map<String, Object> parms = new HashMap<>();
        parms.put("brand", string);
        parms.put("faccount", Constants.USER_NAME);
        parms.put("url", Constants.URLS.MODELS_URL);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ToastUtil.showMessage("网络异常");
                        mProgressType.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    CarStyleBean carStyleBean = gson.fromJson(s, CarStyleBean.class);
                    List<CarStyleBean.ResponseBean> list = carStyleBean.getResponse();
                    if (list != null)
                        mTypeList.addAll(list);
                    //请求成功更新listview的数据
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressType.setVisibility(View.GONE);
                            mTypeAdapter.updataAdapter(mTypeList);
                            mListViewType.setVisibility(View.VISIBLE);
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMessage("数据有误");
                            mProgressType.setVisibility(View.GONE);
                        }
                    });
                }
            }
        };
        //设置参数
        protocol.setParams(parms);
        //触发请求
        protocol.loadDataFromNet();
    }

    //请求年款和排量
    private void loadDataForType(String string) {
        mYearList.clear();
        mYearAdaptr.updataAdapter(mYearList);
        Map<String, Object> parms = new HashMap<>();
        parms.put("brand", mBrand);
        parms.put("type", string);
        parms.put("faccount", Constants.USER_NAME);
        parms.put("url", Constants.URLS.MODEYEAR_URL);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showMessage("网络异常");
                        mProgressYeat.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                if (s.contains("请求成功")) {
                    ModelBean modelBean = gson.fromJson(s, ModelBean.class);
                    int code = modelBean.getCode();
                    if (code == 0) {
                        List<ModelBean.ResponseBean> list = modelBean.getResponse();
                        if (list != null) {
                            mYearList.addAll(list);
                        }

                        //请求成功更新listview的数据
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressYeat.setVisibility(View.GONE);
                                mYearAdaptr.updataAdapter(mYearList);
                                mListViewYear.setVisibility(View.VISIBLE);
                            }
                        });
                    } else if (code == 25) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showMessage("查询次数已到上限");
                                mProgressYeat.setVisibility(View.GONE);
                                finish();
                            }
                        });
                    } else if (code == 26) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showMessage("没有权限");
                                mProgressYeat.setVisibility(View.GONE);
                                finish();
                            }
                        });
                    }

                }

            }
        };
        //设置参数
        protocol.setParams(parms);
        //触发请求
        protocol.loadDataFromNet();
    }

    //查询次数
    private void queryNumber(final String yearandconfiginfo) {
        Map<String, Object> parms = new HashMap<>();
        parms.put("faccount", Constants.USER_NAME);
        parms.put("url", Constants.URLS.QUERY_NUMBER_URL);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
            }

            @Override
            public void parseData(Gson gson, String s) {
                NUmberBean bean = gson.fromJson(s, NUmberBean.class);
                int code = bean.getCode();
                if (code == 0) {
                    Intent intent = new Intent(DataQueryActivity.this, ResultActivity.class);
                    intent.putExtra("id", yearandconfiginfo);
                    startActivity(intent);
                } else if (code == 25) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMessage("查询次数已用尽");
                        }
                    });
                } else if (code == 26) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMessage("没有权限");
                            finish();
                        }
                    });
                }
            }
        };
        //设置参数
        protocol.setParams(parms);
        //触发请求
        protocol.loadDataFromNet();
    }


}
