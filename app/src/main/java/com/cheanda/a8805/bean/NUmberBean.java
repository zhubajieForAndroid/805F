package com.cheanda.a8805.bean;

/**
 * Created by dell on 2017/8/31.
 */

public class NUmberBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : null
     */

    private int code;
    private ExtraBean extra;
    private String message;
    private Object response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public static class ExtraBean {
    }
}
