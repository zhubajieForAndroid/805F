package com.cheanda.a8805.views;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.EquipmentOnlineBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.utils.Protocol;
import com.cheanda.a8805.utils.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/12/21.
 */

public class OnlineDialog extends Dialog implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "OnlineDialog";

    private List<String> companyName = new ArrayList<>();
    private List<String> equipmentTypeName = new ArrayList<>();
    private List<String> companyIdList = new ArrayList<>();
    private List<String> equipmentTypeIdList = new ArrayList<>();
    private Context mContext;
    private TextView mTvName;
    private TextView mTvType;
    private EditText mEtSim;
    private int selectComanpyIndex;
    private int selectEquipmentTypeIndex;
    private View mViewById;
    private String mEquipmentID;
    private final Activity mActivity;
    private OnEquipmentIDlistener mOnEquipmentIDlistener;
    private ProgressDialog mDialog;

    public OnlineDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        mActivity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_equipment_online);
        mTvName = (TextView) findViewById(R.id.name);
        mTvType = (TextView) findViewById(R.id.type);
        mEtSim = (EditText) findViewById(R.id.sim);
        mViewById = findViewById(R.id.image_bg);
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("正在上线,请稍后");
        //请求公司名称
        getComapnyLIst();
        //获取设备类型
        getEquipmentType();
        initListener();

    }

    private void initListener() {
        mTvName.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mViewById.setOnTouchListener(this);
    }
    /**
     * 获取设备类型列表
     */
    public void getEquipmentType() {
        Map<String, Object> pames = new HashMap<>();
        pames.put("url", Constants.URLS.GET_EQUIPMENT_TYPE);
        pames.put("faccount", Constants.USER_NAME);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showMessage("网络异常,获取设备列表失败");
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                Log.d(TAG, "parseData: "+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectResult = jsonArray.getJSONObject(i);
                        String equipmentType = jsonObjectResult.getString("type");
                        String equipmentTypeID = jsonObjectResult.getString("equipmentTypeID");
                        equipmentTypeName.add(equipmentType);
                        equipmentTypeIdList.add(equipmentTypeID);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        protocol.setParams(pames);
        protocol.loadDataFromNet();
    }
    /**
     * 获取公司列表
     */
    public void getComapnyLIst() {
        Map<String, Object> pames = new HashMap<>();
        pames.put("url", Constants.URLS.GET_COMAPNY_TYPE);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showMessage("网络异常,获取公司列表失败");
                    }
                });
            }

            @Override
            public void parseData(Gson gson, String s) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String companyFullName = jsonObject.getString("companyFullName");
                        String companyId = jsonObject.getString("companyId");
                        companyIdList.add(companyId);
                        companyName.add(companyFullName);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        protocol.setParams(pames);
        protocol.loadDataFromNet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name:
                showMyDialog(companyName,true);
                break;
            case R.id.type:
                showMyDialog(equipmentTypeName,false);
                break;
        }

    }

    private void showMyDialog(List<String> data, final boolean isD) {
        if (data.size() > 0) {
            final String[] arr = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                arr[i] = data.get(i);
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setTitle("公司列表");
            dialog.setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //保存一份选中的索引
                    if (isD){
                        selectComanpyIndex = which;
                        mTvName.setText(arr[which]);
                    }else {
                        selectEquipmentTypeIndex = which;
                        mTvType.setText(arr[which]);
                    }

                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x > 290 && x < 705 && y > 365 && y < 432) {
            String name = mTvName.getText().toString().trim();
            String type = mTvType.getText().toString().trim();
            String sim = mEtSim.getText().toString().trim();
            if (!TextUtils.isEmpty(name) && !"正在获取公司列表".equals(name) && !"点此获取".equals(name)){
                if (!TextUtils.isEmpty(type) && !"正在获取设备类型列表".equals(type)  && !"点此获取".equals(type)){
                    if (!TextUtils.isEmpty(sim)){
                        //开启上线
                        equipmentOnLIne();
                    }else {
                        ToastUtil.showMessage("SIM卡号不能为空");
                    }
                }else {
                    ToastUtil.showMessage("请先选择设备类型");
                }
            }else {
                ToastUtil.showMessage("请先选择公司");
            }

        }
        return false;
    }

    private void equipmentOnLIne() {
        mDialog.show();
        String sim = mEtSim.getText().toString().trim();
        Map<String, Object> pames = new HashMap<>();
        pames.put("url", Constants.URLS.START_ONLINE);
        pames.put("id", companyIdList.get(selectComanpyIndex)+equipmentTypeIdList.get(selectEquipmentTypeIndex));
        pames.put("simNum", sim);
        pames.put("simPassword", "000000");
        pames.put("createUser", Constants.USER_NAME);
        Protocol protocol = new Protocol() {
            @Override
            public void errorManage(IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        ToastUtil.showMessage("网络异常,上线失败");
                    }
                });

            }

            @Override
            public void parseData(Gson gson, String s) {
                EquipmentOnlineBean equipmentOnlineBean = gson.fromJson(s, EquipmentOnlineBean.class);
                int code = equipmentOnlineBean.getCode();
                if (code == 0){
                    mEquipmentID = equipmentOnlineBean.getResponse().get(0).getEquipmentID();
                    final String messagestr = equipmentOnlineBean.getResponse().get(0).getMessagestr();
                    if (TextUtils.isEmpty(messagestr)){
                        if (!TextUtils.isEmpty(mEquipmentID)){
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnEquipmentIDlistener.idListener(mEquipmentID);
                                    ToastUtil.showMessage("上线成功");
                                    dismiss();
                                    mDialog.dismiss();
                                }
                            });
                        }
                    }else {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                Toast.makeText(mContext,messagestr,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        };
        protocol.setParams(pames);
        protocol.loadDataFromNet();
    }
    public void setOnEquipmentIDlistener(OnEquipmentIDlistener listener){
        mOnEquipmentIDlistener = listener;
    }
    public interface OnEquipmentIDlistener{
        void idListener(String id);
    }


}
