package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.cheanda.a8805.utils.BItmapUtil;

/**
 * Created by dell on 2017/5/12.
 */

public class StstemSettingDialog extends Dialog implements View.OnTouchListener {
    private static final String TAG = "SettingActivity";
    private Context mContext;
    private String title;
    private OnBtnListener mOnBtnListener;
    private EditText mEt;

    public StstemSettingDialog(Context context, int themeResId,String s) {
        super(context, themeResId);
        mContext = context;
        title = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_system_setting_dialog);
        ImageView iv = (ImageView) findViewById(R.id.dialog_bg);
        Bitmap bitmap = BItmapUtil.compressBItmap(mContext, R.mipmap.input_text_bg_1);
        iv.setImageBitmap(bitmap);
        iv.setOnTouchListener(this);

        TextView tv = (TextView) findViewById(R.id.dialog_title);
        tv.setText(title);
        mEt = (EditText) findViewById(R.id.dialog_et);




    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x> 85 && x<230 &&y>240 &&y<290){
            dismiss();
        }else if (x> 355 && x<500 &&y>240 &&y<293){
            String data = mEt.getText().toString().trim();
            if (!TextUtils.isEmpty(data)){
                mOnBtnListener.btnListener(data);
                dismiss();
            }else {
                Toast.makeText(mContext,"内容不能为空",Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    public void setEdInputSize(int edInputSize) {
        mEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(edInputSize)});
    }

    public void setIsStyle(boolean isStyle) {
        if (isStyle){
            //设置输入类型
            mEt.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public interface OnBtnListener{
        void btnListener(String data);
    }
    public void setOnBtnListener(OnBtnListener listener){
        mOnBtnListener = listener;
    }

}
