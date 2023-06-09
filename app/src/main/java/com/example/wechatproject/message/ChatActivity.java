package com.example.wechatproject.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;
import com.example.wechatproject.contact.Friends_CardActivity;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.FileUtil;
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
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动文件选择器
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);//不允许多选
                startActivityForResult(intent, 1);
            }
        });

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
                            DBHelper dbHelper = new DBHelper(ChatActivity.this);
                            dbHelper.addMessage(friendName, messageText, timeStamp, "0", "true");
                            // 刷新消息列表
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 刷新消息列表数据源
                                    chatItemList = dbHelper.getDesignatedMessage(friendName);
                                    // 通知适配器数据已更新
                                    chatAdapter.setData(chatItemList);
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

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 刷新消息列表
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新消息列表数据源，并更新适配器，使得刚发送的消息能够显示在消息列表中
                        DBHelper dbHelper = new DBHelper(ChatActivity.this);
                        chatItemList = dbHelper.getDesignatedMessage(friendName);
                        System.out.println("chatItemList.size() = " + chatItemList.size());
                        System.out.println("getCount() = " + chatAdapter.getCount());
                        chatAdapter.setData(chatItemList);
                    }
                });
            }
        }, 0, 3000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == 1){
            Uri uri = data.getData();
            String fileType = getContentResolver().getType(uri);
            String fileName = uri.getLastPathSegment();
            String fileNameWithoutExtension = fileName;
            int index = fileName.lastIndexOf(".");
            if(index != -1){
                fileNameWithoutExtension = fileName.substring(0, index);
            }
            String type = "6";
            if(fileName.contains(".img")){
                type = "1";
            }else if(fileName.contains(".mp3")){
                type = "2";
            }else if(fileName.contains(".mp4")){
                type = "3";
            }else if(fileName.contains(".txt")){
                type = "4";
            }
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            String finalType = type;
            Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String response) {
                    dbHelper.addMessage(friendName, uri.toString(), String.valueOf(System.currentTimeMillis()), finalType, "true");
                    // 刷新消息列表
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 刷新消息列表数据源
                            chatItemList = dbHelper.getDesignatedMessage(friendName);
                            // 通知适配器数据已更新
                            chatAdapter.setData(chatItemList);
                        }
                    });
                }
            });
            sendJSONTask.execute(JSONHandler.generateBase64JSON(CurrentUserInfo.getUsername(), friendName,FileUtil.fileToBase64(getApplicationContext(),uri) , finalType,fileNameWithoutExtension));
        }
    }
}
