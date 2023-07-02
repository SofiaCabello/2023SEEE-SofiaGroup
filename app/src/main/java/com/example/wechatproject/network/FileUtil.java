package com.example.wechatproject.network;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by: 神楽坂千紗
 * 文件通用类，包括文件编解码等
 */
public class FileUtil {


    public static String base64ToFile(Context context, String base64, String fileType) {
        String filePath = "";
        try {
            byte[] bytes;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
            } else {
                bytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
            }
            String fileName = generateFileName(fileType);
            filePath = context.getFilesDir().getAbsolutePath() + "/" + fileName;
            System.out.println("file://" + context.getFilesDir().getAbsolutePath());
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


    public static String fileToBase64(Context context, Uri fileUri) {
        String base64 = "";
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byteArrayOutputStream.flush();

            byte[] bytes = byteArrayOutputStream.toByteArray();
            base64 = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);

            inputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
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
}
