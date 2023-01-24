package com.example.progresseveryday.presenter;

import android.content.Context;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.SystemArticleListModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.ExceptionHelper;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.SystemArticleListView;

import okhttp3.Request;

public class SystemArticleListPresenter extends BasePresenter {
    private SystemArticleListView systemArticleListView;

    public SystemArticleListPresenter(SystemArticleListView systemArticleListView){
        this.systemArticleListView = systemArticleListView;
    }

    public void loadSystemArticleList(final Context context, String page, String id){
        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "article/list/"+ page + "/json?cid=" + id,
                new BaseDelegate.ResultCallback<SystemArticleListModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e,context));
                    }

                    @Override
                    public void onResponse(SystemArticleListModel response, Object tag) {
                        systemArticleListView.getSystemArticleListData(response);
                    }

                });

    }

}
