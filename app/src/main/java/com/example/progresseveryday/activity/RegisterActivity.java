package com.example.progresseveryday.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progresseveryday.R;
import com.example.progresseveryday.db.RegisterOpenHelper;

public class RegisterActivity extends AppCompatActivity {
    /*数据库成员变量*/
    private RegisterOpenHelper rgOpenHelper;


    private Button btnRegister;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        /*实例化数据库变量*/
        rgOpenHelper=new RegisterOpenHelper(RegisterActivity.this,"user.db",null,1);


    }
    //插入数据方法
    private void insertData(SQLiteDatabase readableDatabase, String username1, String password1){
        ContentValues values=new ContentValues();
        values.put("username",username1);
        values.put("password",password1);
        readableDatabase.insert("user",null,values);
    }
}
