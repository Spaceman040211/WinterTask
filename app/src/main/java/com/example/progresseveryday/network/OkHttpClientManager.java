package com.example.progresseveryday.network;

import android.content.Context;
import android.util.Log;

import com.example.progresseveryday.config.ConfigValue;
import com.example.progresseveryday.config.SPConfig;
import com.example.progresseveryday.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.ByteString;

public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private PostDelegate mPostDelegate = null;
    private GetDelegate mGetDelegate = null;
    private static String cooki;
    List<Cookie> cookies;

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.d("HttpLogInfo", message);
        }
    }

    private OkHttpClientManager(Context context) {
        mOkHttpClient = new OkHttpClient();
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
//        mOkHttpClient.setCache(cache);
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS).readTimeout(1000,TimeUnit.MILLISECONDS);
        builder.cache(cache);
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addNetworkInterceptor(logInterceptor);


       /* cooki = (String) SPUtils.get(context, SPConfig.COOKIE, "");
        ConfigValue.Cookie = (String) SPUtils.get(context, SPConfig.COOKIE, "");*/

        mOkHttpClient = builder
                .cookieJar(new CookieJar() {

                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    //                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//
//                    }
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies=cookieStore.get(url.host());
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
                        if (cookies != null) {
                            cooki = cookies.get(0).toString();
                            String cookie = cooki.substring(0, cooki.indexOf(";"));
                            setCooki(cookie);
                            return cookies;
                        } else {
                            cookies = new ArrayList<Cookie>();
                            return cookies;
                        }

                    }

                })
                .build();

        mPostDelegate = new PostDelegate(mOkHttpClient);
        mGetDelegate = new GetDelegate(mOkHttpClient);


//        // cookie enabled
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
//        /* just for test !!! */
//        mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });

    }

    public static String getCooki() {
        return cooki;
    }

    public static void setCooki(String cooki) {
        cooki = cooki;
    }

    public static OkHttpClientManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager(context);
                }
            }
        }
        return mInstance;
    }

    public PostDelegate _getPostDelegate() {
        return mPostDelegate;
    }

    private GetDelegate _getGetDelegate() {
        return mGetDelegate;
    }

    public static GetDelegate getGetDelegate(Context context) {
        return getInstance(context)._getGetDelegate();
    }

    public static PostDelegate getPostDelegate(Context context) {
        return getInstance(context)._getPostDelegate();
    }

    /**
     * ============Get?????????????????????============
     */
    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, false, null);
    }

    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, forceNetWork, null);
    }

    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback, boolean forceNetWork, Object tag) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, forceNetWork, tag);
    }

    /**
     * ============POST?????????????????????===============
     */
    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, null);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, forceNetWork);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, Object tag) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, tag);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, boolean forceNetWork, Object tag) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, forceNetWork, tag);
    }

    //    public static void cancelTag(Context context, Object tag) {
    public static void cancelTag(Context context, Object tag) {
        //   client = mOkHttpClient,
        if (context == null || tag == null) return;
        getInstance(context)._cancelTag(tag);
    }

    private void _cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        //     mOkHttpClient.cancel(tag);
//        mOkHttpClient.dispatcher().cancelAll();
    }

    public static OkHttpClient getClinet(Context context) {
        return getInstance(context).client();
    }

    private OkHttpClient client() {
        return mOkHttpClient;
    }

}

