package com.example.progresseveryday.adapter;

import android.content.Context;




import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.progresseveryday.fragment.WechatArticleFragment;

import java.util.List;

public class WechatItemFragmentAdapter extends FragmentPagerAdapter {
    private List<String> names;
    private List<Fragment> fragments;
    private Context context;


    public WechatItemFragmentAdapter(FragmentManager fm, List<String> names, List<Fragment> fragments, Context context) {
        super(fm);
        this.names = names;
        this.fragments = fragments;
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }
}
