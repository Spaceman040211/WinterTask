package com.example.progresseveryday.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ArticleUtil {
    public static String ARTICLE_URL = "https://www.wanandroid.com/article/list/0/json";

    public static String doGet(String url){
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        String bookISONString = null;
        try {
            //1.建立连接
            URL requestUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestProperty("Charset","utf-8");
            httpURLConnection.connect();

            //2.获取二进制流
            InputStream inputStream = httpURLConnection.getInputStream();
            //3.将二进制流包装
            reader = new BufferedReader((new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
            //4.从BufferedReader中读取string字符串
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0){
                return null;
            }
            bookISONString = builder.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (reader !=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bookISONString;
    }
    public static String getArticle(String page){
        String articleUrl = ARTICLE_URL+"?page="+page;
        Log.d("fan","========article====" + articleUrl);
        String articleResult = doGet(articleUrl);

        return articleResult;
    }

}
