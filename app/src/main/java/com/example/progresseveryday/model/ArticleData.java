package com.example.progresseveryday.model;

import java.util.List;

public class ArticleData {
    private String curPage;
    private List<ArticleDatasBean> datas;

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public List<ArticleDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleDatasBean> datas) {
        this.datas = datas;
    }
}
