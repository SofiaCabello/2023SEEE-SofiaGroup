package com.example.wechatproject.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;
import com.example.wechatproject.login.LoginActivity;
import com.example.wechatproject.login.RegisterActivity;
import com.example.wechatproject.message.ChatActivity;

public class Friends_CardActivity extends AppCompatActivity {
    //此界面为好友信息界面，有一个发消息按钮，此处需要设置点击事件，跳转到对话界面
    private Button friends_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_card);

        friends_message = findViewById(R.id.buttonmessage);

        friends_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到对话界面
                Intent intent = new Intent(Friends_CardActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
