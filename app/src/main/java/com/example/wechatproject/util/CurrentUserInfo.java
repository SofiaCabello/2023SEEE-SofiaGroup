package com.example.wechatproject.util;

/**
 * Created by: 神楽坂千紗
 * 用以存储当前用户信息，作为全局变量使用。
 * 这是一个单例类，这样可以保证全局只有一个实例，数据的修改是互相影响的。
 * 用法：CurrentUserInfo.getInstance().setUsername("username");
 *      CurrentUserInfo.getInstance().getUsername();
 */
public class CurrentUserInfo {
    private static CurrentUserInfo instance;
    private String username;

    private CurrentUserInfo() {}

    public static synchronized CurrentUserInfo getInstance() {
        if (instance == null) {
            instance = new CurrentUserInfo();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
