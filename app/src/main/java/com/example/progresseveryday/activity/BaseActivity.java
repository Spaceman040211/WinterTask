package com.example.progresseveryday.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progresseveryday.utils.ActivityManager;

public class BaseActivity extends AppCompatActivity {

    private int baise = 0x00000000;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(baise);
        }*/
        //去掉状态栏
       /* getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );*/


        mContext = this;

    }



    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.getActivityManager().pushActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.getActivityManager().popActivity(this);
    }

}
