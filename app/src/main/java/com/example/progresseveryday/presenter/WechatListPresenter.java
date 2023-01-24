package com.example.progresseveryday.presenter;


import android.content.Context;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.WechatListModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.WechatListView;

import okhttp3.Request;

public class WechatListPresenter extends BasePresenter {

    private WechatListView wechatListView;

    public WechatListPresenter(WechatListView wechatListView){
        this.wechatListView = wechatListView;
    }

    public void loadWechatList(final Context context) {

        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "wxarticle/chapters"+"/json", new BaseDelegate.ResultCallback<WechatListModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, e.getMessage());
            }

            @Override
            public void onResponse(WechatListModel response, Object tag) {
                wechatListView.getWechatListData(response);
            }

        });

    }

}
