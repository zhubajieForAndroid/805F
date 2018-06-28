package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheanda.a8805.R;

import org.w3c.dom.Text;

/**
 * Created by dell on 2017/5/23.
 */

public class PhoneDialog extends Dialog implements View.OnTouchListener {
    private  Bitmap mBitmap;
    private  Context mContext;
    private static final String TAG = "SettingActivity";
    private EditText mPhoneEt;

    public PhoneDialog(Context context, int themeResId, Bitmap bitmap) {
        super(context, themeResId);
        mContext =context;
        mBitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_phone_dialog);
        ImageView iv = (ImageView) findViewById(R.id.clear_dialog_bg);
        mPhoneEt = (EditText) findViewById(R.id.phone_dialog_et);
        //读取电话号码
        SharedPreferences sp = mContext.getSharedPreferences("phoneFile",0);
        String phone = sp.getString("phone", "");
        if (!TextUtils.isEmpty(phone)){
            mPhoneEt.setText(phone);
        }

        iv.setImageBitmap(mBitmap);
        iv.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        if (x>428 && x<530 &&y>262 &&y<300){
            //保存电话号码
            String phone = mPhoneEt.getText().toString().trim();
            String telRegex = "[1][3456789]\\d{9}";
            if (!TextUtils.isEmpty(phone)){
                if (phone.matches(telRegex)){
                    //保存到文件
                    SharedPreferences sp = mContext.getSharedPreferences("phoneFile",0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("phone",phone);
                    edit.commit();
                    Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
                    dismiss();
                }else {
                    Toast.makeText(mContext,"非法的手机号",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(mContext,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            }

        }else if (x>58 && x<160 &&y>262 &&y<290){
            dismiss();
        }
        return false;
    }
}
