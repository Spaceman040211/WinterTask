package com.example.progresseveryday.presenter;

import android.content.Context;
import android.util.Log;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.model.ArticleModel;
import com.example.progresseveryday.network.BaseDelegate;
import com.example.progresseveryday.network.OkHttpClientManager;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.ArticleView;

import okhttp3.Request;

public class ArticlePresenter extends BasePresenter {
    private ArticleView articleView;

    public ArticlePresenter(ArticleView articleView){
        this.articleView = articleView;
    }

    public void loadArticle(final Context context,String page) {

        OkHttpClientManager.getAsyn(context, ConfigValue.APP_IP + "article/list/" + page + "/json", new BaseDelegate.ResultCallback<ArticleModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, e.getMessage());
            }

            @Override
            public void onResponse(ArticleModel response, Object tag) {
                articleView.getArticleData((ArticleModel) response);
                Log.e("response",response+"");
            }

        });

    }

}
