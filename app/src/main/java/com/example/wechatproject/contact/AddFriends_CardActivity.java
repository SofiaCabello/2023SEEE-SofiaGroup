package com.example.wechatproject.contact;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wechatproject.R;
public class AddFriends_CardActivity extends AppCompatActivity {
    //此处有一个加好友按钮，需进行点击处理，之后跳转到加好友验证界面
    private Button Addfriends;
    //Dai
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends_card);

        Addfriends = findViewById(R.id.buttonAdd);

        Addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到加好友验证界面
            }
        });

    }
}
