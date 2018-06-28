package com.cheanda.a8805.bean;

import java.util.List;

/**
 * Created by dell on 2017/3/16.
 */

public class PriceBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"abluentprice":20,"changeoil":"设备换油","gravityoilmass":"3.9L","hour":1,"hourprice":200,"initoilmass":"6.5L","loopoilmass":"9.7L","name":"DX-IIIH","oilbottompic":"","oilbottomprice":0,"oilbottomtype":"","oilprice":98,"otherprice":0,"sievepic":"","sieveprice":0,"sievetype":"","totalprice":1170.6}]
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
         * abluentprice : 20
         * changeoil : 设备换油
         * gravityoilmass : 3.9L
         * hour : 1
         * hourprice : 200
         * initoilmass : 6.5L
         * loopoilmass : 9.7L
         * name : DX-IIIH
         * oilbottompic :
         * oilbottomprice : 0
         * oilbottomtype :
         * oilprice : 98
         * otherprice : 0
         * sievepic :
         * sieveprice : 0
         * sievetype :
         * totalprice : 1170.6
         */

        private int abluentprice;
        private String changeoil;
        private String gravityoilmass;
        private int hour;
        private int hourprice;
        private String initoilmass;
        private String loopoilmass;
        private String name;
        private String oilbottompic;
        private int oilbottomprice;
        private String oilbottomtype;
        private int oilprice;
        private int otherprice;
        private String sievepic;
        private int sieveprice;
        private String sievetype;
        private double totalprice;

        public int getAbluentprice() {
            return abluentprice;
        }

        public void setAbluentprice(int abluentprice) {
            this.abluentprice = abluentprice;
        }

        public String getChangeoil() {
            return changeoil;
        }

        public void setChangeoil(String changeoil) {
            this.changeoil = changeoil;
        }

        public String getGravityoilmass() {
            return gravityoilmass;
        }

        public void setGravityoilmass(String gravityoilmass) {
            this.gravityoilmass = gravityoilmass;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getHourprice() {
            return hourprice;
        }

        public void setHourprice(int hourprice) {
            this.hourprice = hourprice;
        }

        public String getInitoilmass() {
            return initoilmass;
        }

        public void setInitoilmass(String initoilmass) {
            this.initoilmass = initoilmass;
        }

        public String getLoopoilmass() {
            return loopoilmass;
        }

        public void setLoopoilmass(String loopoilmass) {
            this.loopoilmass = loopoilmass;
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

        public int getOilbottomprice() {
            return oilbottomprice;
        }

        public void setOilbottomprice(int oilbottomprice) {
            this.oilbottomprice = oilbottomprice;
        }

        public String getOilbottomtype() {
            return oilbottomtype;
        }

        public void setOilbottomtype(String oilbottomtype) {
            this.oilbottomtype = oilbottomtype;
        }

        public int getOilprice() {
            return oilprice;
        }

        public void setOilprice(int oilprice) {
            this.oilprice = oilprice;
        }

        public int getOtherprice() {
            return otherprice;
        }

        public void setOtherprice(int otherprice) {
            this.otherprice = otherprice;
        }

        public String getSievepic() {
            return sievepic;
        }

        public void setSievepic(String sievepic) {
            this.sievepic = sievepic;
        }

        public int getSieveprice() {
            return sieveprice;
        }

        public void setSieveprice(int sieveprice) {
            this.sieveprice = sieveprice;
        }

        public String getSievetype() {
            return sievetype;
        }

        public void setSievetype(String sievetype) {
            this.sievetype = sievetype;
        }

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }
    }
}
