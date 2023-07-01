package com.example.wechatproject.contact;

public class ContactItem {
    private String userName;
    private String signature;
    private String avatarFilePath;

    public ContactItem(String userName, String signature, String avatarFilePath) {
        this.userName = userName;
        this.signature = signature;
        this.avatarFilePath = avatarFilePath;
    }

    public String getUserName() {
        return userName;
    }

    public String getSignature() {
        return signature;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }
}
