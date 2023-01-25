package com.example.progresseveryday.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.progresseveryday.R;

public class LifeFragment extends Fragment {
    private String mTitle;

    public static LifeFragment getInstance(String title) {
        LifeFragment sf = new LifeFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_life, null);

        return v;
    }
}
