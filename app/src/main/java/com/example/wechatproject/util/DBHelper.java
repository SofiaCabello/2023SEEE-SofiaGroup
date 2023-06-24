package com.example.wechatproject.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 数据库帮助类，利用Sqlite数据库
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "wechat.db", null, 1);
    }

    private static final String CREATE_CONTACTS_TABLE
            = "CREATE TABLE IF NOT EXISTS contacts (username TEXT PRIMARY KEY, userID TEXT, usergender INTEGER, photoId INTEGER, signature TEXT)";
    private static final String CREATE_CHAT_TABLE
            = "CREATE TABLE IF NOT EXISTS message (username TEXT PRIMARY KEY, content TEXT, time TEXT, type INTEGER)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + "wechat.db";
        db.execSQL(upgradeTable);
        onCreate(db);
    }

    // 添加联系人
    public void addContact(String username, String userID, int usergender, int photoId, String signature) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO contacts (username, userID, usergender, photoId, signature) VALUES(?, ?, ?, ?, ?)", new Object[]{username, userID, usergender, photoId, signature});
    }

    // 添加聊天记录
    public void addMessage(String username, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO message (username, content, time) VALUES(?, ?, ?)", new Object[]{username, content, time});
    }

    // 查询所有联系人
    public List<String> getContacts() {
        List<String> contactResult = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM contacts", null);
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndex("username");
            if (index != -1) {
                do {
                    contactResult.add(cursor.getString(index));
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null)
            cursor.close();

        return contactResult;
    }


    // 查询特定联系人的聊天记录，其中需要根据type的值来判断是文本还是文件
    public List<MessageEntry> getMessages(String username) {
        List<MessageEntry> messageResult = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT time, type, content FROM message WHERE username = ?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            int timeIndex = cursor.getColumnIndex("time");
            int typeIndex = cursor.getColumnIndex("type");
            int contentIndex = cursor.getColumnIndex("content");
            if (timeIndex != -1 && typeIndex != -1 && contentIndex != -1) {
                do {
                    // 从游标中读取每个字段的值
                    String time = cursor.getString(timeIndex);
                    String type = cursor.getString(typeIndex);
                    String content = cursor.getString(contentIndex);

                    // 创建消息条目并添加到结果列表中
                    MessageEntry entry = new MessageEntry(time, type, content);
                    messageResult.add(entry);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null)
            cursor.close();

        return messageResult;
    }

    public class MessageEntry {
        private String time;
        private String type;
        private String content;

        public MessageEntry(String time, String type, String content) {
            this.time = time;
            this.type = type;
            this.content = content;
        }

        // 添加 getter 和 setter 方法
        // ...

        // 添加 toString 方法以便于打印对象信息
        @Override
        public String toString() {
            return "MessageEntry{" +
                    "time='" + time + '\'' +
                    ", type='" + type + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

}
