package com.cheanda.a8805.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2017/9/22.
 */

public class UserTabDataOpenHelper extends SQLiteOpenHelper {
    public UserTabDataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //用户信息的数据库
        String sql2 = "create table companyList(companyNamwe varchar,homeTopBg Integer,depthTopBg Integer,addTopBg Integer," +
                "reduceTopBg Integer,loopTopBg Integer,changeTopBg Integer,emptyNewTopBg Integer," +
                "emptyOldTopBg Integer,setting Integer,systemSetting Integer,about Integer,alineNewTopBg Integer," +
                "alineOldTopBg Integer,wifiTopBg Integer,selectIntent Integer,carType Integer,qureyResult Integer,startBgOne Integer," +
                "startBgTwo Integer,startBgThree Integer,vinQuery Integer)";
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
