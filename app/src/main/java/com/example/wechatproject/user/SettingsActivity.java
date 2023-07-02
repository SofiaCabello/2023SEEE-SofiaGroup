package com.example.wechatproject.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.wechatproject.R;
import com.example.wechatproject.login.LoginActivity;
import com.example.wechatproject.login.RegisterActivity;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.FileUtil;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.util.CurrentUserInfo;

public class SettingsActivity extends AppCompatActivity {
//这里需要添加每个按钮的响应
    private Button buttonChangeAvatar;
    private Button buttonChangeIP;
    private Button buttonChangeSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonChangeAvatar = findViewById(R.id.buttonChangeAvatar);
        buttonChangeIP = findViewById(R.id.buttonChangeIP);
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

        buttonChangeIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理修改IP的逻辑
                Intent intent = new Intent(SettingsActivity.this, IPActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonChangeSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理修改签名的逻辑
                Intent intent = new Intent(SettingsActivity.this, SignatureActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && 1 == requestCode) {
            // 处理修改头像的逻辑
            Uri SlectedFileUri = data.getData();
            String fileType = data.getType();
            CurrentUserInfo.getInstance().setAvatarFilePath(SlectedFileUri.toString());
            Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {
                    if(response.equals("success")){
                        Toast.makeText(getApplicationContext(), "修改头像成功", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            String fileBase64 = FileUtil.fileToBase64(getApplicationContext(), SlectedFileUri);
            sendJSONTask.execute(JSONHandler.generateUpdateAvatarJSON(CurrentUserInfo.getInstance().getUsername(), fileBase64));
        }
    }
}

