package com.example.progresseveryday.model;

import java.util.List;

public class KnowledgeModel extends BaseModel{
    List<KnowledgeData> data;

    public List<KnowledgeData> getData() {
        return data;
    }

    public void setData(List<KnowledgeData> data) {
        this.data = data;
    }

}
