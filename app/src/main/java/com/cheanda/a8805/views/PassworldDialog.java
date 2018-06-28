package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cheanda.a8805.R;


/**
 * Created by dell on 2017/5/11.
 */

public class PassworldDialog extends Dialog implements View.OnClickListener {
    private  String mTitle;
    private Context mContext;
    private static final String TAG = "LoginDialog";
    private EditText mEt;
    private OnBtnListener mOnBtnListener;
    public PassworldDialog(Context context, int themeResId, String title) {
        super(context, themeResId);
        mContext = context;
        mTitle = title;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_wifi_dialog);
        mEt = (EditText) findViewById(R.id.et);
        Button lianjie = (Button) findViewById(R.id.lianjie);
        Button quxiao  = (Button) findViewById(R.id.quxiao);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mTitle);
        lianjie.setOnClickListener(this);
        quxiao.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lianjie:

                String passworld = mEt.getText().toString();
                if (!TextUtils.isEmpty(passworld)){
                    mOnBtnListener.btnListener(passworld);
                }
                break;
            case R.id.quxiao:
                dismiss();
                break;
        }
    }


    public interface OnBtnListener{
        void btnListener(String passworld);
    }
    public void setOnBtnListener(OnBtnListener listener){
        mOnBtnListener = listener;
    }


}
