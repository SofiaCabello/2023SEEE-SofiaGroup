package com.example.wechatproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;
import com.example.wechatproject.network.Client;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.util.CurrentUserInfo;

public class SignatureActivity extends AppCompatActivity {
    private EditText editTextSignature;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        editTextSignature = findViewById(R.id.editTextSignature);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSignature();
            }
        });
    }

    private void saveSignature() {
        String newSignature = editTextSignature.getText().toString().trim();
        CurrentUserInfo.getInstance().setSignature(newSignature);
        Client.SendJSONTask task = new Client.SendJSONTask(getApplicationContext(), new Client.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if(response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "个性签名修改成功，请返回界面查看", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "个性签名修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute(JSONHandler.generateUpdateSignatureJSON(CurrentUserInfo.getUsername(), newSignature));
    }
}

