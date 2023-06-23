package com.example.wechatproject.LoginActivity;

import static com.example.wechatproject.network.JSONHandler.generateRegisterJSON;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.MainActivity;
import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.util.CurrentUserInfo;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {//注册界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 获取布局中的控件
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        // 在这里可以设置登录按钮的点击事件处理逻辑
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 处理登录按钮的点击事件，向服务器发送JSON并等待服务器返回信息
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // 向服务器发送JSON
                if(Objects.equals(Client.sendJSON(generateRegisterJSON(username, password, 0, 0, null)), "true")){
                    // 注册成功，跳转到微信主界面，并传递用户名
                    new CurrentUserInfo().setUsername(username);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前登录界面
                }else{
                    // 注册失败，弹出错误消息
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();

//                    //这是测试用跳转，测试完毕后删除
//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish(); // 结束当前登录界面
                }
            }
        });
    }

}
