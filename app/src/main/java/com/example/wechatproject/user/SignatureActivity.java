package com.example.wechatproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechatproject.R;

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

        // 模拟保存个性签名的操作，这里假设成功保存
        boolean saveSuccess = true;

        if (saveSuccess) {
            Toast.makeText(this, "个性签名修改成功，请返回界面查看", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "个性签名修改失败", Toast.LENGTH_SHORT).show();
        }
    }
}

