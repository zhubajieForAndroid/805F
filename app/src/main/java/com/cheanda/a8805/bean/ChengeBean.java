package com.cheanda.a8805.bean;


import java.util.List;

/**
 * Created by dell on 2017/3/17.
 */

public class ChengeBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"changeoil":"设备换油","changeoilpic":"https://jecking168.gicp.net:443/CAD/attached//201704/A04皇冠锐志接头（老款）1.jpg","gravityoilmass":"4L","initoilmass":"7L","jtname":"A42","jtpic":"https://jecking168.gicp.net:443/CAD/attached//201704/A42.png","loopoilmass":"12L","name":"","oilcheckmode":"通过变速箱油尺检查","oilcheckpic":"https://jecking168.gicp.net:443/CAD/attached//201704/A03.jpg","sievepic":"","sievetype":"","ypname":"ATF 8HP(德系)","yppic":"https://jecking168.gicp.net:443/CAD/attached//201704/A42.png"}]
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
         * changeoil : 设备换油
         * changeoilpic : https://jecking168.gicp.net:443/CAD/attached//201704/A04皇冠锐志接头（老款）1.jpg
         * gravityoilmass : 4L
         * initoilmass : 7L
         * jtname : A42
         * jtpic : https://jecking168.gicp.net:443/CAD/attached//201704/A42.png
         * loopoilmass : 12L
         * name :
         * oilcheckmode : 通过变速箱油尺检查
         * oilcheckpic : https://jecking168.gicp.net:443/CAD/attached//201704/A03.jpg
         * sievepic :
         * sievetype :
         * ypname : ATF 8HP(德系)
         * yppic : https://jecking168.gicp.net:443/CAD/attached//201704/A42.png
         */

        private String changeoil;
        private String changeoilpic;
        private String gravityoilmass;
        private String initoilmass;
        private String jtname;
        private String jtpic;
        private String loopoilmass;
        private String name;
        private String oilcheckmode;
        private String oilcheckpic;
        private String sievepic;
        private String sievetype;
        private String ypname;
        private String yppic;

        public String getChangeoil() {
            return changeoil;
        }

        public void setChangeoil(String changeoil) {
            this.changeoil = changeoil;
        }

        public String getChangeoilpic() {
            return changeoilpic;
        }

        public void setChangeoilpic(String changeoilpic) {
            this.changeoilpic = changeoilpic;
        }

        public String getGravityoilmass() {
            return gravityoilmass;
        }

        public void setGravityoilmass(String gravityoilmass) {
            this.gravityoilmass = gravityoilmass;
        }

        public String getInitoilmass() {
            return initoilmass;
        }

        public void setInitoilmass(String initoilmass) {
            this.initoilmass = initoilmass;
        }

        public String getJtname() {
            return jtname;
        }

        public void setJtname(String jtname) {
            this.jtname = jtname;
        }

        public String getJtpic() {
            return jtpic;
        }

        public void setJtpic(String jtpic) {
            this.jtpic = jtpic;
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

        public String getOilcheckmode() {
            return oilcheckmode;
        }

        public void setOilcheckmode(String oilcheckmode) {
            this.oilcheckmode = oilcheckmode;
        }

        public String getOilcheckpic() {
            return oilcheckpic;
        }

        public void setOilcheckpic(String oilcheckpic) {
            this.oilcheckpic = oilcheckpic;
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

        public String getYpname() {
            return ypname;
        }

        public void setYpname(String ypname) {
            this.ypname = ypname;
        }

        public String getYppic() {
            return yppic;
        }

        public void setYppic(String yppic) {
            this.yppic = yppic;
        }
    }
}
