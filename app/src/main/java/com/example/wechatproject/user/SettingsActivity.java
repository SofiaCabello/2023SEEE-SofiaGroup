package com.example.wechatproject.user;

import static com.example.wechatproject.network.JSONHandler.generateUpdateJSON;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.FileUtil;
import com.example.wechatproject.util.CurrentUserInfo;

public class SettingsActivity extends AppCompatActivity {
//这里需要添加每个按钮的响应
    private Button buttonChangeAvatar;
    private Button buttonChangeUsername;
    private Button buttonChangeSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonChangeAvatar = findViewById(R.id.buttonChangeAvatar);
        buttonChangeUsername = findViewById(R.id.buttonChangeUsername);
        buttonChangeSignature = findViewById(R.id.buttonChangeSignature);

        buttonChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理修改头像的逻辑
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);//单选
                startActivityForResult(intent, 1);
            }
        });

        buttonChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理修改用户名的逻辑
            }
        });

        buttonChangeSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理修改签名的逻辑
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && 1 == requestCode) {
            // 处理修改头像的逻辑
            Uri SlectedFileUri = data.getData();
            String fileType = data.getType();
//            String response = Client.sendJSON(
//                    generateUpdateJSON(CurrentUserInfo.getInstance().getUsername(), null,0,FileUtil.fileToBase64(SlectedFileUri),null,fileType));
            // 如果服务器返回成功，修改头像，否则弹出错误消息

        }
    }
}

