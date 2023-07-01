package com.example.wechatproject.user;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;

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

                // 在这里执行修改IP地址的逻辑
                boolean isUpdateSuccessful = updateIpAddress(ipAddress);

                if (isUpdateSuccessful) {
                    Toast.makeText(IPActivity.this, "IP地址修改成功,请返回界面查看", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(IPActivity.this, "IP地址修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean updateIpAddress(String ipAddress) {
        // 在这里实现修改IP地址的逻辑
        // 这里只是一个示例，你需要根据具体需求自行实现
        // 返回true表示修改成功，返回false表示修改失败
        return true;
    }
}

