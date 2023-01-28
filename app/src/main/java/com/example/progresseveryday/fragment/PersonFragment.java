package com.example.progresseveryday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progresseveryday.activity.MainActivity;
import com.example.progresseveryday.R;
import com.example.progresseveryday.activity.CollectActivity;
import com.example.progresseveryday.activity.LoginActivity;
import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.config.SPConfig;
import com.example.progresseveryday.model.CollectModel;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.presenter.CollectPresenter;
import com.example.progresseveryday.utils.SPUtils;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.CollectView;

import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends Fragment implements View.OnClickListener,CollectView{



    private LinearLayout linearCollect;//收藏
    private LinearLayout linearLogOut;//退出登录

    private List<String> collectList = new ArrayList<>();
    private String name;
    private String username;
    private String flag;
    String path;
    int id;
    private String cookie;

    CollectPresenter collectPresenter;//判断登录状态

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_person, container, false);

        Intent intent = getActivity().getIntent();
        collectList = intent.getStringArrayListExtra("collectList");
        name = intent.getStringExtra("name");

        path = (String) SPUtils.get(this.getContext(), SPConfig.PICTUREPATH, "");
        ConfigValue.Picture_Path = (String) SPUtils.get(this.getContext(), SPConfig.PICTUREPATH, "");

        username = (String) SPUtils.get(this.getContext(), SPConfig.USERNAME, "");
        ConfigValue.User_Name = (String) SPUtils.get(this.getContext(), SPConfig.USERNAME, "");
        cookie = OkHttpClientManager.getCooki();
        Log.e("cookie", cookie);

        collectPresenter = new CollectPresenter(this);
        collectPresenter.loadCollect(this.getContext(), "0");
        return view;
    }

    private void initView() {

        linearCollect = view.findViewById(R.id.linear_collect);
        linearLogOut = view.findViewById(R.id.linear_log_out);

        linearCollect.setOnClickListener(this);
        linearLogOut.setOnClickListener(this);
    }


        @Override
        public void onClick (View v){
            switch (v.getId()){

                case R.id.bt_login://登录


                    startActivity(new Intent(this.getContext(), LoginActivity.class));
                    break;



                case R.id.linear_collect://收藏

                    startActivity(new Intent(this.getContext(), CollectActivity.class));
                    break;


                case R.id.linear_log_out://退出登录
                    if (flag.equals("1")) {

                        Utils.showDialog(getActivity(), "温馨提示", "确定要现在退出登陆吗？", "", "", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.txtDialogCancel:
                                        Log.e("toast", "取消");
                                        Utils.dismissDialog();
                                        break;
                                    case R.id.txtDialogSure:
                                        Log.e("toast", "确定");
                                        flag = "0";
                                        SPUtils.put(getActivity(), SPConfig.FLAG, flag);
                                        ConfigValue.Flag = flag;
                                        Utils.dismissDialog();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        });


                    } else {
                        Toast.makeText(this.getContext(), "您未登录！", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }

        }


    @Override
    public void getCollect(CollectModel model) {
        if (model.getErrorCode().equals("0")) {
            flag = "1";
        } else {
            flag = "0";
        }

        initView();
    }
}

