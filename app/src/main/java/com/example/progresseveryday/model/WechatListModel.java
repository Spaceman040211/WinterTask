package com.example.progresseveryday.model;

import java.util.List;

public class WechatListModel extends BaseModel{
    private List<WechatListData> data;

    public List<WechatListData> getData() {
        return data;
    }

    public void setData(List<WechatListData> data) {
        this.data = data;
    }

}
