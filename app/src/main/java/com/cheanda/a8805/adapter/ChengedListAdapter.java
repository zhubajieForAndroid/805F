package com.cheanda.a8805.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.ChengeBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.views.Mydialog;
import com.cheanda.a8805.views.ZoomImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/16.
 */
public class ChengedListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "ChengedListAdapter";
    private List<ChengeBean.ResponseBean> mDataList;
    private Context mContext;

    private String mBottompic;
    private String mOilbottompic;
    private String mSievepic;
    private String mYpImageURL;
    private String mJtImageURL;
    private String mDisplay;

    public ChengedListAdapter(Context context, List<ChengeBean.ResponseBean> list) {
        mContext = context;
        mDataList = list;
        //读取标记值,是否显示油图片
        File file = new File(Environment.getExternalStorageDirectory(), "catch.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            mDisplay = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.view_attribet_tow, null);
        }
        TextView oilType = (TextView) convertView.findViewById(R.id.oil_type);
        TextView initoilmass = (TextView) convertView.findViewById(R.id.initoilmass);
        TextView gravityoilmass = (TextView) convertView.findViewById(R.id.gravityoilmass);
        TextView loopoilmass = (TextView) convertView.findViewById(R.id.loopoilmass);
        TextView sievetype = (TextView) convertView.findViewById(R.id.sievetype);
        TextView oilcheckmode = (TextView) convertView.findViewById(R.id.oilcheckmode);
        //油品名字
        TextView name = (TextView) convertView.findViewById(R.id.name);

        TextView oilimage1 = (TextView) convertView.findViewById(R.id.oilimage1);
        TextView oilimage2 = (TextView) convertView.findViewById(R.id.oilimage2);

        TextView joint_text = (TextView) convertView.findViewById(R.id.joint_text);//接头

        //滤网图片
        sievetype.setOnClickListener(this);
        //油位检查位置图片
        oilimage1.setOnClickListener(this);
        //换油接头拆装位置图片
        oilimage2.setOnClickListener(this);
        //接头图片
        joint_text.setOnClickListener(this);
        //油品图片
        name.setOnClickListener(this);

        oilType.setText(mDataList.get(position).getChangeoil());
        initoilmass.setText(mDataList.get(position).getInitoilmass());
        gravityoilmass.setText(mDataList.get(position).getGravityoilmass());
        loopoilmass.setText(mDataList.get(position).getLoopoilmass());

        oilcheckmode.setText(mDataList.get(position).getOilcheckmode());
        //油品型号
        String ypname = mDataList.get(position).getYpname();
        if (!TextUtils.isEmpty(ypname)){
            name.setText(ypname.split("\\.")[0]);
            //油品图片
            mYpImageURL = Constants.URLS.QUERY_URL+"/attached/tboiltype/"+ chechSuffix(ypname);
        }else {
            name.setText("NULL");
            name.setTextColor(Color.parseColor("#808080"));
        }
        //接头型号
        String jtname = mDataList.get(position).getJtname();
        if (!TextUtils.isEmpty(jtname)){
            joint_text.setText(jtname.split("\\.")[0]);
            //接头图片
            mJtImageURL = Constants.URLS.QUERY_URL+"/attached/tbconnect/"+chechSuffix(jtname);
        }else {
            joint_text.setText("NULL");
            joint_text.setTextColor(Color.parseColor("#808080"));
        }

        //滤网型号
        String sievetype1 = mDataList.get(position).getSievetype();
        if (!TextUtils.isEmpty(sievetype1)){
            sievetype.setText(sievetype1.split("\\.")[0]);
            //滤网图片
            mBottompic = Constants.URLS.QUERY_URL+"/attached/tbfiltertype/"+chechSuffix(mDataList.get(position).getSievepic());
        }else {
            sievetype.setText("NULL");
            sievetype.setTextColor(Color.parseColor("#808080"));
        }

        //油位检查位置图片
        String oilcheckpic = mDataList.get(position).getOilcheckpic();
        if (!TextUtils.isEmpty(oilcheckpic)){
            oilimage1.setText("点击查看");
            oilimage1.setTextColor(Color.parseColor("#21bfe7"));
            mOilbottompic = Constants.URLS.QUERY_URL+"/attached/"+chechSuffix(oilcheckpic);
        }else {
            oilimage1.setText("NULL");
            oilimage1.setTextColor(Color.parseColor("#808080"));
        }

        //换油接头拆装位置图片
        String changeoilpic = mDataList.get(position).getChangeoilpic();
        if (!TextUtils.isEmpty(changeoilpic)){
            oilimage2.setText("点击查看");
            oilimage2.setTextColor(Color.parseColor("#21bfe7"));
            mSievepic = Constants.URLS.QUERY_URL+"/attached/"+chechSuffix(changeoilpic);
        }else {
            oilimage2.setText("NULL");
            oilimage2.setTextColor(Color.parseColor("#808080"));
        }

        return convertView;
    }


    public void setDataList(List<ChengeBean.ResponseBean> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        Mydialog mydialog = new Mydialog(mContext, R.style.dialog);
        mydialog.setCanceledOnTouchOutside(true);
        switch (v.getId()) {
            //油品
            case R.id.name:
                if (TextUtils.isEmpty(mYpImageURL) && "".equals(mYpImageURL)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mYpImageURL);
                    mydialog.show();

                }
                break;
            //接头
            case R.id.joint_text:
                if (TextUtils.isEmpty(mJtImageURL) && "".equals(mJtImageURL)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mJtImageURL);
                    mydialog.show();

                }
                break;
            case R.id.sievetype:
                if (TextUtils.isEmpty(mBottompic) && "".equals(mBottompic)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mBottompic);
                    mydialog.show();

                }
                break;
            case R.id.oilimage1:
                if (TextUtils.isEmpty(mOilbottompic) && "".equals(mOilbottompic)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mOilbottompic);
                    mydialog.show();

                }
                break;
            case R.id.oilimage2:
                if (TextUtils.isEmpty(mSievepic) && "".equals(mSievepic)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mSievepic);
                    mydialog.show();
                }
                break;
        }
    }
    /**
     * 检查图片后缀
     */
    public static String chechSuffix(String data){
        if (!TextUtils.isEmpty(data)) {
            if (data.endsWith(".png") || data.endsWith(".jpg") || data.endsWith(".jpeg")) {
                return data;
            } else {
                return data + ".png";
            }
        }
        return "";

    }
}
