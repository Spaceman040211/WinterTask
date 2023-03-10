package com.example.progresseveryday.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.progresseveryday.R;
import com.example.progresseveryday.fragment.LoginFragment;
import com.example.progresseveryday.fragment.RegisterFragment;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button backBtn;
    private FrameLayout frameContain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        replaceFragment(LoginFragment.newInstance());

    }

    /**
     * 移除注册界面
     */
    public void toRegisterFragment() {
        replaceFragment(RegisterFragment.newInstance());
    }

    /**
     * 移除登录界面
     */
    public void toLoginFragment() {
        replaceFragment(LoginFragment.newInstance());
    }

    /**
     * 切换登录和注册
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContain,fragment);
        if (fragment instanceof RegisterFragment) transaction.addToBackStack(null);
        transaction.commit();

    }


    private void initView() {

        backBtn = (Button) findViewById(R.id.backBtn);
        frameContain = (FrameLayout) findViewById(R.id.frameContain);

        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn://返回
                finish();
                break;
        }
    }


}