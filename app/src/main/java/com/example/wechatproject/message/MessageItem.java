package com.example.wechatproject.message;

import com.example.wechatproject.R;

public class MessageItem {
    private String name;
    private String message;

    public MessageItem(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }


    //这个方法是一定要改的，但是需要数据库支持
    public int getAvatarResId() {
        return R.mipmap.ic_launcher;
    }
}
