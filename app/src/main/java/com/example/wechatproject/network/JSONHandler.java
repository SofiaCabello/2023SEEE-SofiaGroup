package com.example.wechatproject.network;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by: 神楽坂千紗
 * 用以生成JSON字符串，在登陆、信息传递都有用途。
 */
public class JSONHandler {

    //生成登陆JSON对象
    public static JSONObject generateLoginJSON(String username, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //生成消息传递JSON对象
    public static JSONObject generateMessageJSON(String userID, String message, String destID) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("destID", destID);
            json.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //生成base64编码的JSON对象
    public static JSONObject genetateBase64JSON(String userID, String base64, String destID, String type) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("destID", destID);
            json.put("base64", base64);
            json.put("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    //解析消息传递JSON字符串
    public static List<String> parseMessageJSON(String json) throws JSONException {
        List<String> list = null;
        JSONObject jsonObject = new JSONObject(json);
        String userID = jsonObject.getString("userID");
        String destID = jsonObject.getString("destID");
        String message = jsonObject.getString("message");
        list.add(userID);
        list.add(destID);
        list.add(message);
        return list;
    }

    public void JSONHandler() {}
}
