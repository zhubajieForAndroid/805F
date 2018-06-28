package com.cheanda.a8805.bean;

import java.util.List;

/**
 * Created by dell on 2017/8/15.
 */

public class CountDataBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"ALLCOUNT":335,"ALLUSERCOUNT":2208,"GEARBOXCOUNT":11221,"OCRDISCERN":80,"PRICECOUNT":37329,"USEDCOUNT":292,"VALIDCOUNT":43,"VEHICLETYPECOUNT":37329,"VINQUERY":34}]
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
         * ALLCOUNT : 335
         * ALLUSERCOUNT : 2208
         * GEARBOXCOUNT : 11221
         * OCRDISCERN : 80
         * PRICECOUNT : 37329
         * USEDCOUNT : 292
         * VALIDCOUNT : 43
         * VEHICLETYPECOUNT : 37329
         * VINQUERY : 34
         */

        private int ALLCOUNT;
        private int ALLUSERCOUNT;
        private int GEARBOXCOUNT;
        private int OCRDISCERN;
        private int PRICECOUNT;
        private int USEDCOUNT;
        private int VALIDCOUNT;
        private int VEHICLETYPECOUNT;
        private int VINQUERY;

        public int getALLCOUNT() {
            return ALLCOUNT;
        }

        public void setALLCOUNT(int ALLCOUNT) {
            this.ALLCOUNT = ALLCOUNT;
        }

        public int getALLUSERCOUNT() {
            return ALLUSERCOUNT;
        }

        public void setALLUSERCOUNT(int ALLUSERCOUNT) {
            this.ALLUSERCOUNT = ALLUSERCOUNT;
        }

        public int getGEARBOXCOUNT() {
            return GEARBOXCOUNT;
        }

        public void setGEARBOXCOUNT(int GEARBOXCOUNT) {
            this.GEARBOXCOUNT = GEARBOXCOUNT;
        }

        public int getOCRDISCERN() {
            return OCRDISCERN;
        }

        public void setOCRDISCERN(int OCRDISCERN) {
            this.OCRDISCERN = OCRDISCERN;
        }

        public int getPRICECOUNT() {
            return PRICECOUNT;
        }

        public void setPRICECOUNT(int PRICECOUNT) {
            this.PRICECOUNT = PRICECOUNT;
        }

        public int getUSEDCOUNT() {
            return USEDCOUNT;
        }

        public void setUSEDCOUNT(int USEDCOUNT) {
            this.USEDCOUNT = USEDCOUNT;
        }

        public int getVALIDCOUNT() {
            return VALIDCOUNT;
        }

        public void setVALIDCOUNT(int VALIDCOUNT) {
            this.VALIDCOUNT = VALIDCOUNT;
        }

        public int getVEHICLETYPECOUNT() {
            return VEHICLETYPECOUNT;
        }

        public void setVEHICLETYPECOUNT(int VEHICLETYPECOUNT) {
            this.VEHICLETYPECOUNT = VEHICLETYPECOUNT;
        }

        public int getVINQUERY() {
            return VINQUERY;
        }

        public void setVINQUERY(int VINQUERY) {
            this.VINQUERY = VINQUERY;
        }
    }
}
