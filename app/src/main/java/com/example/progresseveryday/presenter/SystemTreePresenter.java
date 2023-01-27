package com.example.progresseveryday.presenter;

import android.content.Context;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.KnowledgeModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.ExceptionHelper;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.KnowledgeDataView;

import okhttp3.Request;

public class SystemTreePresenter extends BasePresenter{

    private KnowledgeDataView knowledgeDataView;

    public SystemTreePresenter(KnowledgeDataView knowledgeDataView){
        this.knowledgeDataView = knowledgeDataView;
    }

    public void loadSystemTree(final Context context){
        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "tree/json", new BaseDelegate.ResultCallback<KnowledgeModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, ExceptionHelper.getMessage(e,context));
            }

            @Override
            public void onResponse(KnowledgeModel response, Object tag) {
                knowledgeDataView.getSystemTreeData(response);
            }

        });

    }

}
