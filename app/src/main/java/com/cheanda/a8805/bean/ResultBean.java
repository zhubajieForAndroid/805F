package com.cheanda.a8805.bean;


import java.util.List;

/**
 * Created by dell on 2017/3/8.
 */

public class ResultBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"bottompic":"","boxtype":"A750F","brand":"丰田","comeinyear":"2007","configinfo":"4.7 自动 VX-R","gearbox":"五速四驱","jtname":"A06","name":"WS","oilbottompic":"","oilbottomtype":"","sievepic":"","sievetype":"","stopyear":"2012","type":"Land Cruiser [兰德酷路泽]","year":"2007"}]
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
         * bottompic :
         * boxtype : A750F
         * brand : 丰田
         * comeinyear : 2007
         * configinfo : 4.7 自动 VX-R
         * gearbox : 五速四驱
         * jtname : A06
         * name : WS
         * oilbottompic :
         * oilbottomtype :
         * sievepic :
         * sievetype :
         * stopyear : 2012
         * type : Land Cruiser [兰德酷路泽]
         * year : 2007
         */

        private String bottompic;
        private String boxtype;
        private String brand;
        private String comeinyear;
        private String configinfo;
        private String gearbox;
        private String jtname;
        private String name;
        private String oilbottompic;
        private String oilbottomtype;
        private String sievepic;
        private String sievetype;
        private String stopyear;
        private String type;
        private String year;

        public String getBottompic() {
            return bottompic;
        }

        public void setBottompic(String bottompic) {
            this.bottompic = bottompic;
        }

        public String getBoxtype() {
            return boxtype;
        }

        public void setBoxtype(String boxtype) {
            this.boxtype = boxtype;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getComeinyear() {
            return comeinyear;
        }

        public void setComeinyear(String comeinyear) {
            this.comeinyear = comeinyear;
        }

        public String getConfiginfo() {
            return configinfo;
        }

        public void setConfiginfo(String configinfo) {
            this.configinfo = configinfo;
        }

        public String getGearbox() {
            return gearbox;
        }

        public void setGearbox(String gearbox) {
            this.gearbox = gearbox;
        }

        public String getJtname() {
            return jtname;
        }

        public void setJtname(String jtname) {
            this.jtname = jtname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOilbottompic() {
            return oilbottompic;
        }

        public void setOilbottompic(String oilbottompic) {
            this.oilbottompic = oilbottompic;
        }

        public String getOilbottomtype() {
            return oilbottomtype;
        }

        public void setOilbottomtype(String oilbottomtype) {
            this.oilbottomtype = oilbottomtype;
        }

        public String getSievepic() {
            return sievepic;
        }

        public void setSievepic(String sievepic) {
            this.sievepic = sievepic;
        }

        public String getSievetype() {
            return sievetype;
        }

        public void setSievetype(String sievetype) {
            this.sievetype = sievetype;
        }

        public String getStopyear() {
            return stopyear;
        }

        public void setStopyear(String stopyear) {
            this.stopyear = stopyear;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
