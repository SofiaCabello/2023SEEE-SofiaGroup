package com.example.wechatproject.network;

import static com.example.wechatproject.network.JSONHandler.generateGetContentJSON;
import static com.example.wechatproject.network.JSONHandler.parseGetJSON;

import com.example.wechatproject.util.CurrentUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.TimerTask;


/**
 * Created by: 神楽坂千紗
 * 用以发送心跳包，保持连接以及检测新消息。
 */

public class HeartbeatTask extends TimerTask {
    private String response;

    @Override
    public void run() {
        try {
            response = Client.sendJSON(generateGetContentJSON(CurrentUserInfo.getInstance().getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResponse() {
        return response;
    }

    public boolean isNullResponse() throws JSONException {
        List<String> responseList = parseGetJSON(response);
        return responseList.size() == 0;
    }
}

