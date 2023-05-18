package com.example.wechatproject.network;

import static com.example.wechatproject.network.JSONHandler.generateMessageJSON;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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
    private static final String ServletURL = "http://localhost:8080/WeChatServer/Servlet";//服务器地址，要改

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

}
