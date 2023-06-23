package com.example.wechatproject.util;

/**
 * Created by: 神楽坂千紗
 * 用以存储当前用户信息，作为全局变量使用。
 */
public class CurrentUserInfo {
    public String userID;
    public String username;
    public void setUserID(String userID){ this.userID = userID; }
    public void setUsername(String username){ this.username = username; }
    public String getUserID(){ return userID; }
    public String getUsername(){ return username; }
}
