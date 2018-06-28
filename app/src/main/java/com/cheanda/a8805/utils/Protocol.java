package com.cheanda.a8805.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dell on 2017/3/15.
 */
public abstract class Protocol {
    private static Map<String,Object> params = new HashMap<>();
    /**
     * 设置参数
     * @param params
     */
    public void setParams(Map<String, Object> params) {
        Protocol.params = params;
    }


    /**
     * 触发网络请求
     */
    public void loadDataFromNet() {
        OkhttpUtil.doPost(params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                errorManage(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String string = response.body().string();
                parseData(gson,string);
            }
        });
    }


    /**
     * 异常处理
     * @param e
     */
    public abstract void errorManage(IOException e);
    /**
     * 解析数据
     * @param gson
     * @param s
     */
    public abstract void parseData(Gson gson, String s);


}
