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

                Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted(){
                    @Override
                    public void onTaskCompleted(String response){
                        if (Objects.equals(response, "true")) {
                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            CurrentUserInfo.getInstance().setUsername(username);
                            Intent intent = new Intent(WeChatLoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                sendJSONTask.execute(generateLoginJSON(username, password));
            }
        });
    }

}
