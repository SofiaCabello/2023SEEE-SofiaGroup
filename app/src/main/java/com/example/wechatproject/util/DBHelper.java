package com.example.wechatproject.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wechatproject.contact.ContactItem;
import com.example.wechatproject.message.MessageItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 数据库帮助类，利用Sqlite数据库
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "wechat.db", null, 3);
    }

    private static final String CREATE_CONTACTS_TABLE
            = "CREATE TABLE IF NOT EXISTS contacts (currentUsername TEXT, username TEXT , photoId TEXT, signature TEXT)";
    private static final String CREATE_CHAT_TABLE
            = "CREATE TABLE IF NOT EXISTS message (currentUsername TEXT, username TEXT , content TEXT, time TEXT, type TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE contacts");
        db.execSQL("DROP TABLE message");
        onCreate(db);
    }


    // 添加联系人
    public void addContact(String username, String photoId, String signature) {
        SQLiteDatabase db = this.getWritableDatabase();
        String currentUsername = CurrentUserInfo.getUsername();
        db.execSQL("INSERT INTO contacts (currentUsername, username, photoId, signature) VALUES(?, ?, ?, ?)", new Object[]{currentUsername, username, photoId, signature});
    }

    // 添加聊天记录
    public void addMessage(String username, String content, String time, int type) {
        SQLiteDatabase db = this.getWritableDatabase();
        String currentUsername = CurrentUserInfo.getUsername();
        db.execSQL("INSERT INTO message (currentUsername, username, content, time, type) VALUES(?, ?, ?, ?, ?)", new Object[]{currentUsername, username, content, time, type});
    }

    // 查询所有联系人
    public List<ContactItem> getContacts(){
        System.out.println("----getContacts----");
        List<ContactItem> contactResult = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String currentUsername = CurrentUserInfo.getUsername();
        Cursor cursor = db.rawQuery("SELECT username, photoId, signature FROM contacts WHERE currentUsername = ?", new String[]{currentUsername});
        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("username");
            int photoIdIndex = cursor.getColumnIndex("photoId");
            int signatureIndex = cursor.getColumnIndex("signature");
            if (usernameIndex != -1 && photoIdIndex != -1 && signatureIndex != -1) {
                do {
                    // 从游标中读取每个字段的值
                    String username = cursor.getString(usernameIndex);
                    String photoId = cursor.getString(photoIdIndex);
                    String signature = cursor.getString(signatureIndex);

                    //System.out.println("username: " + username + " photoId: " + photoId + " signature: " + signature);
                    // 创建联系人条目并添加到结果列表中
                    ContactItem entry = new ContactItem(username, signature, photoId);
                    contactResult.add(entry);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null)
            cursor.close();
        System.out.println(contactResult.get(0).getUserName());

        return contactResult;
    }



    // 查询每个联系人的最近一条聊天记录，可以根据time排序
    public List<MessageItem> getLatestMessage(){
        System.out.println("----getLatestMessage----");
        List<MessageItem> messageResult = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String currentUsername = CurrentUserInfo.getUsername();
        Cursor cursor = db.rawQuery("SELECT username, content, time, type FROM message WHERE currentUsername = ? GROUP BY username", new String[]{currentUsername});
        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("username");
            int contentIndex = cursor.getColumnIndex("content");
            int timeIndex = cursor.getColumnIndex("time");
            int typeIndex = cursor.getColumnIndex("type");
            if (usernameIndex != -1 && contentIndex != -1 && timeIndex != -1 && typeIndex != -1) {
                do {
                    // 从游标中读取每个字段的值
                    String username = cursor.getString(usernameIndex);
                    String content = cursor.getString(contentIndex);
                    String time = cursor.getString(timeIndex);
                    String type = cursor.getString(typeIndex);

                    Cursor cursor1 = db.rawQuery("SELECT photoId FROM contacts WHERE currentUsername = ? AND username = ?", new String[]{currentUsername, username});
                    String filePath = "";
                    if (cursor1 != null && cursor1.moveToFirst()) {
                        int photoIdIndex = cursor1.getColumnIndex("photoId");
                        if (photoIdIndex != -1) {
                            filePath = cursor1.getString(photoIdIndex);
                        }
                    }
                    //System.out.println("username: " + username + " content: " + content + " time: " + time + " type: " + type);
                    // 创建联系人条目并添加到结果列表中
                    MessageItem entry = new MessageItem(filePath, username, content, time, type);
                    messageResult.add(entry);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null)
            cursor.close();

        return messageResult;
    }
//        System.out.println("----getLatestMessage----");
//        List<MessageItem> messageResult = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String currentUsername = CurrentUserInfo.getUsername();
//        Cursor cursor = db.rawQuery("SELECT username, content, time, type FROM message WHERE currentUsername = ? GROUP BY username", new String[]{currentUsername});
//        if (cursor != null && cursor.moveToFirst()) {
//            int usernameIndex = cursor.getColumnIndex("username");
//            int contentIndex = cursor.getColumnIndex("content");
//            int timeIndex = cursor.getColumnIndex("time");
//            int typeIndex = cursor.getColumnIndex("type");
//            if (usernameIndex != -1 && contentIndex != -1 && timeIndex != -1 && typeIndex != -1) {
//                do {
//                    // 从游标中读取每个字段的值
//                    String username = cursor.getString(usernameIndex);
//                    String content = cursor.getString(contentIndex);
//                    String time = cursor.getString(timeIndex);
//                    String type = cursor.getString(typeIndex);
//
//                    Cursor cursor1 = db.rawQuery("SELECT photoId FROM contacts WHERE currentUsername = ? AND username = ?", new String[]{currentUsername, username});
//                    String filePath = "";
//                    if (cursor1 != null && cursor1.moveToFirst()) {
//                        int photoIdIndex = cursor1.getColumnIndex("photoId");
//                        if (photoIdIndex != -1) {
//                            filePath = cursor1.getString(photoIdIndex);
//                        }
//                    }
//                    //System.out.println("username: " + username + " content: " + content + " time: " + time + " type: " + type);
//                    // 创建联系人条目并添加到结果列表中
//                    MessageItem entry = new MessageItem(filePath, username, content, time, type);
//                    messageResult.add(entry);
//                } while (cursor.moveToNext());
//            }
//        }
//
//        if (cursor != null)
//            cursor.close();
//        System.out.println(messageResult.get(0).getName());
//
//        return messageResult;
//    }




    // 测试方法，用于展示数据库中的内容
    public void showAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contacts", null);
        if (cursor != null && cursor.moveToFirst()) {
            int currentUsernameIndex = cursor.getColumnIndex("currentUsername");
            int usernameIndex = cursor.getColumnIndex("username");
            int photoIdIndex = cursor.getColumnIndex("photoId");
            int signatureIndex = cursor.getColumnIndex("signature");
            if (currentUsernameIndex != -1 && usernameIndex != -1 && photoIdIndex != -1 && signatureIndex != -1) {
                do {
                    // 从游标中读取每个字段的值
                    String currentUsername = cursor.getString(currentUsernameIndex);
                    String username = cursor.getString(usernameIndex);
                    String photoId = cursor.getString(photoIdIndex);
                    String signature = cursor.getString(signatureIndex);

                    // 创建联系人条目并添加到结果列表中
                    System.out.println("currentUsername: " + currentUsername + " username: " + username + " photoId: " + photoId + " signature: " + signature);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null)
            cursor.close();

        cursor = db.rawQuery("SELECT * FROM message", null);
        if (cursor != null && cursor.moveToFirst()) {
            int currentUsernameIndex = cursor.getColumnIndex("currentUsername");
            int usernameIndex = cursor.getColumnIndex("username");
            int contentIndex = cursor.getColumnIndex("content");
            int timeIndex = cursor.getColumnIndex("time");
            int typeIndex = cursor.getColumnIndex("type");
            if (currentUsernameIndex != -1 && usernameIndex != -1 && contentIndex != -1 && timeIndex != -1 && typeIndex != -1) {
                do {
                    // 从游标中读取每个字段的值
                    String currentUsername = cursor.getString(currentUsernameIndex);
                    String username = cursor.getString(usernameIndex);
                    String content = cursor.getString(contentIndex);
                    String time = cursor.getString(timeIndex);
                    String type = cursor.getString(typeIndex);

                    // 创建联系人条目并添加到结果列表中
                    System.out.println("currentUsername: " + currentUsername + " username: " + username + " content: " + content + " time: " + time + " type: " + type);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null)
            cursor.close();
    }


}
