package com.example.progresseveryday.presenter;

import android.content.Context;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.RegisterModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.ExceptionHelper;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.RegisterView;

import java.util.Map;

import okhttp3.Request;

public class RegisterPresenter extends BasePresenter{

    private RegisterView registerView;

    public RegisterPresenter( RegisterView registerView){
        this.registerView = registerView;
    }

    public void loadRegister(final Context context, String username, String password, String repassword) {

        Map<String,String> params = getDefaultMD5Params("user","register");
        params.put("username", username);
        params.put("password",password);
        params.put("repassword",repassword);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/register",
                params, new BaseDelegate.ResultCallback<RegisterModel>() {

                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e,context));
                    }

                    @Override
                    public void onResponse(RegisterModel response, Object tag) {
                        registerView.getRegisterData(response);
                    }

                },true);
    }

}
