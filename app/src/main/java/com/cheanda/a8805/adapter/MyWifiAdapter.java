package com.cheanda.a8805.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheanda.a8805.R;

import java.util.List;

/**
 * Created by dell on 2017/6/1.
 */
public class MyWifiAdapter extends BaseAdapter {
    private static final String TAG = "MyWifiAdapter";
    private Context mContext;
    private List<ScanResult> mList;

    public MyWifiAdapter(Context context, List<ScanResult> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
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
            convertView = View.inflate(mContext, R.layout.view_list, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.wifi_name);
        ImageView Strength = (ImageView) convertView.findViewById(R.id.wifi_strength);
        ScanResult result = mList.get(position);

        /*  0   —— (-55)dbm  满格(4格)信号
             (-55) —— (-70)dbm  3格信号
            (-70) —— (-85)dbm　2格信号
            (-85) —— (-100)dbm 1格信号*/
        if (result.level<0 && result.level>-55){
            //满格
            Strength.setImageResource(R.mipmap.wifi_four);
        }
        if (result.level<-55 && result.level>-70){
            //三格
            Strength.setImageResource(R.mipmap.wifi_three);
        }
        if (result.level<-70 && result.level>-85){
            //2格
            Strength.setImageResource(R.mipmap.wifi_two);
        }
        if (result.level<-85 && result.level>-100){
            //一格
            Strength.setImageResource(R.mipmap.wifi_one);
        }
        //过滤无信号的WiFi
        if (!(result.level<-100)){
            if (!result.SSID.equals(" "))
                name.setText(result.SSID);
        }

        return convertView;
    }
}
