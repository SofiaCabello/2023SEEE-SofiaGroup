package com.example.wechatproject.message;

public class MessageItem {
    private String avatarFilePath;
    private String name;
    private String latestMessage;
    private String time;
    private String type;

    public MessageItem(String avatarFilePath, String name, String latestMessage, String time, String type) {
        this.avatarFilePath = avatarFilePath;
        this.name = name;
        this.latestMessage = latestMessage;
        this.time = time;
        this.type = type;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public String getName() {
        return name;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
