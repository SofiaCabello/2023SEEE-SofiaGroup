package com.example.wechatproject.user;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;
import com.example.wechatproject.util.CurrentUserInfo;

public class IPActivity extends AppCompatActivity {

    private EditText editTextIpAddress;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        editTextIpAddress = findViewById(R.id.editTextIpAddress);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAddress = editTextIpAddress.getText().toString().trim();
                CurrentUserInfo.getInstance().setIPAddress(ipAddress);
                Toast.makeText(IPActivity.this, "IP地址修改成功,请返回界面查看", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

