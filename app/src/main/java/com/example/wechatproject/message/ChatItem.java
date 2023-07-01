package com.example.wechatproject.message;

public class ChatItem {
    private String message;
    private boolean isMeSend;
    private String avatarFilePath;
    private String type;
    private String time;

    public ChatItem(String message, boolean isMeSend, String avatarFilePath, String type,String time) {
        this.message = message;
        this.isMeSend = isMeSend;
        this.avatarFilePath = avatarFilePath;
        this.type = type;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMeSend() {
        return isMeSend;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

}
