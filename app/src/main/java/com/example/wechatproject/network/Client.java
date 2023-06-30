package com.example.wechatproject.network;

import android.os.AsyncTask;
import android.content.*;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by: 神楽坂千紗
 * 用以发送JSON字符串，登陆、信息传递都有用途。
 */
public class Client {

    public static class SendJSONTask extends AsyncTask<JSONObject, Void, String> {
        private OnTaskCompleted onTaskCompleted;
        private Context context;
        private String response;

        public SendJSONTask(Context context, OnTaskCompleted onTaskCompleted) {
            this.context = context;
            this.onTaskCompleted = onTaskCompleted;
        }

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            JSONObject json = jsonObjects[0];
            try {
                // 建立连接
                // ...
                String servletURL = "http://172.20.10.4:8080/Server/json";
                URL url = new URL(servletURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

                // 发送JSON对象
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(json.toString().getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();

                // 获取响应
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    inputStream.close();

                    this.response = response.toString();
                } else {
                    System.out.println("发送失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // 在任务完成后执行的操作
            // 可以在这里处理网络请求的结果
            // ...

            if (response != null) {
                onTaskCompleted.onTaskCompleted(response);
            } else {
                onTaskCompleted.onTaskCompleted("error");
            }
        }

        public String getResponse(){
            return response;
        }
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(String response);
    }

    // ...
}
