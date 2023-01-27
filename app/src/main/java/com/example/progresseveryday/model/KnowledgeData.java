package com.example.progresseveryday.model;

import java.util.List;

public class KnowledgeData {
    private List<KnowledgeDataBean> children;
    private String name;

    public List<KnowledgeDataBean> getChildren() {
        return children;
    }

    public void setChildren(List<KnowledgeDataBean> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
