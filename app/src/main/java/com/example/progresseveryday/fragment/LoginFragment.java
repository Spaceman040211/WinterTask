package com.example.progresseveryday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progresseveryday.R;
import com.example.progresseveryday.activity.LoginActivity;
import com.example.progresseveryday.activity.MainActivity;
import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.config.SPConfig;
import com.example.progresseveryday.model.LoginModel;
import com.example.progresseveryday.presenter.LoginPresenter;
import com.example.progresseveryday.utils.SPUtils;
import com.example.progresseveryday.view.LoginView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment implements View.OnClickListener, LoginView {
    View view;
    private EditText etName;
    private EditText etPassword;
    private TextView tvLogin;
    private TextView tvRegister;

    String name;//用户名
    String password;//密码

    LoginPresenter loginPresenter;
    List<String> collectList = new ArrayList<>();
    String flag = "0";

    String cookie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login,container,false);
        loginPresenter = new LoginPresenter(this);
        initView();
        return view;
    }

    private void initView() {

        etName = view.findViewById(R.id.edt_username);
        etPassword = view.findViewById(R.id.edt_password);
        tvLogin = view.findViewById(R.id.bt_login);
        tvRegister = view.findViewById(R.id.bt_register);

        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }

    public static Fragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login://登录
                name = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(getContext(), "账号不能为空！", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toast.makeText(getContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else
                    loginPresenter.loadLogin(this.getContext(),name,password);
                break;

            case R.id.bt_register://注册
                ((LoginActivity) getActivity()).toRegisterFragment();
                break;
        }
    }

    /**
     * 登录
     * @param model
     */
    @Override
    public void getLoginData(LoginModel model) {

        if (model.getErrorCode().equals("0")){

            collectList = model.getData().getCollectIds();
            name = model.getData().getUsername();

            Intent intent = new Intent(this.getContext(), MainActivity.class);
            intent.putExtra("collectList", (Serializable) collectList);
            intent.putExtra("name",name);
            intent.putExtra("id",1);

            SPUtils.put(this.getContext(), SPConfig.USERNAME, name);
            ConfigValue.User_Name = name;


            flag = "1";
            SPUtils.put(this.getContext(), SPConfig.FLAG, flag);
            ConfigValue.Flag = flag;

            startActivity(intent);


        }else {
            Toast.makeText(this.getContext(), model.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }

    }


}
