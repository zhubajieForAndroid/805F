package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheanda.a8805.R;

/**
 * Created by dell on 2017/5/24.
 */

public class MainDialog extends Dialog {
    private  Context mContext;
    private  Bitmap mBitmap;
    private String mPhone;

    public MainDialog(Context context, int themeResId, Bitmap bitmap,String phone) {
        super(context, themeResId);
        mContext =context;
        mBitmap = bitmap;
        mPhone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main_dialog);
        ImageView iv = (ImageView) findViewById(R.id.clear_dialog_bg);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(mPhone);
        iv.setImageBitmap(mBitmap);
    }
}
