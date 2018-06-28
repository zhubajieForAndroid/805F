package com.cheanda.a8805.utils;

import android.text.TextUtils;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by dell on 2017/9/14.
 */

public class OkhttpUtil {

    private static OkHttpClient client = null;

    private static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkhttpUtil.class) {
                if (client == null)
                    client = new OkHttpClient();
            }
        }
        return client;
    }

    /**
     * post请求
     * @param pames 请求的参数
     * @param callback 回调
     */
    public static void doPost(Map<String, Object> pames, Callback callback) {
        String url = "";
        FormBody.Builder body = new FormBody.Builder();
        if (pames.size() > 0) {
            for (String s : pames.keySet()) {
                String value = (String) pames.get(s);
                if (!TextUtils.isEmpty(value))
                    body.add(s, (String) pames.get(s));
                if ("url".equals(s)) {
                    url = (String) pames.get(s);
                }
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(body.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}
