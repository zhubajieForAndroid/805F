package com.cheanda.a8805.dao;

/**
 * Created by dell on 2017/9/22.
 */

public class QueryDBBean {
   private String companyNamwe;//公司名称
   private Integer homeTopBg;//主页背景
   private Integer depthTopBg;//深度保养背景
   private Integer addTopBg;//添加油量名称
   private Integer reduceTopBg;//减少油量名称
   private Integer loopTopBg;//循环清洗名称
   private Integer changeTopBg;//智能换油

   private Integer emptyNewTopBg;//排空新邮箱
   private Integer emptyOldTopBg;//排空旧邮箱
   private Integer setting;//设置
   private Integer systemSetting;//系统设置
   private Integer about;//关于设备
   private Integer alineNewTopBg;//新油电子秤校准
   private Integer alineOldTopBg;//旧油电子秤校准
   private Integer wifiTopBg;//wifi链接
   private Integer selectIntent;//选择入口
   private Integer carType;//选择车型
   private Integer qureyResult;//查询结果
   private Integer startBgOne;//启动1
   private Integer startBgTwo;//启动2
   private Integer startBgThree;//启动3
   private Integer vinQuery;//vin查询

    public QueryDBBean(String companyNamwe, Integer homeTopBg, Integer depthTopBg, Integer addTopBg, Integer reduceTopBg, Integer loopTopBg, Integer changeTopBg, Integer emptyNewTopBg, Integer emptyOldTopBg, Integer setting, Integer systemSetting, Integer about, Integer alineNewTopBg, Integer alineOldTopBg, Integer wifiTopBg, Integer selectIntent, Integer carType, Integer qureyResult, Integer startBgOne, Integer startBgTwo, Integer startBgThree,Integer vinQuery) {
        this.companyNamwe = companyNamwe;
        this.homeTopBg = homeTopBg;
        this.depthTopBg = depthTopBg;
        this.addTopBg = addTopBg;
        this.reduceTopBg = reduceTopBg;
        this.loopTopBg = loopTopBg;
        this.changeTopBg = changeTopBg;
        this.vinQuery =vinQuery;
        this.emptyNewTopBg = emptyNewTopBg;
        this.emptyOldTopBg = emptyOldTopBg;
        this.setting = setting;
        this.systemSetting = systemSetting;
        this.about = about;
        this.alineNewTopBg = alineNewTopBg;
        this.alineOldTopBg = alineOldTopBg;
        this.wifiTopBg = wifiTopBg;
        this.selectIntent = selectIntent;
        this.carType = carType;
        this.qureyResult = qureyResult;
        this.startBgOne = startBgOne;
        this.startBgTwo = startBgTwo;
        this.startBgThree = startBgThree;
    }

    public Integer getVinQuery() {
        return vinQuery;
    }

    public String getCompanyNamwe() {
        return companyNamwe;
    }

    public Integer getHomeTopBg() {
        return homeTopBg;
    }

    public Integer getDepthTopBg() {
        return depthTopBg;
    }

    public Integer getAddTopBg() {
        return addTopBg;
    }

    public Integer getReduceTopBg() {
        return reduceTopBg;
    }

    public Integer getLoopTopBg() {
        return loopTopBg;
    }

    public Integer getChangeTopBg() {
        return changeTopBg;
    }



    public Integer getEmptyNewTopBg() {
        return emptyNewTopBg;
    }

    public Integer getEmptyOldTopBg() {
        return emptyOldTopBg;
    }

    public Integer getSetting() {
        return setting;
    }

    public Integer getSystemSetting() {
        return systemSetting;
    }

    public Integer getAbout() {
        return about;
    }

    public Integer getAlineNewTopBg() {
        return alineNewTopBg;
    }

    public Integer getAlineOldTopBg() {
        return alineOldTopBg;
    }

    public Integer getWifiTopBg() {
        return wifiTopBg;
    }

    public Integer getSelectIntent() {
        return selectIntent;
    }

    public Integer getCarType() {
        return carType;
    }

    public Integer getQureyResult() {
        return qureyResult;
    }

    public Integer getStartBgOne() {
        return startBgOne;
    }

    public Integer getStartBgTwo() {
        return startBgTwo;
    }

    public Integer getStartBgThree() {
        return startBgThree;
    }
}
