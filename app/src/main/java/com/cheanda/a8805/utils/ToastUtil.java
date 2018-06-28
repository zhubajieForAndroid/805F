package com.cheanda.a8805.utils;

import android.widget.Toast;

import com.cheanda.a8805.base.MyApplication;

/**
 * Created by dell on 2017/12/5.
 */

public class ToastUtil {

    public static void showMessage(String data) {
        MyToast.makeText(MyApplication.getContext(), data, Toast.LENGTH_SHORT).show();
    }


}
