package com.example.progresseveryday.fragment;

import android.app.Instrumentation;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progresseveryday.R;
import com.example.progresseveryday.activity.RegisterActivity;
import com.example.progresseveryday.model.RegisterModel;
import com.example.progresseveryday.presenter.RegisterPresenter;
import com.example.progresseveryday.view.RegisterView;

public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterView {

    View view;
    private EditText etName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private TextView tvRegister;
    private TextView tvLogin;

    String username;
    String password;
    String repassword;

    RegisterPresenter registerPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register, container, false);

        registerPresenter = new RegisterPresenter(this);

        initView();

        return view;
    }

    private void initView() {

        etName = view.findViewById(R.id.edt_username);
        etPassword = view.findViewById(R.id.edt_password);
        etConfirmPassword = view.findViewById(R.id.edt_confirm_password);
        tvRegister = view.findViewById(R.id.bt_register);


        tvRegister.setOnClickListener(this);

    }

    public static Fragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register://注册
                username = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                repassword = etConfirmPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(getContext(), "用户名不能为空！", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (repassword.isEmpty()) {
                    Toast.makeText(getContext(), "确认密码不能为空！", Toast.LENGTH_SHORT).show();
                }

                registerPresenter.loadRegister(getContext(), username, password, repassword);

                break;

        }
    }

    private void toLoginFragment() {

        //模拟返回键
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 注册
     *
     * @param model
     */
    @Override
    public void getRegisterData(RegisterModel model) {

        if (model.getErrorCode().equals("0")) {
            toLoginFragment();
            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), model.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }

    }
}