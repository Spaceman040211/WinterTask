package com.example.progresseveryday.model;

public class RegisterModel extends  BaseModel{
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
