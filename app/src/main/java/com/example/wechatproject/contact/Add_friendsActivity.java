package com.example.wechatproject.contact;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.MainActivity;
import com.example.wechatproject.R;
import com.example.wechatproject.user.SettingsActivity;

public class Add_friendsActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonSearch;

    private LinearLayout layoutUserInfo;
    private ImageView imageViewProfile;
    private TextView textViewUsername;
    private ImageView imageView;

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
        imageView = findViewById(R.id.imageViewProfile);

        // 设置搜索按钮点击事件
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();

                // TODO: 在数据库中根据用户名进行搜索，判断用户是否存在

                if (username.isEmpty()) {
                    // 用户名为空，显示用户不存在的消息提示
                    Toast.makeText(Add_friendsActivity.this, "请输入用户名!", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户名不为空，根据搜索结果进行显示用户信息或用户不存在的消息提示
                    if (/* 用户存在 */true) {//这里需要更改，为防止报错，我在此处添加了true,
                        // 这个if空里面的是上面匹配数据库中应当定义的函数返回的true或者false


                        // 更新用户信息
                        // TODO: 根据搜索结果更新用户信息
                        /*如果存在，则imageViewProfile用户头像和textViewUsername用户名
                        设置为搜索到的数据库中的数据，后面的layout会有显示*/

                        // 显示用户信息
                        showUserNotExistMessage(false);
                        showUserInfo(true);


                    } else {
                        // 用户不存在，显示用户不存在的消息提示
                        showUserNotExistMessage(true);
                        showUserInfo(false);
                    }
                }
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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Add_friendsActivity.this, AddFriends_CardActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            layoutUserInfo.setVisibility(View.GONE);
        }
    }
}
