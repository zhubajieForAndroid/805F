package com.cheanda.a8805.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/6/1.
 */

public class WifiUtils {
    private Context mContext;
    //wifi manager
    private final WifiManager mManager;
    private final WifiInfo mInfo;
    private List<ScanResult> mWifiList;
    private List<WifiConfiguration> wifiConfiguration;

    public WifiUtils(Context context) {
        mContext = context;
        //得到WiFimangwe对象
        mManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mInfo = mManager.getConnectionInfo();
    }

    //打开WiFi
    public void openWifi() {
        if (!mManager.isWifiEnabled()) {
            mManager.setWifiEnabled(true);
            Toast.makeText(mContext, "开启成功", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(mContext, "WiFi已经开启", Toast.LENGTH_SHORT);
        }
    }

    //关闭WiFi
    public void closeWifi() {
        if (mManager.isWifiEnabled()) {
            mManager.setWifiEnabled(false);
        } else if (mManager.getWifiState() == 1) {
            Toast.makeText(mContext, "WiFi已经关闭", Toast.LENGTH_SHORT);
        } else if (mManager.getWifiState() == 2) {
            Toast.makeText(mContext, "WiFi正在关闭", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(mContext, "请从新关闭WiFi", Toast.LENGTH_SHORT);
        }
    }

    //检查当前WiFi状态
    public void checkSWifitate() {
        if (mManager.getWifiState() == 0) {
            Toast.makeText(mContext, "Wifi正在关闭", Toast.LENGTH_SHORT).show();
        } else if (mManager.getWifiState() == 1) {
            Toast.makeText(mContext, "Wifi已经关闭", Toast.LENGTH_SHORT).show();
        } else if (mManager.getWifiState() == 2) {
            Toast.makeText(mContext, "Wifi正在开启", Toast.LENGTH_SHORT).show();
        } else if (mManager.getWifiState() == 3) {
            Toast.makeText(mContext, "Wifi已经开启", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "没有获取到WiFi状态", Toast.LENGTH_SHORT).show();
        }
    }

    //得到一个锁定WiFi对象
    public WifiManager.WifiLock getWifiLock() {
        WifiManager.WifiLock lock = mManager.createWifiLock("a");
        return lock;
    }

    //锁定WiFi链接
    public void acquireWifi() {
        getWifiLock().acquire();
    }

    //接触锁定WiFi
    public void releaseWifi() {
        if (getWifiLock().isHeld()) {
            getWifiLock().acquire();
        }
    }

    //获取配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return wifiConfiguration;
    }

    //指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > getConfiguration().size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mManager.enableNetwork(getConfiguration().get(index).networkId, true);
    }

    //扫描WiFi
    public void startScale() {
        openWifi();
        mManager.startScan();
        //得到扫描结果
        List<ScanResult> results = mManager.getScanResults();
        // 得到配置好的网络连接
        wifiConfiguration = mManager.getConfiguredNetworks();
        if (results == null) {
            if(mManager.getWifiState()==3){
                Toast.makeText(mContext,"当前区域没有无线网络",Toast.LENGTH_SHORT).show();
            }else if(mManager.getWifiState()==2){
                Toast.makeText(mContext,"wifi正在开启，请稍后扫描", Toast.LENGTH_SHORT).show();
            }else{Toast.makeText(mContext,"WiFi没有开启", Toast.LENGTH_SHORT).show();
            }
        } else {
            mWifiList = new ArrayList();
            for(ScanResult result : results){
                if (result.SSID == null || result.SSID.length() == 0 || result.capabilities.contains("[IBSS]")) {
                    continue;
                }
                boolean found = false;
                for(ScanResult item:mWifiList){
                    if(item.SSID.equals(result.SSID)&&item.capabilities.equals(result.capabilities)){
                        found = true;break;
                    }
                }
                if(!found){
                    mWifiList.add(result);
                }
            }
        }
    }

    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }


    // 得到MAC地址
    public String getMacAddress() {
        return (mInfo == null) ? "NULL" : mInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mInfo == null) ? "NULL" : mInfo.getBSSID();
    }

    // 得到IP地址
    public int getIPAddress() {
        return (mInfo == null) ? 0 : mInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        return (mInfo == null) ? 0 : mInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mInfo == null) ? "NULL" : mInfo.toString();
    }
    //获取WiFi名字
    public String getWifiName() {
        return (mInfo == null) ? "NULL" : mInfo.getSSID();
    }

    // 添加一个网络并连接
    public Boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = mManager.addNetwork(wcg);
        boolean b = mManager.enableNetwork(wcgID, true);
        return b;
    }
    //保存WiFi配置
    public void scalWifiConfig(){
        mManager.saveConfiguration();
    }

    //然后是一个实际应用方法，只验证过没有密码的情况：

    public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            mManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

}
