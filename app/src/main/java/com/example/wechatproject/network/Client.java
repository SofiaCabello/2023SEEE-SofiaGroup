package com.example.wechatproject.network;

import static com.example.wechatproject.network.JSONHandler.generateMessageJSON;
import static com.example.wechatproject.network.JSONHandler.genetateBase64JSON;
import static com.z.fileselectorlib.Objects.FileInfo.FileType.File;

import android.net.Uri;
import android.os.AsyncTask;
import android.content.*;
import android.os.Build;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by: 神楽坂千紗
 * 用以发送JSON字符串，登陆、信息传递都有用途。
 */
public class Client {
    private static final String ServletURL = "http://localhost:8080/WeChatServer/Servlet";//服务器地址，要改
    private Context context;

    /**
     * 以下是发送文件的方法，分为图片、音频、视频、其他文件。
     * 是最终封装好的方法，应该调用这些部分。
     */

    public void sendImageToServer(Uri fileUri,String userID, String destID){
        String base64 = base64Encode(fileUri);
        JSONObject json = genetateBase64JSON(userID,destID,base64,"image");
        sendJSON(json);
    }

    public void sendAudioToServer(Uri fileUri,String userID, String destID){
        String base64 = base64Encode(fileUri);
        JSONObject json = genetateBase64JSON(userID,destID,base64,"audio");
        sendJSON(json);
    }

    public void sendVideoToServer(Uri fileUri,String userID, String destID){
        String base64 = base64Encode(fileUri);
        JSONObject json = genetateBase64JSON(userID,destID,base64,"video");
        sendJSON(json);
    }

    public void sendOtherFileToServer(Uri fileUri,String userID, String destID){
        String base64 = base64Encode(fileUri);
        JSONObject json = genetateBase64JSON(userID,destID,base64,"other");
        sendJSON(json);
    }



    public Client(Context context){
        this.context = context;
    }
    //发送JSON字符串
    public static void sendJSON(JSONObject json){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    //建立连接
                    URL url = new URL(ServletURL);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

                    //发送JSON对象
                    OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                    out.write(json.toString().getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();

                    //获取响应
                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpsURLConnection.HTTP_OK){
                        System.out.println("发送成功");
                    }else{
                        System.out.println("发送失败");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    //请求消息，基于JSON格式交换信息，HTTP_GET方式
    public static JSONObject GETJSON(){
        JSONObject json = new JSONObject();
        try{
            //建立连接
            URL url = new URL(ServletURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //获取响应并解析数据
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK){
                System.out.println("请求成功");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();

                //解析JSON字符串
                json = new JSONObject(response.toString());
                return json;
            }else{
                System.out.println("请求失败");
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }

//    public void sendFileToServer(Uri fileUri){
//        String mimeType = context.getContentResolver().getType(fileUri);
//
//        if(mimeType!=null){
//            if (mimeType.startsWith("image/")) {
//                // 图片文件类型
//                sendImageToServer(fileUri);
//            } else if (mimeType.startsWith("audio/")) {
//                // 音频文件类型
//                sendAudioToServer(fileUri);
//            } else if (mimeType.startsWith("video/")) {
//                // 视频文件类型
//                sendVideoToServer(fileUri);
//            } else {
//                // 其他文件类型
//                sendOtherFileToServer(fileUri);
//            }
//        }
//    }

    private String base64Encode(Uri fileUri){
        String base64 = "";
        try{
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                bytes = context.getContentResolver().openInputStream(fileUri).readAllBytes();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                base64 = Base64.getEncoder().encodeToString(bytes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return base64;
    }

}
