package com.cheanda.a8805.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.ModelBean;

import java.util.List;


/**
 * Created by dell on 2017/3/8.
 */
public class ModelYearAdapter extends BaseAdapter{
    private Context mContext;
    private List<ModelBean.ResponseBean> mList;
    public ModelYearAdapter(Context context, List<ModelBean.ResponseBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.view_modelyear_listview,null);
            holder.model = (TextView) convertView.findViewById(R.id.modelyear);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelBean.ResponseBean responseBean = mList.get(position);
        holder.model.setText(responseBean.getYearandconfiginfo());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updataAdapter(List<ModelBean.ResponseBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView model;
    }

}
