package com.example.progresseveryday.presenter;

import android.content.Context;
import android.util.Log;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.CollectModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.CollectView;

import okhttp3.Request;

public class CollectPresenter extends BasePresenter {

    private CollectView collectView;

    public CollectPresenter(CollectView collectView){
        this.collectView = collectView;
    }

    public void loadCollect(final Context context, String page) {

        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "lg/collect/list/" + page + "/json", new BaseDelegate.ResultCallback<CollectModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, e.getMessage());
            }

            @Override
            public void onResponse(CollectModel response, Object tag) {
                collectView.getCollect(response);
                Log.e("response",response+"");
            }

        });

    }

}
