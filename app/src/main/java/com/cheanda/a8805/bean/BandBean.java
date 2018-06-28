package com.cheanda.a8805.bean;


import java.util.List;

/**
 * Created by dell on 2017/a003/a003.
 */

public class BandBean {


    /**
     * code : 0
     * extra : {}
     * message : 请求成功
     * response : [{"brand":"奥迪"},{"brand":"MINI"},{"brand":"兰博基尼"},{"brand":"起亚"},{"brand":"凯迪拉克"},{"brand":"绅宝"},{"brand":"腾势"},{"brand":"哈飞"},{"brand":"江南"},{"brand":"金程"},{"brand":"风神"},{"brand":"东风"},{"brand":"昌河"},{"brand":"双环"},{"brand":"宝骏"},{"brand":"华颂"},{"brand":"新雅途"},{"brand":"哈弗"},{"brand":"MG"},{"brand":"欧朗"},{"brand":"大发"},{"brand":"布加迪"},{"brand":"东风风度"},{"brand":"华北"},{"brand":"帕加尼"},{"brand":"保时捷"},{"brand":"捷豹"},{"brand":"劳斯莱斯"},{"brand":"玛莎拉蒂"},{"brand":"纳智捷"},{"brand":"启腾"},{"brand":"莲花"},{"brand":"万丰"},{"brand":"黑豹"},{"brand":"江淮"},{"brand":"帝豪"},{"brand":"天马"},{"brand":"解放"},{"brand":"汇众"},{"brand":"alpina"},{"brand":"劳伦士"},{"brand":"庞蒂克"},{"brand":"路特斯"},{"brand":"标致"},{"brand":"双龙"},{"brand":"三菱"},{"brand":"宝龙"},{"brand":"威兹曼"},{"brand":"威旺"},{"brand":"幻速"},{"brand":"吉奥"},{"brand":"东南"},{"brand":"九龙"},{"brand":"风行"},{"brand":"大迪"},{"brand":"福迪"},{"brand":"瑞麒"},{"brand":"开瑞"},{"brand":"红旗"},{"brand":"众泰"},{"brand":"长安商用"},{"brand":"英致"},{"brand":"海格"},{"brand":"云雀"},{"brand":"宝马"},{"brand":"Smart"},{"brand":"林肯"},{"brand":"克莱斯勒"},{"brand":"欧宝"},{"brand":"RUF"},{"brand":"沃尔沃"},{"brand":"DS"},{"brand":"江铃"},{"brand":"五十铃"},{"brand":"卡威"},{"brand":"东风小康"},{"brand":"理念"},{"brand":"恒天"},{"brand":"五菱"},{"brand":"SPRINGO"},{"brand":"陆风"},{"brand":"力帆"},{"brand":"华阳"},{"brand":"安驰"},{"brand":"通田"},{"brand":"佳星"},{"brand":"陕汽通家"},{"brand":"光冈"},{"brand":"阿尔法-罗密欧"},{"brand":"卡尔森"},{"brand":"本田"},{"brand":"宾利"},{"brand":"奔驰"},{"brand":"斯柯达"},{"brand":"路虎"},{"brand":"铃木"},{"brand":"别克"},{"brand":"北汽"},{"brand":"英伦"},{"brand":"观致"},{"brand":"野马"},{"brand":"吉利"},{"brand":"华泰"},{"brand":"长安"},{"brand":"海马"},{"brand":"大宇"},{"brand":"萨博"},{"brand":"赛宝"},{"brand":"大众"},{"brand":"西雅特"},{"brand":"丰田"},{"brand":"雷克萨斯"},{"brand":"福特"},{"brand":"英菲尼迪"},{"brand":"迈凯伦"},{"brand":"北京"},{"brand":"金杯"},{"brand":"启辰"},{"brand":"猎豹"},{"brand":"思铭"},{"brand":"荣威"},{"brand":"奔腾"},{"brand":"长城"},{"brand":"夏利"},{"brand":"通用"},{"brand":"Scion"},{"brand":"扬子"},{"brand":"宝沃"},{"brand":"悍马"},{"brand":"GMC"},{"brand":"保斐利"},{"brand":"世爵"},{"brand":"科尼赛克"},{"brand":"a005"},{"brand":"讴歌"},{"brand":"Jeep"},{"brand":"马自达"},{"brand":"特斯拉"},{"brand":"现代"},{"brand":"福田"},{"brand":"奇瑞"},{"brand":"威麟"},{"brand":"华普"},{"brand":"依维柯"},{"brand":"富奇"},{"brand":"中兴"},{"brand":"中欧"},{"brand":"迈巴赫"},{"brand":"阿斯顿马丁"},{"brand":"巴博斯"},{"brand":"法拉利"},{"brand":"菲亚特"},{"brand":"雷诺"},{"brand":"道奇"},{"brand":"日产"},{"brand":"斯巴鲁"},{"brand":"雪佛兰"},{"brand":"雪铁龙"},{"brand":"比亚迪"},{"brand":"全球鹰"},{"brand":"传祺"},{"brand":"西安奥拓"},{"brand":"黄海"},{"brand":"大通"},{"brand":"中华"},{"brand":"凯翼"},{"brand":"新凯"},{"brand":"一汽"},{"brand":"美亚"},{"brand":"永源"},{"brand":"庆铃"},{"brand":"中顺"},{"brand":"知豆"},{"brand":"罗孚"}]
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
         * brand : 奥迪
         */
        private String brand;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
