package com.example.wechatproject.message;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.FileUtil;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.util.CurrentUserInfo;

import org.json.JSONObject;

public class Add_friendsActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonSearch;

    private LinearLayout layoutUserInfo;
    private ImageView imageViewProfile;
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        // 获取视图引用
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSearch = findViewById(R.id.buttonSearch);
        layoutUserInfo = findViewById(R.id.layoutUserInfo);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewUsername = findViewById(R.id.textViewUsername);

        // 设置搜索按钮点击事件
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("------点击搜索按钮-----");
                String targetUsername = editTextUsername.getText().toString().trim();

                // TODO: 在数据库中根据用户名进行搜索，判断用户是否存在

                if (targetUsername.isEmpty()) {
                    // 用户名为空，显示用户不存在的消息提示
                    Toast.makeText(Add_friendsActivity.this, "请输入用户名!", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户名不为空，根据搜索结果进行显示用户信息或用户不存在的消息提示
                    Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String response) {
                            if (!response.isEmpty()) {
                                // TODO: 这里写本地数据库逻辑，包括是否已经添加了好友，以及没有添加好友的情况下的添加好友逻辑
                                // response是服务器返回的用户信息JSON字符串，下面解析JSON字符串并显示用户信息
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (jsonObject != null) {
                                            String friendName = jsonObject.optString("friendName");
                                            String friendPhotoId = jsonObject.optString("friendPhotoId");
                                            String filePath = FileUtil.base64ToFile(getApplicationContext(),friendPhotoId, "1");
                                            System.out.println("filePath: " + filePath);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imageViewProfile.setImageURI(Uri.parse("file://" + filePath));
                                                    textViewUsername.setText(friendName);
                                                    showUserNotExistMessage(false);
                                                    showUserInfo(true);
                                                }
                                            });
                                        }
                                    }
                                }).start();

                            } else{
                                showUserNotExistMessage(true);
                                showUserInfo(false);
                                Toast.makeText(Add_friendsActivity.this, "用户不存在!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    sendJSONTask.execute(JSONHandler.generateAddFriendJSON(CurrentUserInfo.getUsername(), targetUsername));
                }
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(Add_friendsActivity.this, FriendInfoActivity.class);
            }
        });
    }



    // 显示或隐藏用户不存在的消息提示
    private void showUserNotExistMessage(boolean show) {
        if (show) {
            Toast.makeText(Add_friendsActivity.this, "用户不存在!", Toast.LENGTH_SHORT).show();
        }
    }

    // 显示或隐藏用户信息布局
    private void showUserInfo(boolean show) {
        if (show) {
            layoutUserInfo.setVisibility(View.VISIBLE);
        } else {
            layoutUserInfo.setVisibility(View.GONE);
        }
    }
}
