package com.example.wechatproject.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.util.CurrentUserInfo;
import com.example.wechatproject.util.DBHelper;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class AddFriends_CardActivity extends AppCompatActivity {
    //此处有一个加好友按钮，需进行点击处理，之后跳转到加好友验证界面
    //Dai
    private Button Addfriends;
    //Dai
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends_card);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String avatarPath = intent.getStringExtra("avatarPath");
        String signature = intent.getStringExtra("signature");

        Addfriends = findViewById(R.id.buttonAdd);
        ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);
        TextView textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewSignature = findViewById(R.id.textViewSignature);
        textViewUsername.setText(username);
        textViewSignature.setText(signature);
        imageViewAvatar.setImageURI(Uri.parse(avatarPath));

        Addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client.SendJSONTask sendJSONTask = new Client.SendJSONTask(getApplicationContext(),new Client.OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String response) {
                        if (response.equals("true")) {
                            new DBHelper(getApplicationContext()).addContact(username, avatarPath, signature);
                            System.out.println(new DBHelper(getApplicationContext()).getContacts());
                        } else{
                            Toast.makeText(AddFriends_CardActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                sendJSONTask.execute(JSONHandler.generateAddConfirmJSON(CurrentUserInfo.getUsername(), username));
            }
        });

    }
}
