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
import com.example.wechatproject.util.DBHelper;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    private TextView tvFriendName;
    private ImageView ivOptions;
    private ListView chatListView;
    private EditText editTextMessage;
    private Button btnFile;
    private Button btnSend;

    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;

    @Override
    protected void onResume() {
        String friendName = getIntent().getStringExtra("name");
        super.onResume();
        // 刷新消息列表
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 刷新消息列表
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新消息列表数据源
                        DBHelper dbHelper = new DBHelper(ChatActivity.this);
                        chatItemList = dbHelper.getDesignatedMessage(friendName);
                        // 通知适配器数据已更新
                        chatAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String friendName = intent.getStringExtra("name");
        String avatarFilePath = intent.getStringExtra("avatarFilePath");

        // 初始化视图
        tvFriendName = findViewById(R.id.tvFriendName);
        ivOptions = findViewById(R.id.ivOptions);
        chatListView = findViewById(R.id.messageListView);
        editTextMessage = findViewById(R.id.editTextMessage);
        btnFile = findViewById(R.id.btnFile);
        btnSend = findViewById(R.id.btnSend);

        // 设置用户名字和图片点击事件，即右边的三点图片，表示打开用户的信息界面
        tvFriendName.setText(friendName);
        ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理选项按钮点击事件
                Intent intent = new Intent(ChatActivity.this, Friends_CardActivity.class);
                startActivity(intent);
            }
        });

        // 初始化消息列表数据源
        chatItemList = new DBHelper(getApplicationContext()).getDesignatedMessage(friendName);

        // 初始化适配器
        chatAdapter = new ChatAdapter(this, chatItemList);

        // 设置适配器给ListView
        chatListView.setAdapter(chatAdapter);

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

        // 刷新消息列表数据源
        //chatItemList.add(newChatItem);
        // 通知适配器数据已更新
        chatAdapter.notifyDataSetChanged();

        // 滚动到最后一条消息
        chatListView.smoothScrollToPosition(chatAdapter.getCount() - 1);
    }
}
