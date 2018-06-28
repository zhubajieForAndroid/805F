package com.cheanda.a8805.bean;

import java.util.List;

/**
 * Created by dell on 2017/6/7.
 */

public class  ApkCheckVersionBean  {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"appname":"","appurl":"https://www.haoanda.cn:443/CAD/attached//201706/车安达2.0.apk","appver":"V2.0","updatecontent":"更新了查询功能，优化了增加功能"}]
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
         * appname :
         * appurl : https://www.haoanda.cn:443/CAD/attached//201706/车安达2.0.apk
         * appver : V2.0
         * updatecontent : 更新了查询功能，优化了增加功能
         */

        private String appname;
        private String appurl;
        private String appver;
        private String updatecontent;

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getAppurl() {
            return appurl;
        }

        public void setAppurl(String appurl) {
            this.appurl = appurl;
        }

        public String getAppver() {
            return appver;
        }

        public void setAppver(String appver) {
            this.appver = appver;
        }

        public String getUpdatecontent() {
            return updatecontent;
        }

        public void setUpdatecontent(String updatecontent) {
            this.updatecontent = updatecontent;
        }
    }
}
