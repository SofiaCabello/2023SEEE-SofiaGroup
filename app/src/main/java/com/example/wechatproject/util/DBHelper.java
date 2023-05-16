package com.example.wechatproject.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * 数据库帮助类，利用Sqlite数据库
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "wechat.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        // 创建数据库表的语句，这里还没有设计好数据库到底有哪些表。
        String createTable = "CREATE TABLE "+ "wechat.db" + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "content TEXT,"
                + "time TEXT,"
                + "type INTEGER,"
                + "avatar INTEGER,"
                + "unread INTEGER,"
                + "top INTEGER,"
                + "disturb INTEGER"
                + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String upgradeTable = "DROP TABLE IF EXISTS " + "wechat.db";
        db.execSQL(upgradeTable);
        onCreate(db);
    }
}
