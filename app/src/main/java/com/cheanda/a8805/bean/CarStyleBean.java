package com.cheanda.a8805.bean;

import java.util.List;

/**
 * Created by dell on 2017/3/8.
 */

public class CarStyleBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"type":"Q7"},{"type":"R8 Coupe"},{"type":"TT Coupe"},{"type":"A7 Sportback"},{"type":"A5 Cabriolet"},{"type":"TT Roadster"},{"type":"A6 Hybrid"},{"type":"A6"},{"type":"A3 Sportback"},{"type":"A1 Sportback"},{"type":"A8L W12"},{"type":"A6 Avant"},{"type":"S3"},{"type":"80 S2"},{"type":"A2"},{"type":"A3"},{"type":"S4"},{"type":"A5 Sportback"},{"type":"A1"},{"type":"100"},{"type":"80 Avant S2"},{"type":"S4 Avant"},{"type":"S6 Avant"},{"type":"80 Avant RS2"},{"type":"TT RS Roadster"},{"type":"RS7 Sportback"},{"type":"200"},{"type":"80 Avant"},{"type":"RS5 Coupe"},{"type":"S6"},{"type":"A5 Coupe"},{"type":"S5 Sportback"},{"type":"Q3"},{"type":"A3 Cabriolet"},{"type":"S3 Limousine"},{"type":"A4"},{"type":"100 Avant"},{"type":"A3 Sportback e-tron"},{"type":"A8 W12"},{"type":"TT RS Coupe"},{"type":"Q5"},{"type":"S5 Cabriolet"},{"type":"TTS Coupe"},{"type":"RS4 Avant"},{"type":"RS4 Cabriolet"},{"type":"TT"},{"type":"R8 Spyder"},{"type":"S5 Coupe"},{"type":"A6 Allroad"},{"type":"A6L"},{"type":"RS4"},{"type":"RS6"},{"type":"S7 Sportback"},{"type":"A4 Allroad Quattro"},{"type":"TTS Roadster"},{"type":"S8"},{"type":"Q5 Hybrid"},{"type":"SQ5"},{"type":"A3 Limousine"},{"type":"S4 Cabriolet"},{"type":"RS5 Cabriolet"},{"type":"A8L"},{"type":"A8L Hybrid"},{"type":"A4L"},{"type":"A4 Cabriolet"},{"type":"RS6 Avant"},{"type":"A8"},{"type":"RS3"}]
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
         * type : Q7
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
