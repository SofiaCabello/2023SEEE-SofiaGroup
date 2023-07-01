package com.example.wechatproject.login;

import static com.example.wechatproject.network.JSONHandler.generateRegisterJSON;

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
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted(){
                    @Override
                    public void onTaskCompleted(String response){
                        if (Objects.equals(response, "true")) {
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                            CurrentUserInfo.getInstance().setUsername(username);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                sendJSONTask.execute(generateRegisterJSON(username, password, "null", "null"));
            }
        });
        }
}
