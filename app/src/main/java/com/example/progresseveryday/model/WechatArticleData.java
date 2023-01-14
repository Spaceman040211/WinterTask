package com.example.progresseveryday.model;

import java.util.List;

public class WechatArticleData {
    private String curPage;
    private List<WechatDataBean> datas;

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public List<WechatDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<WechatDataBean> datas) {
        this.datas = datas;
    }
}
