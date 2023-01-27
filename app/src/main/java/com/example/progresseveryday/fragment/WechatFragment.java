package com.example.progresseveryday.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;






import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.progresseveryday.R;
import com.example.progresseveryday.adapter.WechatItemFragmentAdapter;
import com.example.progresseveryday.model.WechatListData;
import com.example.progresseveryday.model.WechatListModel;
import com.example.progresseveryday.presenter.WechatListPresenter;
import com.example.progresseveryday.view.WechatListView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      time:2023/1/15
 *      desc:微信公众号 Fragment
 * </pre>
 */

public class WechatFragment extends Fragment implements WechatListView {

    private String id;//公众号Id
    private String name;//公众号名称
    private List<String> idList = new ArrayList<>();//公众号Id 集合
    private List<WechatListData> wechatListDataList;
    private TabLayout tablayout_details;
    private ViewPager viewpager_details;

    private List<Fragment> mFragments= new ArrayList<>();
    private WechatItemFragmentAdapter imAdapter;
    private List<String> names = new ArrayList<>();

    View view;
    WechatListPresenter wechatListPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wechat,container,false);
        wechatListPresenter = new WechatListPresenter(this);
        wechatListPresenter.loadWechatList(getContext());
        return view;
    }

    private void initView() {
        tablayout_details = view.findViewById(R.id.tablayout_details);
        viewpager_details = view.findViewById(R.id.viewpager_details);

        init();
    }

    //页面
    private void init() {

        for (int i = 0; i<names.size();i++){
            mFragments.add(new WechatArticleFragment());
        }

        for (int i = 0;i<mFragments.size();i++){
            WechatArticleFragment wechatArticleFragment = (WechatArticleFragment) mFragments.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("chapterId",idList.get(i));
            wechatArticleFragment.setArguments(bundle);
        }

        imAdapter = new WechatItemFragmentAdapter(getChildFragmentManager(), names, mFragments, getContext());
        viewpager_details.setAdapter(imAdapter);
        tablayout_details.setupWithViewPager(viewpager_details);
    }

    /**
     * 获取公众号列表
     */
    @Override
    public void getWechatListData(WechatListModel model) {

        wechatListDataList = model.getData();

        for (int i = 0;i<wechatListDataList.size();i++){
            id = wechatListDataList.get(i).getId();
            idList.add(id);
            name = wechatListDataList.get(i).getName();
            names.add(name);
            Log.e("names",name);
        }

        initView();
    }


}