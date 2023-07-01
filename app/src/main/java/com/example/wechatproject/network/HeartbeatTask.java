package com.example.wechatproject.network;

import static com.example.wechatproject.network.JSONHandler.generateAddConfirmJSON;
import static com.example.wechatproject.network.JSONHandler.generateAddFriendJSON;
import static com.example.wechatproject.network.JSONHandler.generateGetContentJSON;
import static com.example.wechatproject.network.JSONHandler.parseGetJSON;

import android.content.Context;

import com.example.wechatproject.util.CurrentUserInfo;
import com.example.wechatproject.util.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by: 神楽坂千紗
 * 用以发送心跳包，保持连接以及检测新消息。
 */

public class HeartbeatTask extends TimerTask {
    private String response;
    public Context context;

    public HeartbeatTask(Context context) {
        this.context = context;
    }
    @Override
    public void run() {
        try {
            Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(context, new Client.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {
                    System.out.println("RESPONSE:"+response);
                    List<String> jsonList = splitJSONStrings(response);
                    for (String json : jsonList) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String type = jsonObject.optString("type");
                            String sourceUsername = jsonObject.optString("sendId");
                            String TS = jsonObject.optString("TS");
                            String content = jsonObject.optString("content");
                            if(type.equals("5")){
                                // 发送确认信息，确认好友的信息
                                Client.SendJSONTask jsonTask = new Client.SendJSONTask(context, new Client.OnTaskCompleted() {
                                    @Override
                                    public void onTaskCompleted(String response2) {
                                        JSONObject jsonObject2 = null;
                                        try {
                                            jsonObject2 = new JSONObject(response2);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        if(jsonObject2!=null){
                                            String friendPhotoId = jsonObject2.optString("friendPhotoId");
                                            String friendSignature = jsonObject2.optString("friendSignature");
                                            String filePath = FileUtil.base64ToFile(context,friendPhotoId,"1");
                                            DBHelper dbHelper = new DBHelper(context);
                                            dbHelper.addContact(sourceUsername,filePath,friendSignature);
                                        }
                                    }
                                });
                                jsonTask.execute(generateAddFriendJSON(CurrentUserInfo.getUsername(),sourceUsername));
                            }else if (type.equals("0")) {
                                new DBHelper(context).addMessage(sourceUsername, content, TS, "0","false");
                            }else if (type.equals("1")) {
                                String filePath = FileUtil.base64ToFile(context,content,"1");
                                new DBHelper(context).addMessage(sourceUsername, filePath, TS, "1","false");
                            }else if (type.equals("2")) {
                                String filePath = FileUtil.base64ToFile(context,content,"2");
                                new DBHelper(context).addMessage(sourceUsername, filePath, TS, "2","false");
                            }else if (type.equals("3")) {
                                String filePath = FileUtil.base64ToFile(context,content,"3");
                                new DBHelper(context).addMessage(sourceUsername, filePath, TS, "3","false");
                            }else if (type.equals("4")) {
                                String filePath = FileUtil.base64ToFile(context,content,"4");
                                new DBHelper(context).addMessage(sourceUsername, filePath, TS, "4","false");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            sendJSONTask.execute(generateGetContentJSON(CurrentUserInfo.getUsername()));
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

    public void setContext(Context context) {
        this.context = context;
    }

    // 写一个函数，用来分割JSON字符串，服务器的返回如下：
    // "{\"TS\":\"1111\",\"sendId\":\"new\",\"receiveId\":\"takina\",\"type\":5,\"content\":\"null\"},{\"TS\":\"1112\",\"sendId\":\"user\",\"receiveId\":\"takina\",\"type\":5,\"content\":\"null\"},{\"TS\":\"1113\",\"sendId\":\"use\",\"receiveId\":\"takina\",\"type\":5,\"content\":\"null\"}"
    // 返回的JSON个数不定，所以需要分割，并且去掉反斜杠
    public List<String> splitJSONStrings(String response) {
        List<String> jsonList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{.*?\\}");
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            String json = matcher.group();
            json = json.replace("\\", "");
            jsonList.add(json);
        }
        return jsonList;
    }
}

