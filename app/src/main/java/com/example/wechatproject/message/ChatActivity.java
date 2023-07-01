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
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.util.CurrentUserInfo;
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
    private String friendName;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItemList;
    private Timer timer;


    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        this.friendName = intent.getStringExtra("name");
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
                    String timeStamp = String.valueOf(System.currentTimeMillis());
                    // 发送消息
                    Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String response) {
                            // 刷新消息列表
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 刷新消息列表数据源
                                    DBHelper dbHelper = new DBHelper(ChatActivity.this);
                                    dbHelper.addMessage(friendName,messageText,timeStamp,"0","true");
                                    chatItemList = dbHelper.getDesignatedMessage(friendName);
                                    // 通知适配器数据已更新
                                    chatAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    sendJSONTask.execute(JSONHandler.generatePostMessageJSON(CurrentUserInfo.getUsername(), friendName, messageText));
                    // 清空输入框
                    editTextMessage.setText("");
                }
            }
        });

        startTimer();
    }

    private void startTimer(){
        timer = new Timer();
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

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
