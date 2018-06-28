package com.cheanda.a8805.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.bean.CarStyleBean;

import java.util.List;


/**
 * Created by dell on 2017/5/8.
 */
public class TypeListAdapter extends BaseAdapter{
    private Context mContext;
    private List<CarStyleBean.ResponseBean> mList;
    public TypeListAdapter(Context context, List<CarStyleBean.ResponseBean> list) {
        mContext = context;
        mList = list;
    }
    public void updataAdapter(List<CarStyleBean.ResponseBean> list){
        mList = list;
        notifyDataSetChanged();
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
        ViewHolder  holder ;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.view_models_listview,null);
            holder.style = (TextView) convertView.findViewById(R.id.styletype);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CarStyleBean.ResponseBean responseBean = mList.get(position);
        holder.style.setText(responseBean.getType());
        return convertView;
    }
    final static class ViewHolder {
        TextView style;
    }
}
