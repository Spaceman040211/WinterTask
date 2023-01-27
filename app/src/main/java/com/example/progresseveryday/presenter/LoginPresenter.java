package com.example.progresseveryday.presenter;

import android.content.Context;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.LoginModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.ExceptionHelper;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.LoginView;

import java.util.Map;

import okhttp3.Request;

public class LoginPresenter extends BasePresenter{
    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void loadLogin(final Context context, String username, String password) {

        Map<String,String> params = getDefaultMD5Params("user","login");
        params.put("username", username);
        params.put("password",password);

        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/login",
                params, new BaseDelegate.ResultCallback<LoginModel>() {

                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e,context));
                    }

                    @Override
                    public void onResponse(LoginModel response, Object tag) {
                        loginView.getLoginData(response);
                    }

                },true);

    }
}
