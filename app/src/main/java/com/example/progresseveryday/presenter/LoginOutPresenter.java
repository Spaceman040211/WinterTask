package com.example.progresseveryday.presenter;

import android.content.Context;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.LoginOutModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.LoginOutView;

import okhttp3.Request;

public class LoginOutPresenter extends BasePresenter{

    private LoginOutView loginOutView;

    public LoginOutPresenter(LoginOutView loginOutView){
        this.loginOutView = loginOutView;
    }

    public void loadArticle(final Context context, String page) {

        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "user/logout/json", new BaseDelegate.ResultCallback<LoginOutModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, e.getMessage());
            }

            @Override
            public void onResponse(LoginOutModel response, Object tag) {
                loginOutView.getLoginOutData(response);
            }


        });

    }
}
