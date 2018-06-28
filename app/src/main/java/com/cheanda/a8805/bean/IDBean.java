package com.cheanda.a8805.bean;

import java.util.List;

/**
 * Created by dell on 2017/8/9.
 */

public class IDBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"id":"00100004"}]
     */

    private int code;
    private ExtraBean extra;
    private String message;
    private List<ResponseBean> response;

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

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ExtraBean {
    }

    public static class ResponseBean {
        /**
         * id : 00100004
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
