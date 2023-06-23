package com.example.wechatproject.network;

import android.os.AsyncTask;
import android.content.*;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by: 神楽坂千紗
 * 用以发送JSON字符串，登陆、信息传递都有用途。
 */
public class Client {
    //private static final String ServletURL = "http://localhost:8080/Server/json";
    private static final String ServletURL = "http://192.168.124.7:8080/Server/json";
    private Context context;

    public Client(Context context){
        this.context = context;
    }

    //发送JSON字符串
    public static String sendJSON(JSONObject json){
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
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();
                inputStream.close();

                return response.toString();
            }else{
                System.out.println("发送失败");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //请求消息，基于JSON格式交换信息，HTTP_GET方式
//    public static JSONObject GETJSON(){
//        JSONObject json = new JSONObject();
//        try{
//            //建立连接
//            URL url = new URL(ServletURL);
//            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            //获取响应并解析数据
//            int responseCode = connection.getResponseCode();
//            if(responseCode == HttpsURLConnection.HTTP_OK){
//                System.out.println("请求成功");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while((line = reader.readLine()) != null){
//                    response.append(line);
//                }
//                reader.close();
//
//                //解析JSON字符串
//                json = new JSONObject(response.toString());
//                return json;
//            }else{
//                System.out.println("请求失败");
//                return null;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return json;
//    }


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



}
