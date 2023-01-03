package com.example.progresseveryday.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.progresseveryday.R;
import com.example.progresseveryday.fragment.HomeFragment;
import com.example.progresseveryday.network.ArticleUtil;

public class BottomActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llHome,llQuestion,llCollect,llFind,llMyself;
    private ImageView imgHome,imgQuestion,imgCollect,imgFind,imgMyself;
    private TextView tvHome,tvQuestion,tvCollect,tvFind,tvMyself,tvArticle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0){
                String article = (String) msg.obj;
                Log.d("fan","=====文章===");
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        /*封装控件*/
        initView();
        /*封装Fragment*/
        initEvent();
    }

    private void initEvent() {
        //添加fragment
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = HomeFragment.newInstance("这是首页","");
        fragmentTransaction.replace(R.id.fcv_fragment,homeFragment).commit();

        llHome.setOnClickListener(this);
        llQuestion.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        llFind.setOnClickListener(this);
        llMyself.setOnClickListener(this);

    }

    private void initView(){
        llHome=findViewById(R.id.ll_home);
        llQuestion=findViewById(R.id.ll_question);
        llCollect=findViewById(R.id.ll_collect);
        llFind=findViewById(R.id.ll_find);
        llMyself=findViewById(R.id.ll_myself);

        imgHome=findViewById(R.id.img_home);
        imgQuestion=findViewById(R.id.img_question);
        imgCollect=findViewById(R.id.img_collect);
        imgFind=findViewById(R.id.img_find);
        imgMyself=findViewById(R.id.img_myself);

        tvHome=findViewById(R.id.tv_home);
        tvQuestion=findViewById(R.id.tv_question);
        tvCollect=findViewById(R.id.tv_collect);
        tvFind=findViewById(R.id.tv_find);
        tvMyself=findViewById(R.id.tv_myself);
        tvArticle=findViewById(R.id.tv_article);

    }

    private void resetButtonState(){
        imgHome.setSelected(false);
        tvHome.setTextColor(getResources().getColor(R.color.black));
        imgQuestion.setSelected(false);
        tvQuestion.setTextColor(getResources().getColor(R.color.black));
        imgCollect.setSelected(false);
        tvCollect.setTextColor(getResources().getColor(R.color.black));
        imgFind.setSelected(false);
        tvFind.setTextColor(getResources().getColor(R.color.black));
        imgMyself.setSelected(false);
        tvMyself.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        resetButtonState();
        switch (id){
            case R.id.ll_home:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = HomeFragment.newInstance("","");
                fragmentTransaction.replace(R.id.fcv_fragment,homeFragment).commit();

                imgHome.setSelected(true);
                tvHome.setTextColor(getResources().getColor(R.color.teal_200));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String articleList = getArticleList();
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = articleList;
                        mHandler.sendMessage(message);
                    }
                }).start();


                break;
            case R.id.ll_question:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
               HomeFragment questionFragment =HomeFragment.newInstance("这是问答","");
                fragmentTransaction.replace(R.id.fcv_fragment,questionFragment).commit();

                imgQuestion.setSelected(true);
                tvQuestion.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case R.id.ll_collect:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment collectFragment =HomeFragment.newInstance("这是收藏","");
                fragmentTransaction.replace(R.id.fcv_fragment,collectFragment).commit();

                imgCollect.setSelected(true);
                tvCollect.setTextColor(getResources().getColor(R.color.teal_200));
                break;

            case R.id.ll_find:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment findFragment =HomeFragment.newInstance("这是发现","");
                fragmentTransaction.replace(R.id.fcv_fragment,findFragment).commit();

                imgFind.setSelected(true);
                tvFind.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case R.id.ll_myself:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment myselfFragment =HomeFragment.newInstance("这是我的","");
                fragmentTransaction.replace(R.id.fcv_fragment,myselfFragment).commit();

                imgMyself.setSelected(true);
                tvMyself.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            default:
                break;
        }

    }
    private String getArticleList(){
        return ArticleUtil.doGet("https://www.wanandroid.com/article/list/0/json");
    }

}