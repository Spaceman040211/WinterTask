package com.example.progresseveryday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progresseveryday.R;
import com.example.progresseveryday.fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btRegister = (Button) findViewById(R.id.bt_register);
        btRegister.setOnClickListener(this);
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch(view.getId()){
            case R.id.bt_register:
                intent.setClass(this,RegisterActivity.class);
                break;
            case R.id.bt_login:
                intent.setClass(this,BottomActivity.class);
            default:
                break;
        }
        startActivity(intent);
    }
}