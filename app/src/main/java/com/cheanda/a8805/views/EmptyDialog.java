package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.cheanda.a8805.R;

/**
 * Created by dell on 2018/1/30.
 */

public class EmptyDialog extends Dialog {

    private Bitmap mBitmap;

    public EmptyDialog(Context context, int themeResId, Bitmap bitmap) {
        super(context, themeResId);
        mBitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_empty_dialog);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageBitmap(mBitmap);
    }
}
