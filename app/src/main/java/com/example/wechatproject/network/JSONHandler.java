package com.example.wechatproject.network;


import android.content.Context;
import android.net.Uri;
import android.os.Build;

import com.example.wechatproject.util.CurrentUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Created by: 神楽坂千紗
 * 用以生成JSON字符串，在登陆、信息传递都有用途。
 */
public class JSONHandler {
    private CurrentUserInfo info;
    private static Context context;

    //生成注册JSON对象
    public static JSONObject generateRegisterJSON(String username, String password, int usergender, int photoId, String siganture) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "register");
            json.put("username", username);
            json.put("password", password);
            json.put("usergender", usergender);
            json.put("photoId", photoId);
            json.put("signature", siganture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //生成登陆JSON对象
    public static JSONObject generateLoginJSON(String username, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "login");
            json.put("username", username);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //生成发送消息JSON对象
    public static JSONObject generatePostMessageJSON(String sendId, String receiveId, String content) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "postMessage");
            json.put("sendId", sendId);
            json.put("receiveId", receiveId);
            json.put("content", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //生成接收消息请求JSON对象
    public static JSONObject generateGetContentJSON(String receiveId) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "getContent");
            json.put("receiveId", receiveId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //生成base64编码的JSON对象
    public static JSONObject generateBase64JSON(String sendId, String receiveId, String content, int fileType, String fileName) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "postBase64");
            json.put("sendId", sendId);
            json.put("receiveId", receiveId);
            json.put("fileType", fileType);
            json.put("fileName", fileName);
            json.put("content", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 以下是解析逻辑
     */

    //解析消息传递JSON字符串
    public static List<String> parseGetJSON(String json) throws JSONException {
        List<String> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);

        String sendId = jsonObject.getString("sendId");
        String TS = jsonObject.getString("TS");
        String type = jsonObject.getString("type");
        String content = jsonObject.getString("content");
        list.add(sendId);
        list.add(type);
        list.add(TS);
        list.add(content);

        return list;
    }


    /**
     * 以下是编码解码逻辑
     */

    //base64编码方法
    public static String fileToBase64(Uri fileUri) {
        String base64 = "";
        try {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                bytes = context.getContentResolver().openInputStream(fileUri).readAllBytes();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                base64 = Base64.getEncoder().encodeToString(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    // base64解码方法
    public static String base64ToFile(String base64, String fileType) {
        String filePath = "";
        try {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(base64);
            }
            String fileName = generateFileName(fileType);
            filePath = context.getFilesDir().getAbsolutePath() + "/" + fileName;
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private static String generateFileName(String fileType){
        if(Objects.equals(fileType, "1")){
            return "image_" + System.currentTimeMillis() + ".jpg";
        }else if(Objects.equals(fileType, "2")){
            return "audio_" + System.currentTimeMillis() + ".mp3";
        } else if (Objects.equals(fileType, "3")){
            return "video_" + System.currentTimeMillis() + ".mp4";
        } else {
            return "file_" + System.currentTimeMillis() + ".txt";
        }
    }

    public JSONHandler() {
    }
}
