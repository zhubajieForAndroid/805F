package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cheanda.a8805.R;


/**
 * Created by dell on 2017/7/21.
 */

public class HintDialog extends Dialog {

    private Context mContext;
    public HintDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_hint_dialog, null);
        this.addContentView(layout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        layout.findViewById(R.id.btn_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window window = getWindow();
        window.setWindowAnimations(R.style.popuwindowAnimation);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
    }
}
