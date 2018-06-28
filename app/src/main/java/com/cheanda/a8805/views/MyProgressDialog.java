package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.cheanda.a8805.R;

/**
 * Created by dell on 2017/5/15.
 */

public class MyProgressDialog extends Dialog implements View.OnTouchListener {
    private static final String TAG = "MyProgressDialog";
    private Bitmap mBitmap;
    private Context mContext;
    private OnButtonListener mOnButtonListener;
    public MyProgressDialog(Context context, int themeResId, Bitmap bitmap) {
        super(context, themeResId);
        mContext =context;
        mBitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clear_dialog);
        ImageView iv = (ImageView) findViewById(R.id.clear_dialog_bg);
        iv.setImageBitmap(mBitmap);
        iv.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x > 60 && x < 175 && y > 168 && y < 224) {
            //无
            mOnButtonListener.iscancle(false);
            dismiss();
        }else if (x > 304 && x < 428 && y > 160 && y < 220) {
            //有
            mOnButtonListener.iscancle(true);
            dismiss();
        }
        return false;
    }
    public void setOnButtonListener(OnButtonListener o){
        mOnButtonListener = o;
    }
    public interface OnButtonListener{
        void  iscancle(boolean b);
    }


}
