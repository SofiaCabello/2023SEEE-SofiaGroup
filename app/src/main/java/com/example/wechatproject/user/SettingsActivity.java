package com.example.wechatproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wechatproject.R;
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
}

