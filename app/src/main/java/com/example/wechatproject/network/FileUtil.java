package com.example.wechatproject.network;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by: 神楽坂千紗
 * 文件通用类，包括文件编解码等
 */
public class FileUtil {


    public static String base64ToFile(Context context,String base64, String fileType){
        String filePath = "";
        try{
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(base64);
            }else{
                bytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
            }
            String fileName = generateFileName(fileType);
            filePath = context.getFilesDir().getAbsolutePath() + "/" + fileName;
            System.out.println("file://"+context.getFilesDir().getAbsolutePath());
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return filePath;
    }

    public static String fileToBase64(Context context,Uri fileUri) {
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
