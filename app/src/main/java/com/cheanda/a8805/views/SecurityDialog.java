package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.cheanda.a8805.R;

/**
 * Created by dell on 2017/6/6.
 */

public class SecurityDialog extends Dialog{
    private RedioButtpmnListener mRedioButtpmnListener;
    public SecurityDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_security_dialog);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.redio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.open:
                        mRedioButtpmnListener.onButtonListener("开放");
                        dismiss();
                        break;
                    case R.id.wep:
                        mRedioButtpmnListener.onButtonListener("WEP");
                        dismiss();
                        break;
                    case R.id.wpa:
                        mRedioButtpmnListener.onButtonListener("PA/WPA2 PSK");
                        dismiss();
                        break;
                }
            }
        });
    }
    public interface RedioButtpmnListener{
        void onButtonListener(String text);
    }
    public void setOnRedioButtpmnListener(RedioButtpmnListener r){
        mRedioButtpmnListener = r;
    }
}
