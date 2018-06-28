package com.cheanda.a8805.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.base.BaseActivity;
import com.cheanda.a8805.dao.QueryDBBean;
import com.cheanda.a8805.dao.UserDataDao;
import com.cheanda.a8805.utils.BItmapUtil;
import com.cheanda.a8805.utils.CheckUtil;
import com.cheanda.a8805.utils.NumberToImage;
import com.cheanda.a8805.views.EmptyDialog;
import com.cheanda.a8805.views.LockDialog;
import com.cheanda.a8805.views.MyProgressDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//排空邮箱
public class EmptyOilActivity extends BaseActivity {

    private static final String TAG = "EmptyOilActivity";
    @Bind(R.id.enpty_bg)
    ImageView mEnptyBg;
    @Bind(R.id.shi)
    ImageView mShi;
    @Bind(R.id.ge)
    ImageView mGe;
    @Bind(R.id.fen)
    ImageView mFen;
    @Bind(R.id.fen_a)
    ImageView mFenA;
    @Bind(R.id.ge_a)
    ImageView mGeA;
    @Bind(R.id.shi_a)
    ImageView mShiA;
    @Bind(R.id.tv_state)
    TextView mTvState;
    @Bind(R.id.fu)
    TextView mFu;
    @Bind(R.id.shi_o)
    ImageView mShiO;
    @Bind(R.id.fen_o)
    ImageView mFenO;
    @Bind(R.id.ge_o)
    ImageView mGeO;
    @Bind(R.id.tv_message_state)
    TextView mTvMessageState;
    @Bind(R.id.top)
    ImageView mTop;

    private Bitmap mBitmap;
    private int distinguishResult;
    private int[] greenImages = {R.mipmap.number_green_zero, R.mipmap.number_green_on, R.mipmap.number_green_two, R.mipmap.number_green_three,
            R.mipmap.number_green_four, R.mipmap.number_green_five, R.mipmap.number_green_six, R.mipmap.number_green_seven, R.mipmap.number_green_eight,
            R.mipmap.number_green_nine,};
    private StringBuffer sb = new StringBuffer();
    private int tempcount;
    private Handler mHandler;
    private int tempDataLength = 0;
    private byte[] startArray = new byte[8];
    private byte[] arr = new byte[16];
    private boolean isStart;
    private Integer mNewOilKG;
    private boolean c = true;
    private Integer initNewOilWeight;
    private String mMNewOilKGStr;
    private String mOldOilKGStr;
    private String mS;
    private String mAStr;
    private String mOilStr;
    private Bitmap mBitmapTop;
    private String mApi;
    private UserDataDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_empty_oil_activiti);
        ButterKnife.bind(this);
        mHandler = new Handler();
        initListener();
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
            case 1:
                //返回
                if (isStart) {
                    finish();
                }
                break;
            case 2:
                //排空新油箱
                if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(EmptyOilActivity.this, EmptyNewOilActivity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EmptyOilActivity.this, "运行失败,稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 3:
                //排空旧邮箱
               /* if (isStart) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(EmptyOilActivity.this, EmptyOldOil1Activity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EmptyOilActivity.this, "停止失败,稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }*/
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
                    mTvMessageState.setText("正常");
                    mTvMessageState.setTextColor(Color.parseColor("#00ff00"));
                }
            });
        }
        //解析电压电流数据
        analysisStateResult();
    }

    //解析电压电流油温的数据
    private void analysisStateResult() {
        //校验位成功
        if (arr[1] == 16) {
            //新油重量参数
            mNewOilKG = CheckUtil.getNewOilWeightInt(arr);
            mMNewOilKGStr = CheckUtil.getNewOilWeight(arr);
            mOldOilKGStr = CheckUtil.getOldOilWeight(arr);
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

    //改变车辆状态文字
    private void chengText(final String i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (i.startsWith("0")) {
                    mTvState.setText("熄火");
                } else if (i.startsWith("1")) {
                    mTvState.setText("启动");
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
                    mFen.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 1)))));
                }
                if (mS.length() - 2 >= 0) {
                    mGe.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 2)))));
                }
                if (mS.length() - 3 >= 0) {
                    mShi.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mS.charAt(mS.length() - 3)))));
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
                if (mOilStr.length() >= 2) {

                }
                if (mOilStr.startsWith("-")) {
                    mFu.setText("-");
                    //油温度
                    if (mOilStr.length() - 1 >= 0) {
                        mGeO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 1)))));
                    }
                    if (mOilStr.length() - 2 >= 0) {
                        mFenO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 2)))));
                    }
                } else {
                    mFu.setText("");
                    if (mOilStr.length() - 1 >= 0) {
                        mGeO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 1)))));
                    }
                    if (mOilStr.length() - 2 >= 0) {
                        mFenO.setImageResource(NumberToImage.getRedImageResId(greenImages, Integer.parseInt(String.valueOf(mOilStr.charAt(mOilStr.length() - 2)))));
                    }
                }

            }
        });
    }

    private void initListener() {
        mEnptyBg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selectOilBox(v, event);
                return false;
            }
        });
    }

    private void initData() {
        mEnptyBg.setScaleType(ImageView.ScaleType.FIT_XY);
        mBitmap = BItmapUtil.compressBItmap(this, R.mipmap.bg_img_23);
        mEnptyBg.setImageBitmap(mBitmap);

        mBitmapTop = BItmapUtil.compressBItmap(this, R.mipmap.home_bg_img_1_1);

        mTop.setImageBitmap(mBitmapTop);
    }

    private void selectOilBox(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 288 && x < 1000 && y > 172 && y < 310) {
            distinguishResult = 2;
            //发送进入排空新油箱2a 05 06 29 23
            int[] bytes = {0x2a, 0x05, 0x06, 0x29, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);

        } else if (x > 280 && x < 995 && y > 423 && y < 550) {
            /*distinguishResult = 3;
            //发送进入排空旧油箱2a 05 07 28 23
            int[] bytes = {0x2a, 0x05, 0x07, 0x28, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);*/
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.drain_old_1_1);
            EmptyDialog dialog = new EmptyDialog(EmptyOilActivity.this, R.style.dialog, bitmap);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

        } else if (x > 1075 && x < 1250 && y > 620 && y < 675) {
            finish();
        }
    }

    private Runnable mTimeRunnable = new Thread() {
        @Override
        public void run() {
            tempcount++;
            if (tempcount > 3) {
                //三次没有数据显示异常
                mTvMessageState.setText("异常");
                mTvMessageState.setTextColor(Color.parseColor("#ff0000"));
            }
            mHandler.postDelayed(this, 300);
            //发送查询2a 06 09 01 24 23
            int[] bytes = {0x2a, 0x06, 0x09, 0x01, 0x24, 0x23};
            CheckUtil.sendMessage(bytes, mOutputStream);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
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
        mEnptyBg.setImageBitmap(null);
        mBitmap.recycle();
    }
}
