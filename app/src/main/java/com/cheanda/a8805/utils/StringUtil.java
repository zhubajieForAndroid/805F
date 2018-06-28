package com.cheanda.a8805.utils;



import android.util.Log;

import com.cheanda.a8805.bean.BandBean;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class StringUtil {

    // 简体中文的编码范围从B0A1（45217）一直到F7FE（63486）
    private static int BEGIN = 45217;  
    private static int END = 63486;  
  
    // 按照声 母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。  
    // i, u, v都不做声母, 自定规则跟随前面的字母  
    private static char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', };  
  
    // 二十六个字母区间对应二十七个端点  
    // GB2312码汉字区间十进制表示  
    private static int[] table = new int[27];  
  
    // 对应首字母区间表  
    private static char[] initialtable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
  
    // 初始化  
    static {  
        for (int i = 0; i < 26; i++) {  
            table[i] = gbValue(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。  
        }  
        table[26] = END;// 区间表结尾  
    }  
  
    // ------------------------public方法区------------------------  
    // 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串 最重要的一个方法，思路如下：一个个字符读入、判断、输出  
  
    public static String cn2py(String SourceStr) {
        String Result = "";
        int StrLength = SourceStr.length();  
        int i;  
        try {  
            for (i = 0; i < StrLength; i++) {
                Result += Char2Initial(SourceStr.charAt(i));
            }  
        } catch (Exception e) {  
            Result = "";  
            e.printStackTrace();  
        }  
        return Result;  
    }  
  
    // ------------------------private方法区------------------------  
    /** 
     * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0' 　　* 　　 
     */  
    private static char Char2Initial(char ch) {  
        // 对英文字母的处理：小写字母转换为大写，大写的直接返回  
        if (ch >= 'a' && ch <= 'z') {  
            return (char) (ch - 'a' + 'A');  
        }  
        if (ch >= 'A' && ch <= 'Z') {  
            return ch;  
        }  
        // 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，  
        // 若不是，则直接返回。  
        // 若是，则在码表内的进行判断。  
        int gb = gbValue(ch);// 汉字转换首字母  
        if ((gb < BEGIN) || (gb > END))// 在码表区间之前，直接返回  
        {  
            return ch;  
        }  
        int i;  
        for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”  
            if ((gb >= table[i]) && (gb < table[i + 1])) {  
                break;  
            }  
        }  
        if (gb == END) {// 补上GB2312区间最右端  
            i = 25;  
        }  
        return initialtable[i]; // 在码表区间中，返回首字母  
    }  
  
    /** 
     * 取出汉字的编码 cn 汉字 　　 
     */  
    private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。  
        String str = new String();  
        str += ch;  
        try {  
            byte[] bytes = str.getBytes("GB2312");  
            if (bytes.length < 2) {  
                return 0;  
            }  
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);  
        } catch (Exception e) {  
            return 0;  
        }  
    }

    /**
     * 传入一个集合,返回按字母排序后的新集合
     * @param list
     * @return
     */
    public static List<String> getSortList(List<BandBean.ResponseBean> list){
        int countA = 2;
        int countB = 0;
        int countC = 0;
        int countD = 0;
         int countE = 0;
        int countF = 0;
         int countG=0;
         int countH=0;
         int countI=0;
        int countJ=0;
         int countK=0;
         int countL=0;
         int countM=0;
         int countN=0;
        int countO=0;
        int countP=0;
        int countQ=0;
        int countR=0;
        int countS=0;
        int countT=0;
       int countU=0;
        int countV=0;
         int countW=0;
         int countX=0;
         int countY=0;
        int countZ=0;
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            BandBean.ResponseBean responseBean = list.get(i);
            String s = responseBean.getBrand();
            String substring = s.substring(0, 1);

            //获取中文首字母
            String result = cn2py(substring);
            //大写转小写
            String s1 = result.toLowerCase();

            mList.add(s1);
        }
        //排序
        Collections.sort(mList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //去除重复
        Set set = new HashSet();
        List<String> newlist = new ArrayList<>();
        for (String cd:mList) {
            if(set.add(cd)){
                newlist.add(cd);
            }
        }

        //中文排序
        List<String> resultList = new ArrayList<>();

        for (int i = 0; i < newlist.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                BandBean.ResponseBean responseBean = list.get(j);
                String s = responseBean.getBrand();
                String substring = s.substring(0, 1);
                if (substring.equals("讴")){
                    substring = "欧";
                }
                String result = cn2py(substring);
                String s1 = result.toLowerCase();
                if (newlist.get(i).equals(s1)){
                    if (newlist.get(i).equals("a"))
                        countA++;
                    if (newlist.get(i).equals("b"))
                        countB++;
                    if (newlist.get(i).equals("c"))
                        countC++;
                    if (newlist.get(i).equals("d"))
                        countD++;
                    if (newlist.get(i).equals("e"))
                        countE++;
                    if (newlist.get(i).equals("f"))
                        countF++;
                    if (newlist.get(i).equals("g"))
                        countG++;
                    if (newlist.get(i).equals("h"))
                        countH++;
                    if (newlist.get(i).equals("i"))
                        countI++;
                    if (newlist.get(i).equals("j"))
                        countJ++;
                    if (newlist.get(i).equals("k"))
                        countK++;
                    if (newlist.get(i).equals("l"))
                        countL++;
                    if (newlist.get(i).equals("m"))
                        countM++;
                    if (newlist.get(i).equals("n"))
                        countN++;
                    if (newlist.get(i).equals("o"))
                        countO++;
                    if (newlist.get(i).equals("p"))
                        countP++;
                    if (newlist.get(i).equals("q"))
                        countQ++;
                    if (newlist.get(i).equals("r"))
                        countR++;
                    if (newlist.get(i).equals("s"))
                        countS++;
                    if (newlist.get(i).equals("t"))
                        countT++;
                    if (newlist.get(i).equals("u"))
                        countU++;
                    if (newlist.get(i).equals("v"))
                        countV++;
                    if (newlist.get(i).equals("w"))
                        countW++;
                    if (newlist.get(i).equals("x"))
                        countX++;
                    if (newlist.get(i).equals("y"))
                        countY++;
                    if (newlist.get(i).equals("z"))
                        countZ++;
                    resultList.add(list.get(j).getBrand());
                }
            }
        }

        return resultList;
    }
    //拆分字符串的每个字符,返回拆分倒序后得到的集合
    public static List<String> subStringToList(String data){
        List<String> list = new ArrayList<>();
        char[] chars = data.toCharArray();
        for (int i = 0; i <chars.length; i++) {
            list.add(String.valueOf(chars[i]));
        }
        return list;
    }
    /**
     * 为SideBar填充数据
     *
     * @param date
     * @return
     */
    public static List<SortModel> filledData(List<String> date) {
        List<SortModel> mSortList = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i));
            //汉字转换成拼音
            String pinyin =  CharacterParser.getInstance().getSelling(date.get(i));
            if (pinyin.equals("zuoge")) {
                pinyin = "ouge";
            }
            String sortString = "";
            if (pinyin.length() > 0) {
                sortString = pinyin.substring(0, 1).toUpperCase();
            }

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;

    }
    //获取网络时间获取时间,
    public static void testDate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;//取得资源对象
                try {
                    url=new URL("http://www.baidu.com/");
                    URLConnection uc=url.openConnection();
                    uc.connect();
                    long id=uc.getDate();
                    Date date=new Date(id);
                    SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd.HHmmss");
                    format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                    String nowTime=format.format(date);

                    //修改系统时间
                    Process process = Runtime.getRuntime().exec("su");
                    String datetime = nowTime; //测试的设置的时间【时间格式 yyyyMMdd.HHmmss】
                    DataOutputStream os = new DataOutputStream(process.getOutputStream());
                    os.writeBytes("setprop persist.sys.timezone GMT\n");
                    os.writeBytes("/system/bin/date -s " + datetime + "\n");
                    os.writeBytes("clock -w\n");
                    os.writeBytes("exit\n");
                    os.flush();
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}  