package com.cheanda.a8805.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.cheanda.a8805.bean.ResultBean;
import com.cheanda.a8805.conf.Constants;
import com.cheanda.a8805.views.Mydialog;
import com.cheanda.a8805.views.ZoomImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/15.
 */
public class ResultListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "ResultListAdapter";
    private Context mContext;
    private List<ResultBean.ResponseBean> mList;
    private String mBottompic = "";
    private String mOilbottompic = "";
    private String mSievepic = "";
    private String mUrl = "";
    private String mOilType = "";
    private  String mDisplay;


    public ResultListAdapter(Context context, List<ResultBean.ResponseBean> response) {
        mContext = context;
        mList = response;
        //读取标记值,是否显示油图片
        File file = new File(Environment.getExternalStorageDirectory(), "catch.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            mDisplay = br.readLine();
            Log.d(TAG, "ResultListAdapter: "+mDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
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
            convertView = View.inflate(mContext, R.layout.view_attribet_one, null);
        }
        TextView bandInfo = (TextView) convertView.findViewById(R.id.band_info);
        TextView carType = (TextView) convertView.findViewById(R.id.cartype);
        TextView yearType = (TextView) convertView.findViewById(R.id.year_type);
        TextView config = (TextView) convertView.findViewById(R.id.config);
        TextView comeinyear = (TextView) convertView.findViewById(R.id.comeinyear);
        TextView stopyear = (TextView) convertView.findViewById(R.id.stopyear);
        TextView gearbox = (TextView) convertView.findViewById(R.id.gearbox);
        TextView boxtype = (TextView) convertView.findViewById(R.id.boxtype);

        //接头型号
        TextView jietouType = (TextView) convertView.findViewById(R.id.jietouTtpe);

        TextView sievetype = (TextView) convertView.findViewById(R.id.sievetype);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        TextView oilimage = (TextView) convertView.findViewById(R.id.oilimage);
        TextView oilimage1 = (TextView) convertView.findViewById(R.id.oilimage1);

        oilimage.setOnClickListener(this);
        oilimage1.setOnClickListener(this);
        sievetype.setOnClickListener(this);
        jietouType.setOnClickListener(this);
        name.setOnClickListener(this);

        bandInfo.setText(mList.get(position).getBrand());
        carType.setText(mList.get(position).getType());
        yearType.setText(mList.get(position).getYear());
        config.setText(mList.get(position).getConfiginfo());
        comeinyear.setText(mList.get(position).getComeinyear());
        stopyear.setText(mList.get(position).getStopyear());
        gearbox.setText(mList.get(position).getGearbox());
        boxtype.setText(mList.get(position).getBoxtype());

        //油底壳垫片型号
        String oilbottomtype = mList.get(position).getOilbottomtype();
        if (!TextUtils.isEmpty(oilbottomtype)) {
            oilimage1.setText(oilbottomtype.split("\\.")[0]);
            //油底壳垫片图片
            mOilbottompic = Constants.URLS.QUERY_URL + "/attached/" + chechSuffix(mList.get(position).getOilbottompic());
        } else {
            oilimage1.setText("NULL");
            oilimage1.setTextColor(Color.parseColor("#808080"));
        }
        //接头型号
        String jtname = mList.get(position).getJtname();

        if (!TextUtils.isEmpty(jtname)) {
            jietouType.setText(jtname.split("\\.")[0]);
            //接头图片
            mUrl = Constants.URLS.QUERY_URL + "/attached/tbconnect/" + chechSuffix(jtname);
        } else {
            jietouType.setText("NULL");
            jietouType.setTextColor(Color.parseColor("#808080"));
        }

        //滤网型号
        String sievetype1 = mList.get(position).getSievetype();
        if (!TextUtils.isEmpty(sievetype1)) {
            sievetype.setText(sievetype1.split("\\.")[0]);
            //滤网图片
            mSievepic = Constants.URLS.QUERY_URL + "/attached/tbfiltertype/" + chechSuffix(mList.get(position).getSievepic());
        } else {
            sievetype.setText("NULL");
            sievetype.setTextColor(Color.parseColor("#808080"));
        }
        //油品型号
        String ypName = mList.get(position).getName();
        if (!TextUtils.isEmpty(ypName)) {
            name.setText(ypName.split("\\.")[0]);
            if (!"false".equals(mDisplay)) {
                //油品图片
                mOilType = Constants.URLS.QUERY_URL + "/attached/tboiltype/" + chechSuffix(ypName);
            }
        } else {
            name.setText("NULL");
            name.setTextColor(Color.parseColor("#808080"));
        }

        //变速箱油底垫图片
        String bottompic = mList.get(position).getBottompic();
        if (!TextUtils.isEmpty(bottompic)){
            oilimage.setText("点击查看");
            oilimage.setTextColor(Color.parseColor("#21bfe7"));
            mBottompic = Constants.URLS.QUERY_URL + "/attached/" + chechSuffix(bottompic);
        }else {
            oilimage.setText("NULL");
            oilimage.setTextColor(Color.parseColor("#808080"));
        }

        return convertView;
    }

    public void setDataList(List<ResultBean.ResponseBean> dataList) {
        mList = dataList;
        //重新走getView()方法
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Mydialog mydialog = new Mydialog(mContext, R.style.dialog);
        mydialog.setCanceledOnTouchOutside(true);
        switch (v.getId()) {
            case R.id.oilimage:
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
            case R.id.sievetype:

                if (TextUtils.isEmpty(mSievepic) && "".equals(mSievepic)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mSievepic);
                    mydialog.show();
                }
                break;
            case R.id.jietouTtpe://接头图片
                if (TextUtils.isEmpty(mUrl) && "".equals(mUrl)) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mUrl);
                    mydialog.show();
                }
                break;
            case R.id.name:
                if (TextUtils.isEmpty(mOilType) && "".equals(mOilType) ) {
                    Toast.makeText(mContext, "没有图片", Toast.LENGTH_SHORT).show();
                } else {
                    mydialog.setData(mOilType);
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
