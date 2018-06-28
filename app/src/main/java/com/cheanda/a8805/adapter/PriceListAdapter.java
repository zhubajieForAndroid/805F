package com.cheanda.a8805.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.PriceBean;
import com.cheanda.a8805.views.Mydialog;
import com.cheanda.a8805.views.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/17.
 */
public class PriceListAdapter extends BaseAdapter implements View.OnClickListener {
    private List<PriceBean.ResponseBean> mDataList;
    private Context mContext;
    private String[] mSplit;
    private ZoomImageView mIv;
    private ArrayList<ZoomImageView> mImageViewList1;
    private String[] mSplit1;

    private String mBottompic;

    public PriceListAdapter(Context context, List<PriceBean.ResponseBean> list) {
        mContext = context;
        mDataList = list;
        mImageViewList1 = new ArrayList<>();

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
            convertView = View.inflate(mContext, R.layout.view_attribet_three, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView oilprice = (TextView) convertView.findViewById(R.id.oilprice);
        TextView sievetype = (TextView) convertView.findViewById(R.id.sievetype);
        TextView sieveprice = (TextView) convertView.findViewById(R.id.sieveprice);
        TextView oilbottomtype = (TextView) convertView.findViewById(R.id.oilbottomtype);
        TextView oilbottomprice = (TextView) convertView.findViewById(R.id.oilbottomprice);
        TextView abluentprice = (TextView) convertView.findViewById(R.id.abluentprice);
        TextView changeoil = (TextView) convertView.findViewById(R.id.changeoil);
        TextView initoilmass = (TextView) convertView.findViewById(R.id.initoilmass);
        TextView gravityoilmass = (TextView) convertView.findViewById(R.id.gravityoilmass);
        TextView loopoilmass = (TextView) convertView.findViewById(R.id.loopoilmass);
        TextView hour = (TextView) convertView.findViewById(R.id.hour);
        TextView hourprice = (TextView) convertView.findViewById(R.id.hourprice);
        TextView otherprice = (TextView) convertView.findViewById(R.id.otherprice);
        TextView totalprice = (TextView) convertView.findViewById(R.id.totalprice);

        sievetype.setOnClickListener(this);


        //滤网图片
        mBottompic = mDataList.get(position).getSievepic();



        name.setText(mDataList.get(position).getName());
        oilprice.setText(mDataList.get(position).getOilprice() + "");
        sievetype.setText(mDataList.get(position).getSievetype());
        sieveprice.setText(mDataList.get(position).getSieveprice() + "");
        oilbottomtype.setText(mDataList.get(position).getOilbottomtype());
        oilbottomprice.setText(mDataList.get(position).getOilbottomprice() + "");
        abluentprice.setText(mDataList.get(position).getAbluentprice() + "");
        changeoil.setText(mDataList.get(position).getChangeoil() + "");
        initoilmass.setText(mDataList.get(position).getInitoilmass() + "");
        gravityoilmass.setText(mDataList.get(position).getGravityoilmass() + "");
        loopoilmass.setText(mDataList.get(position).getLoopoilmass() + "");
        hour.setText(mDataList.get(position).getHour() + "");

        hourprice.setText(mDataList.get(position).getHourprice() + "");

        otherprice.setText(mDataList.get(position).getOtherprice() + "");

        totalprice.setText(mDataList.get(position).getTotalprice() + "");



        return convertView;
    }



    public void setDataList(List<PriceBean.ResponseBean> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Mydialog mydialog = new Mydialog(mContext,R.style.dialog);
        mydialog.setCanceledOnTouchOutside(true);
        switch (v.getId()) {
            //滤网图片
            case R.id.sievetype:
                if (TextUtils.isEmpty(mBottompic) && "".equals(mBottompic)){
                    Toast.makeText(mContext,"没有图片",Toast.LENGTH_SHORT).show();
                }else {
                    mydialog.setData(mBottompic);
                    mydialog.show();

                }
                break;

        }
    }
}
