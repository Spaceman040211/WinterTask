package com.example.progresseveryday.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progresseveryday.R;
import com.example.progresseveryday.adapter.FlexboxAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

public class KnowledgeActivity extends AppCompatActivity {

    private ArrayList<String> dataList =new ArrayList();
    private ArrayList<Integer> colorList = new ArrayList();
    private RecyclerView rv;
    private String[] arr= {"aaaaaaaa.jpg","巴拉巴.jpg","我的下朋友企业.jpg","新中.jpg","我的好朋友是不同的风格类型哦哦哦哦哦","开心","测试tag标签"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        rv = findViewById(R.id.rev);
        //将正常的manager替换为FlexboxLayoutManager
        FlexboxLayoutManager layoutManager =new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);//设置水平方向。也可以设置垂直方向
        rv.setLayoutManager(layoutManager);

        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            list.add(arr[i]);
        }
        FlexboxAdapter adapter =new FlexboxAdapter(this,list);
        rv.setAdapter(adapter);

    }


}
