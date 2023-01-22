package com.example.progresseveryday.presenter;

import android.content.Context;
import android.util.Log;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.WechatArticleModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.WechatArticleView;

import okhttp3.Request;

public class WechatArticlePresenter extends BasePresenter{

    private WechatArticleView wechatArticleView;

    public WechatArticlePresenter(WechatArticleView wechatArticleView){
        this.wechatArticleView = wechatArticleView;
    }

    public void loadArticle(final Context context, String chapterId, String page) {

        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "wxarticle/list/"+chapterId +"/" + page + "/json", new BaseDelegate.ResultCallback<WechatArticleModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, e.getMessage());
            }

            @Override
            public void onResponse(WechatArticleModel response, Object tag) {
                wechatArticleView.getWechatArticleData(response);
                Log.e("response",response+"");
            }

        });

    }

}
