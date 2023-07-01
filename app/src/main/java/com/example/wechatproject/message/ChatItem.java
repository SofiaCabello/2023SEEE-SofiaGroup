package com.example.wechatproject.message;

import com.example.wechatproject.R;

public class ChatItem {
    private String name;
    private String message;
    private boolean isMeSend;
    private String avatarFilePath;

    public ChatItem(String name, String message, boolean isMeSend, String avatarFilePath) {
        this.name = name;
        this.message = message;
        this.isMeSend = isMeSend;
    }

    public String getName() {
        return name;
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

}
