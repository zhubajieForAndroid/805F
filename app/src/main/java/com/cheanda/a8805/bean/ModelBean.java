package com.cheanda.a8805.bean;

import java.util.List;

/**
 * Created by dell on 2017/3/8.
 */

public class ModelBean {

    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"id":"32124","yearandconfiginfo":"2006 3.0TDI 手自一体 Quattro"},{"id":"321","yearandconfiginfo":"2006 3.6FSI 手自一体 Quattro基本型"},{"id":"338","yearandconfiginfo":"2006 4.2FSI 手自一体 Quattro技术型"},{"id":"323","yearandconfiginfo":"2007 3.6FSI 手自一体 Quattro技术型"},{"id":"328","yearandconfiginfo":"2007 3.6FSI 手自一体 Quattro舒适型"},{"id":"320","yearandconfiginfo":"2007 3.6FSI 手自一体 Quattro豪华型"},{"id":"340","yearandconfiginfo":"2007 4.2FSI 手自一体 Quattro舒适型"},{"id":"336","yearandconfiginfo":"2007 4.2FSI 手自一体 Quattro豪华型"},{"id":"32123","yearandconfiginfo":"2007 4.2TDI 手自一体 Quattro 柴油版"},{"id":"326","yearandconfiginfo":"2009 3.6FSI 手自一体 Quattro技术越野版"},{"id":"322","yearandconfiginfo":"2009 3.6FSI 手自一体 Quattro技术风尚版"},{"id":"331","yearandconfiginfo":"2009 3.6FSI 手自一体 Quattro舒适越野版"},{"id":"327","yearandconfiginfo":"2009 3.6FSI 手自一体 Quattro舒适风尚版"},{"id":"32116","yearandconfiginfo":"2010 3.0TDI 手自一体 Quattro"},{"id":"283","yearandconfiginfo":"2010 3.0TDI 手自一体 Quattro领先型运动典藏版"},{"id":"284","yearandconfiginfo":"2010 3.0TDI 手自一体 领先型"},{"id":"324","yearandconfiginfo":"2010 3.6FSI 手自一体 Quattro技术型越野典藏版"},{"id":"325","yearandconfiginfo":"2010 3.6FSI 手自一体 Quattro技术型运动典藏版"},{"id":"329","yearandconfiginfo":"2010 3.6FSI 手自一体 Quattro舒适型越野典藏版"},{"id":"330","yearandconfiginfo":"2010 3.6FSI 手自一体 Quattro舒适型运动典藏版"},{"id":"333","yearandconfiginfo":"2010 3.6FSI 手自一体 基本型"},{"id":"334","yearandconfiginfo":"2010 3.6FSI 手自一体 技术型"},{"id":"335","yearandconfiginfo":"2010 3.6FSI 手自一体 舒适型"},{"id":"332","yearandconfiginfo":"2010 3.6FSI 手自一体 豪华型"},{"id":"339","yearandconfiginfo":"2010 4.2FSI 手自一体 Quattro技术型"},{"id":"337","yearandconfiginfo":"2010 4.2FSI 手自一体 Quattro豪华型"},{"id":"341","yearandconfiginfo":"2010 6.0TDI 手自一体 V12 Quattro旗舰型"},{"id":"316","yearandconfiginfo":"2011 3.0TFSI 手自一体 专享型 200kw"},{"id":"318","yearandconfiginfo":"2011 3.0TFSI 手自一体 专享型 245kw"},{"id":"306","yearandconfiginfo":"2011 3.0TFSI 手自一体 技术型 200kw"},{"id":"308","yearandconfiginfo":"2011 3.0TFSI 手自一体 技术型 245kw"},{"id":"312","yearandconfiginfo":"2011 3.0TFSI 手自一体 舒适型 200kw"},{"id":"314","yearandconfiginfo":"2011 3.0TFSI 手自一体 舒适型 245kw"},{"id":"310","yearandconfiginfo":"2011 3.0TFSI 手自一体 进取型 200kw"},{"id":"286","yearandconfiginfo":"2012 3.0TDI 手自一体 专享型"},{"id":"285","yearandconfiginfo":"2012 3.0TDI 手自一体 领先型"},{"id":"317","yearandconfiginfo":"2012 3.0TFSI 手自一体 专享型 200kw"},{"id":"319","yearandconfiginfo":"2012 3.0TFSI 手自一体 专享型 245kw"},{"id":"307","yearandconfiginfo":"2012 3.0TFSI 手自一体 技术型 200kw"},{"id":"309","yearandconfiginfo":"2012 3.0TFSI 手自一体 技术型 245kw"},{"id":"313","yearandconfiginfo":"2012 3.0TFSI 手自一体 舒适型 200kw"},{"id":"315","yearandconfiginfo":"2012 3.0TFSI 手自一体 舒适型 245kw"},{"id":"305","yearandconfiginfo":"2012 3.0TFSI 手自一体 豪华型 245kw"},{"id":"311","yearandconfiginfo":"2012 3.0TFSI 手自一体 进取型 200kw"},{"id":"342","yearandconfiginfo":"2012 6.0TDI 手自一体 旗舰型"},{"id":"280","yearandconfiginfo":"2013 3.0TDI 手自一体 35TDI 专享型"},{"id":"277","yearandconfiginfo":"2013 3.0TDI 手自一体 35TDI 领先型"},{"id":"295","yearandconfiginfo":"2013 3.0TFSI 手自一体 35TFSI 专享型"},{"id":"287","yearandconfiginfo":"2013 3.0TFSI 手自一体 35TFSI 技术型"},{"id":"291","yearandconfiginfo":"2013 3.0TFSI 手自一体 35TFSI 舒适型"},{"id":"288","yearandconfiginfo":"2013 3.0TFSI 手自一体 35TFSI 进取型"},{"id":"302","yearandconfiginfo":"2013 3.0TFSI 手自一体 40TFSI 专享型"},{"id":"298","yearandconfiginfo":"2013 3.0TFSI 手自一体 40TFSI 技术型"},{"id":"299","yearandconfiginfo":"2013 3.0TFSI 手自一体 40TFSI 舒适型"},{"id":"281","yearandconfiginfo":"2014 3.0TDI 手自一体 35TDI 专享型"},{"id":"278","yearandconfiginfo":"2014 3.0TDI 手自一体 35TDI 越野型"},{"id":"279","yearandconfiginfo":"2014 3.0TDI 手自一体 35TDI 运动型"},{"id":"296","yearandconfiginfo":"2014 3.0TFSI 手自一体 35TFSI 专享型"},{"id":"292","yearandconfiginfo":"2014 3.0TFSI 手自一体 35TFSI 越野型"},{"id":"293","yearandconfiginfo":"2014 3.0TFSI 手自一体 35TFSI 运动型"},{"id":"289","yearandconfiginfo":"2014 3.0TFSI 手自一体 35TFSI 进取型"},{"id":"303","yearandconfiginfo":"2014 3.0TFSI 手自一体 40TFSI 专享型"},{"id":"300","yearandconfiginfo":"2014 3.0TFSI 手自一体 40TFSI 越野型"},{"id":"301","yearandconfiginfo":"2014 3.0TFSI 手自一体 40TFSI 运动型"},{"id":"32122","yearandconfiginfo":"2014 3.0TFSI 手自一体 欧规"},{"id":"282","yearandconfiginfo":"2015 3.0TDI 手自一体 35TDI 卓越版"},{"id":"32113","yearandconfiginfo":"2015 3.0TDI 手自一体 Premium Plus 美规版"},{"id":"32114","yearandconfiginfo":"2015 3.0TDI 手自一体 Premium 美规版"},{"id":"32115","yearandconfiginfo":"2015 3.0TDI 手自一体 Prestige 美规版"},{"id":"32117","yearandconfiginfo":"2015 3.0TFSI 手自一体"},{"id":"294","yearandconfiginfo":"2015 3.0TFSI 手自一体 35TFSI 运动型"},{"id":"290","yearandconfiginfo":"2015 3.0TFSI 手自一体 35TFSI 进取型"},{"id":"297","yearandconfiginfo":"2015 3.0TFSI 手自一体 40TFSI 典藏型"},{"id":"304","yearandconfiginfo":"2015 3.0TFSI 手自一体 40TFSI 尊藏型"},{"id":"32118","yearandconfiginfo":"2015 3.0TFSI 手自一体 Premium Plus 美规版"},{"id":"32119","yearandconfiginfo":"2015 3.0TFSI 手自一体 Premium 美规版"},{"id":"32120","yearandconfiginfo":"2015 3.0TFSI 手自一体 Prestige 美规版"},{"id":"32121","yearandconfiginfo":"2015 3.0TFSI 手自一体 Prestige 美规版"},{"id":"272","yearandconfiginfo":"2016 2.0T 手自一体 40TFSI S-Line运动型"},{"id":"273","yearandconfiginfo":"2016 2.0T 手自一体 40TFSI 舒适型"},{"id":"274","yearandconfiginfo":"2016 3.0T 手自一体 45TFSI S-Line运动型"},{"id":"276","yearandconfiginfo":"2016 3.0T 手自一体 45TFSI 尊贵型"},{"id":"275","yearandconfiginfo":"2016 3.0T 手自一体 45TFSI 技术型"}]
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
         * id : 32124
         * yearandconfiginfo : 2006 3.0TDI 手自一体 Quattro
         */

        private String id;
        private String yearandconfiginfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYearandconfiginfo() {
            return yearandconfiginfo;
        }

        public void setYearandconfiginfo(String yearandconfiginfo) {
            this.yearandconfiginfo = yearandconfiginfo;
        }
    }
}
