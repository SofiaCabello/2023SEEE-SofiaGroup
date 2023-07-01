package com.example.wechatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wechatproject.contact.ContactFragment;
import com.example.wechatproject.contact.Add_friendsActivity;
import com.example.wechatproject.message.MessageFragment;
import com.example.wechatproject.network.HeartbeatTask;
import com.example.wechatproject.user.UserFragment;
import com.example.wechatproject.util.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private TextView weixintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weixintext=findViewById(R.id.weixintext);
        System.out.println("Hello World"); // 测试语句

        //启动心跳包
        HeartbeatTask heartbeatTask = new HeartbeatTask(getApplicationContext());
        Timer timer = new Timer();
        timer.schedule(heartbeatTask, 0, 100000);

        //测试数据库
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        dbHelper.showAll();

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
                    weixintext.setText("联系人");
                    break;
                case "user":
                    switchFragment(new UserFragment());
                    bottomNav.setSelectedItemId(R.id.navUser);
                    weixintext.setText("我");
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