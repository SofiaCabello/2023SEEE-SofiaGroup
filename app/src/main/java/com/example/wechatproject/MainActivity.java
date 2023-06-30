package com.example.wechatproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.wechatproject.contact.ContactFragment;
import com.example.wechatproject.message.Add_friendsActivity;
import com.example.wechatproject.message.MessageFragment;
import com.example.wechatproject.network.FileUtil;
import com.example.wechatproject.network.HeartbeatTask;
import com.example.wechatproject.network.JSONHandler;
import com.example.wechatproject.user.UserFragment;
import com.example.wechatproject.util.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.z.fileselectorlib.FileSelectorSettings;
import com.z.fileselectorlib.Objects.FileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by: 神楽坂千紗
 * 这是MainActivity类，主要包含了底部导航栏的切换功能。
 */

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private BottomNavigationView bottomNav;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private Timer timer;
    private ImageButton button_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello World"); // 测试语句

        DBHelper dbHelper = new DBHelper(this);

//        // 在后台启动心跳包发送任务
//        timer = new Timer();
//        HeartbeatTask heartbeatTask = new HeartbeatTask();
//        timer.schedule(heartbeatTask, 0, 10000);
//        try {
//            // 如果响应不为空，那么将响应内容转数据库执行
//            if (!heartbeatTask.isNullResponse()) {
//                String response = heartbeatTask.getResponse();
//                Log.d("HeartbeatTask", "onCreate: " + response);
//                // 响应内容由JSON字符串转换为ArrayList，其内容依次是sendId, TS, type, content
//                List<String> responseList = JSONHandler.parseGetJSON(response);
//                String sendId = responseList.get(0);
//                String TS = responseList.get(1);
//                String type = responseList.get(2);
//                String content = responseList.get(3);
//                /* 对于不同类型的响应，执行不同的操作
//                 * 1. 如果是文本，直接存入数据库
//                 * 2. 如果是二进制文件，将文件经解析后存入本地，再将文件路径存入数据库
//                 */
//                if (type.equals("0")) {
//                    dbHelper.addMessage(sendId,content,TS);
//                }else{
//                    String filePath = FileUtil.base64ToFile(content, type);
//                    dbHelper.addMessage(sendId, filePath, TS);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //绑定控件
        fragmentContainer = findViewById(R.id.fragmentContainer);
        bottomNav = findViewById(R.id.bottomNav);
        fragmentManager = getSupportFragmentManager();

//        //测试语句，测试完毕后删除
//        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new TestFragment()).commit();

        //切换fragment
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navMessage:
                        switchFragment(new MessageFragment());
                        break;
                    case R.id.navContact:
                        switchFragment(new ContactFragment());
                        break;
                    case R.id.navUser:
                        switchFragment(new UserFragment());
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit();
                return true;
            }
        });

        /* 下面的语句用于其他需要启动一个新的Fragment时，传回参数的情况。
         * 如果没有传回fragment参数，那么什么都不做。
         * 如果传回了fragment参数，那么就启动传回的fragment。
         * 这些参数是在其他Activity中传回的，包括message, contact, user三个参数。
         */
        String fragment = getIntent().getStringExtra("fragment");
        if (fragment == null) {
            switchFragment(new MessageFragment());
            bottomNav.setSelectedItemId(R.id.navMessage);
        } else {
            switch (fragment) {
                case "message":
                    switchFragment(new MessageFragment());
                    bottomNav.setSelectedItemId(R.id.navMessage);
                    break;
                case "contact":
                    switchFragment(new ContactFragment());
                    bottomNav.setSelectedItemId(R.id.navContact);
                    break;
                case "user":
                    switchFragment(new UserFragment());
                    bottomNav.setSelectedItemId(R.id.navUser);
                    break;
            }
        }


        button_image=findViewById(R.id.image_add);
        //加好友界面加载
        button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_friendsActivity.class);
                startActivity(intent);
            }
        });
    }



    //切换fragment方法
    private void switchFragment(Fragment fragment) {
        if (currentFragment == fragment) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (fragment.isAdded()) {
            transaction.add(R.id.fragmentContainer, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
        currentFragment = fragment;
    }


}