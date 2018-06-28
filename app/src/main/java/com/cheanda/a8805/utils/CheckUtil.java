package com.cheanda.a8805.utils;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dell on 2017/5/18.
 */

public class CheckUtil {
    private static final String TAG = "CheckUtil";

    //发送数据
    public static void sendMessage(int[] arr, OutputStream os) {
        try {
            for (int i = 0; i < arr.length; i++) {
                os.write(arr[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //解析返回数据长度是14
    public static Boolean analysisStartResult(byte[] startArray) {
        startArray[7] = 0;
        for (int i = 0; i < startArray.length - 3; i++) {
            startArray[7] ^= startArray[i];
        }

        //过滤错误
        if (startArray[5] == -37) {
            return false;
        }
        if (startArray[7] == startArray[5]) {
            startArray[7] = 0;
            return true;
        } else {
            startArray[7] = 0;
            return false;
        }
    }
    //获取总的换油数
    public static int getChageOilNumber(byte[] bytes){
        String s = castBytesToHexString(bytes);
        Log.d(TAG, "getChageOilNumber: "+s);
        String newOil = s.substring(13,17);
        int integer = Integer.valueOf(newOil, 16);
        return integer;
    }


    //获取新油重量
    public static String getNewOilWeight(byte[] bytes) {
        //42 16 9 1 1 -86 0 -76 -116 0 50 1 0 0 -110 35
        //2a 10 09 01 01 aa 00 b4 8c 00 32 01 00 00 92 23
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23

        String s = castBytesToHexString(bytes);
        String newOil = s.substring(8,12);
        int integer = Integer.valueOf(newOil, 16);
        return "000" + String.valueOf(integer);
    }

    private  static String castBytesToHexString(byte[] byeData) {
        String strRet = null;
        int intLen = byeData.length;
        for (int i = 0; i < intLen; i++) {
            byte byeTemp = byeData[i];
            String strHexTemp = Integer.toHexString(byeTemp);
            if (strHexTemp.length() > 2) {
                strHexTemp = strHexTemp.substring(strHexTemp.length() - 2);
            } else if (strHexTemp.length() < 2) {
                strHexTemp = "0" + strHexTemp;
            }
            if (i == 0) {
                strRet = strHexTemp;
            } else {
                strRet = strRet + strHexTemp;
            }
        }
        strRet = strRet.toUpperCase();
        return strRet;
    }

    //获取旧油重量
    public static String getOldOilWeight(byte[] bytes) {
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23
        String s = castBytesToHexString(bytes);
        String newOil = s.substring(12,16);
        int integer = Integer.valueOf(newOil, 16);
        return "000" + String.valueOf(integer);
    }
    //获取新油重量返回int
    public static int getNewOilWeightInt(byte[] bytes) {
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23
        String s = castBytesToHexString(bytes);
        String newOil = s.substring(8,12);
        int integer = Integer.valueOf(newOil, 16);
        return integer;
    }

    //获取旧油重量
    public static int getOldOilWeightInt(byte[] bytes) {
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23
        String s = castBytesToHexString(bytes);
        String newOil = s.substring(12,16);
        int integer = Integer.valueOf(newOil, 16);
        return integer;
    }
    //获取工作电压
    public static String getWorkV(byte[] bytes) {
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23
        String s = castBytesToHexString(bytes);
        String newOil = s.substring(16,18);
        int integer = Integer.valueOf(newOil, 16);
        return "000" + String.valueOf(integer);
    }

    //获取工作电流
    public static String getWorkA(byte[] bytes) {
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23
        String s = castBytesToHexString(bytes);
        String newOil = s.substring(18,22);
        int integer = Integer.valueOf(newOil, 16);
        return "000" + String.valueOf(integer);
    }
    //获取设备激活状态
    public static String getEquipmentState(byte[] bytes){
        String result = Integer.toBinaryString((bytes[11] & 0xFF) + 0x100).substring(1);
        return result;
    }
    //获取车辆状态
    public static String getCarState(byte[] bytes) {
        String data = String.valueOf(bytes[11]);
        int i = Integer.parseInt(data);
        String result;
        int a;
        a = i;
        i &= 0x00000001;
        if (i > 0) {
            result = "1";
        } else {
            result = "0";
        }
        i = a;
        i &= 0x00000002;
        if (i > 0) {
            result += "1";
        } else {
            result += "0";
        }
        i = a;
        i &= 0x00000004;
        if (i > 0) {
            result += "1";
        } else {
            result += "0";
        }
        i = a;
        i &= 0x00000008;
        if (i > 0) {
            result += "1";
        } else {
            result += "0";
        }

        //String resultState = Integer.toBinaryString(Integer.parseInt(carState));
        return result;
    }

    //获取油温
    public static String getOilTemperature(byte[] bytes) {
        //2A 10 09 01 01 AA 00 B5 8C 00 32 01 00 00 93 23
        String s = castBytesToHexString(bytes);
        String newOil = s.substring(24,26);
        int integer = Integer.valueOf(newOil, 16);
        if (integer<0){
            return "-" + "00" + String.valueOf(integer);
        }
        return "00" + String.valueOf(integer);
    }

    //工作进度
    public static String getWorkProgress(byte[] bytes) {
        String data = String.valueOf(bytes[13]);
        Integer workProgress = Integer.valueOf(data, 10);
        return "" + workProgress;
    }
    //获取换油升数,和次数
    public static String getChangeOilNumber(byte[] array) {
        //2A 08 09 03   00 0B   23 23
        //2A 08 09 03   01 1F 36  23
        String s = castBytesToHexString(array);
        String result = s.substring(8,12);
        Log.d(TAG, "getChangeOilNumber: 换油"+result);
        int integer = Integer.valueOf(result, 16);
        return "000"+integer;
    }
    //获取新油的值
    public static String getNewOilKG(byte[] arr) {
        //2A 0A 09 02 0C AA 09 01 85 23
        String s = castBytesToHexString(arr);
        String result = s.substring(8,12);
        int integer = Integer.valueOf(result, 16);
        return "0000"+integer;
    }
    //获取旧油的值
    public static String getOldOilKG(byte[] arr) {
        //2A 0A 09 02 0C AA 09 01 85 23
        String s = castBytesToHexString(arr);
        String result = s.substring(12,16);
        int integer = Integer.valueOf(result, 16);
        return "0000"+integer;
    }

    public static String getLongitude(byte[] zuobiaoArray) {
        StringBuffer sb = new StringBuffer();
        for (int i = 4; i < zuobiaoArray.length-3; i++) {
            String s = Integer.toHexString(zuobiaoArray[i]-0x30);
            if (s.equals("fffffffe")){
                sb.append(".");
            }else if (s.equals("fffffffc")){
                sb.append(",");
            } else {
                sb.append(s);
            }
        }

        return sb.toString();
    }
    //字符串转换为ascii
    public static List<String> stringToA(String content){
        String result = "";
        int max = content.length();
        for (int i=0; i<max; i++){
            char c = content.charAt(i);
            int b = (int)c;
            result = result + b;
        }
        List<String> stringList = stringToList(result);
        return stringList;
    }
    //2个分组
    public static List<String> stringToList(String data){
        List<String> resultList = new ArrayList<>();
        int length = data.length();
        for (int i = 0; i < length; i+=2) {
            resultList.add(data.charAt(i)+""+data.charAt(i+1));
        }
        return resultList;
    }
}
