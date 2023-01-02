package com.example.progresseveryday.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


public class RegisterFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private TextView tvHead;
    private EditText etUsername,etPassword,etConfirmPassword;
    private ImageView ivLogo;
    private Button btRegister;

    public RegisterFragment() {

    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvHead=view.findViewById(R.id.textview_head);
        ivLogo=view.findViewById(R.id.img_login_logo);
        etUsername=view.findViewById(R.id.edt_username);
        etPassword=view.findViewById(R.id.edt_password);
        etConfirmPassword=view.findViewById(R.id.edt_confirm_password);
        btRegister=view.findViewById(R.id.bt_register);
    }
}