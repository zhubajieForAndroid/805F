package com.cheanda.a8805.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/9/22.
 */

public class UserDataDao {
    private static final String TABLE_NAME = "companyList";
    UserTabDataOpenHelper mHelper;
    public UserDataDao(Context context) {
        mHelper = new UserTabDataOpenHelper(context, "companyList", null, 1);
    }

    /**
     * 插入方法
     *
     * @return
     */
    public long insert(String companyNamwe,Integer homeTopBg, Integer depthTopBg, Integer addTopBg,Integer reduceTopBg, Integer loopTopBg,
                       Integer changeTopBg, Integer emptyNewTopBg,Integer emptyOldTopBg,Integer setting,
                       Integer systemSetting, Integer about, Integer alineNewTopBg,Integer alineOldTopBg, Integer wifiTopBg, Integer selectIntent,Integer carType,
                       Integer qureyResult,Integer startBgOne,Integer startBgTwo,Integer startBgThree,Integer vinQuery) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("companyNamwe", companyNamwe);
        values.put("homeTopBg", homeTopBg);
        values.put("depthTopBg", depthTopBg);
        values.put("addTopBg", addTopBg);
        values.put("reduceTopBg", reduceTopBg);
        values.put("loopTopBg", loopTopBg);
        values.put("changeTopBg", changeTopBg);
        values.put("emptyNewTopBg", emptyNewTopBg);
        values.put("emptyOldTopBg", emptyOldTopBg);
        values.put("setting", setting);
        values.put("systemSetting", systemSetting);
        values.put("about", about);
        values.put("alineNewTopBg", alineNewTopBg);
        values.put("alineOldTopBg", alineOldTopBg);
        values.put("wifiTopBg", wifiTopBg);
        values.put("selectIntent", selectIntent);
        values.put("carType", carType);
        values.put("qureyResult", qureyResult);
        values.put("startBgOne", startBgOne);
        values.put("startBgTwo", startBgTwo);
        values.put("startBgThree", startBgThree);
        values.put("vinQuery", vinQuery);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
    public List<QueryDBBean> query(String companyNamwe) {
        List<QueryDBBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //参数1,查询的表名字
        //参数2,返回那一列
        //参数3,返回那一行
        //参数4,对参数3?进行复制
        Cursor cursor = db.query(TABLE_NAME, null, "companyNamwe=?", new String[] {companyNamwe}, null, null, null);
        while (cursor.moveToNext()) {
            String companyNamweo = cursor.getString(cursor.getColumnIndex("companyNamwe"));
            int homeTopBg = cursor.getInt(cursor.getColumnIndex("homeTopBg"));
            int depthTopBg = cursor.getInt(cursor.getColumnIndex("depthTopBg"));
            int addTopBg = cursor.getInt(cursor.getColumnIndex("addTopBg"));
            int reduceTopBg = cursor.getInt(cursor.getColumnIndex("reduceTopBg"));
            int loopTopBg = cursor.getInt(cursor.getColumnIndex("loopTopBg"));
            int changeTopBg = cursor.getInt(cursor.getColumnIndex("changeTopBg"));
            int emptyNewTopBg = cursor.getInt(cursor.getColumnIndex("emptyNewTopBg"));
            int emptyOldTopBg = cursor.getInt(cursor.getColumnIndex("emptyOldTopBg"));
            int setting = cursor.getInt(cursor.getColumnIndex("setting"));
            int systemSetting = cursor.getInt(cursor.getColumnIndex("systemSetting"));
            int about = cursor.getInt(cursor.getColumnIndex("about"));
            int alineNewTopBg = cursor.getInt(cursor.getColumnIndex("alineNewTopBg"));
            int alineOldTopBg = cursor.getInt(cursor.getColumnIndex("alineOldTopBg"));
            int wifiTopBg = cursor.getInt(cursor.getColumnIndex("wifiTopBg"));
            int selectIntent = cursor.getInt(cursor.getColumnIndex("selectIntent"));
            int carType = cursor.getInt(cursor.getColumnIndex("carType"));
            int qureyResult = cursor.getInt(cursor.getColumnIndex("qureyResult"));
            int startBgOne = cursor.getInt(cursor.getColumnIndex("startBgOne"));
            int startBgTwo = cursor.getInt(cursor.getColumnIndex("startBgTwo"));
            int startBgThree = cursor.getInt(cursor.getColumnIndex("startBgThree"));
            int vinQuery = cursor.getInt(cursor.getColumnIndex("vinQuery"));

            QueryDBBean bean = new QueryDBBean(companyNamweo,homeTopBg, depthTopBg, addTopBg, reduceTopBg, loopTopBg, changeTopBg,
                    emptyNewTopBg, emptyOldTopBg, setting, systemSetting, about, alineNewTopBg, alineOldTopBg, wifiTopBg, selectIntent,carType,qureyResult,
                    startBgOne,startBgTwo,startBgThree,vinQuery);
            list.add(bean);
        }
        cursor.close();
        db.close();
        return list;
    }
    public int queryName(String companyNamwe){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //参数1,查询的表名字
        //参数2,返回那一列
        //参数3,返回那一行
        //参数4,对参数3?进行复制
        Cursor cursor = db.query(TABLE_NAME, null, "companyNamwe=?", new String[] {companyNamwe}, null, null, null);
        int i = 0;
        while (cursor.moveToNext()){
            i++;
        }
        return i;
    }


}
