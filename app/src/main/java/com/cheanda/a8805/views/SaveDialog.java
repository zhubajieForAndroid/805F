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

public class SaveDialog extends Dialog implements View.OnTouchListener {
    private static final String TAG = "MyProgressDialog";
    private Bitmap mBitmap;
    private Context mContext;
    private OnButtonListener mOnButtonListener;
    public SaveDialog(Context context, int themeResId, Bitmap bitmap) {
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
        if (x>73 && x<223 && y>198 &&y<255){
            //取消
            mOnButtonListener.iscancle(false);
        }else if (x>375 && x<520 && y>198 &&y<262){
            //确定删除
            mOnButtonListener.iscancle(true);
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
