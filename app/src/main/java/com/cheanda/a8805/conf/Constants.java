package com.cheanda.a8805.conf;

/**
 * Created by dell on 2017/3/15.
 */

public class Constants {

    public static final String HOST = "https://www.haoanda.cn/Cadapi";
    public static final String USER_NAME = "equipment";
    public static final String ACTIVITY_STATE = "finish";
    public static final String DATA_RESULT = "data";


    public static final class URLS {
        public static final String BRAND_URL = HOST + "/findBrand.action";
        public static final String MODELS_URL = HOST +"/findType.action";
        public static final String MODEYEAR_URL = HOST + "/findYearAndConfiginfo.action";
        public static final String RESULT1_URL = HOST + "/findFourForOne.action";
        public static final String RESULT2_URL = HOST + "/findFourForTwo.action";
        public static final String RESULT3_URL = HOST + "/findFourForThree.action";
        //获取设备列表
        public static final String GET_EQUIPMENT_TYPE ="https://www.haoanda.cn/weixin/manage/getEquipmentTypeList";
        //获取公司列表
        public static final String GET_COMAPNY_TYPE = "https://www.haoanda.cn/weixin/manage/getCompanyList";
        //开启上线
        public static final String START_ONLINE = "https://www.haoanda.cn/weixin/manage/createNewEquipment";
        //二维码图片
        public static final String QR_CODE = "https://www.haoanda.cn/qrCode/CAD/erweima.png";

        //检查版本
        public static final String CHECKAPK_URL = HOST +"/appConfigGet.action";
        //查询图片的地址
        public static final String QUERY_URL = "https://www.haoanda.cn/CAD";
        //vin
        public static final String QUERY_VIN_URL = HOST + "/vinInfoData.action";
        //查询次数
        public static final String QUERY_NUMBER_URL = HOST + "/getQueryRightMap.action";
        //序列号
        public static final String QUERY_XULIE_URL = HOST + "/getEquipmentID.action";
        //上传
        public static final String QUERY_UPLOAD_URL = HOST + "/uploadChangeOli.action";
        //获取统计数据
        public static final String QUERY_COUNT_DATA_URL = HOST + "/getReportData.action";
    }
}
