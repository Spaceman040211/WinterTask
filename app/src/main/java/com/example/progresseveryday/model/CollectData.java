package com.example.progresseveryday.model;

import java.util.List;

public class CollectData {
    private String curPage;
    private List<CollectDatasBean> datas;

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public List<CollectDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectDatasBean> datas) {
        this.datas = datas;
    }
}
