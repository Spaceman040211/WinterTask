package com.example.progresseveryday.model;

import java.util.List;

public class LoginData {

    private List<String> collectIds;
    private String username;

    public List<String> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<String> collectIds) {
        this.collectIds = collectIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
