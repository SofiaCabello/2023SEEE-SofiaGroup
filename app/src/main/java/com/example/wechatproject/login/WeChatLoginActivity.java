package com.example.wechatproject.login;

import static com.example.wechatproject.network.JSONHandler.generateLoginJSON;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.MainActivity;
import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.util.CurrentUserInfo;

import java.util.Objects;

public class WeChatLoginActivity extends AppCompatActivity {//登陆界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechatlogin);

        // 获取布局中的控件
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        // 在这里可以设置登录按钮的点击事件处理逻辑
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 处理登录按钮的点击事件
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 假设数据库中的用户名和密码为 "admin" 和 "password"，进行简单验证
                if (Objects.equals(Client.sendJSON(generateLoginJSON(username, password)), "true")) {
                    // 登录成功，跳转到微信主界面，并传递用户名
                    CurrentUserInfo.getInstance().setUsername(username);
                    Intent intent = new Intent(WeChatLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前登录界面
                } else {
                    // 登录失败，弹出错误消息
                    Toast.makeText(WeChatLoginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();

//                    // 这是测试用跳转，测试完毕后删除
//                    Intent intent = new Intent(WeChatLoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish(); // 结束当前登录界面
                }
            }
        });
    }

}
