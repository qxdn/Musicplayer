package com.qianxu.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class LaunchActivity extends AppCompatActivity { //启动页面

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(1000); //展示启动页 1s
                    Intent intent = new Intent(LaunchActivity.this,LoginActivity.class);
                    startActivity(intent);  //跳转到登录页面
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
