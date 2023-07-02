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
    private static String username;
    private static String avatarFilePath;
    private static String signature;
    private static String IPAddress;

    private CurrentUserInfo() {}

    public static synchronized CurrentUserInfo getInstance() {
        if (instance == null) {
            instance = new CurrentUserInfo();
        }
        return instance;
    }

    public static String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        CurrentUserInfo.signature = signature;
    }

    public static String getAvatarFilePath() {
        return avatarFilePath;
    }

    public void setAvatarFilePath(String avatarFilePath) {
        CurrentUserInfo.avatarFilePath = avatarFilePath;
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username){
        CurrentUserInfo.username = username;
    }

    public static String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        CurrentUserInfo.IPAddress = IPAddress;
    }
}
