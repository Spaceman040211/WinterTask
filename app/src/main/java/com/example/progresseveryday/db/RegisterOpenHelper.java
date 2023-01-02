package com.example.progresseveryday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegisterOpenHelper extends SQLiteOpenHelper {

    final String CREATE_USER_SQL="create table user(_id integer primary "+" key autoincrement,username,password )";

    public RegisterOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory Factory,int version){
        super(context,name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabaseb) {
       sqLiteDatabaseb.execSQL(CREATE_USER_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        System.out.println("===版本更新==="+oldVersion+"===>"+newVersion);

    }
}

