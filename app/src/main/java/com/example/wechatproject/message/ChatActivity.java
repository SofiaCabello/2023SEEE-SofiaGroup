package com.example.wechatproject.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;
import com.example.wechatproject.contact.Friends_CardActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private TextView tvFriendName;
    private ImageView ivOptions;
    private ListView messageListView;
    private EditText editTextMessage;
    private Button btnFile;
    private Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化视图
        tvFriendName = findViewById(R.id.tvFriendName);
        ivOptions = findViewById(R.id.ivOptions);
        messageListView = findViewById(R.id.messageListView);
        editTextMessage = findViewById(R.id.editTextMessage);
        btnFile = findViewById(R.id.btnFile);
        btnSend = findViewById(R.id.btnSend);

        // 设置用户名字和图片点击事件，即右边的三点图片，表示打开用户的信息界面
        tvFriendName.setText("用户名字");
        ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理选项按钮点击事件
                Intent intent = new Intent(ChatActivity.this, Friends_CardActivity.class);
                startActivity(intent);
            }
        });



        // 设置发送按钮点击事件
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    // 发送消息
                    sendMessage(messageText);
                    // 清空输入框
                    editTextMessage.setText("");
                }
            }
        });
    }

    private void sendMessage(String messageText) {
        // 创建消息对象，添加到消息列表中

        // 刷新消息列表

        // 滚动到最后一条消息

    }
}

