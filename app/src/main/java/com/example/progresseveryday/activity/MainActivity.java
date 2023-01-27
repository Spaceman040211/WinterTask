package com.example.progresseveryday.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.progresseveryday.R;
import com.example.progresseveryday.bean.Tab;
import com.example.progresseveryday.fragment.HomeFragment;
import com.example.progresseveryday.fragment.KnowledgeFragment;
import com.example.progresseveryday.fragment.PersonFragment;
import com.example.progresseveryday.fragment.WechatFragment;
import com.example.progresseveryday.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabhost;
    private LayoutInflater mInflater;//加载布局管理器
    private List<Tab> mTabs = new ArrayList<>(5);
    private Toolbar mToolBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityManager.getActivityManager().popAllActivityExceptOne(MainActivity.class);

        initTab();

    }

    private void initToolBar() {
        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "NavigationClicked", Toast.LENGTH_SHORT).show();
            }
        });

        mToolBar.inflateMenu(R.menu.menu_toolbar);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_settings){
                    Toast.makeText(MainActivity.this, "action_settings Clicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void initTab() {

        Tab tab_home = new Tab(R.string.home,R.drawable.selector_home_bg, HomeFragment.class);
        Tab tab_hot = new Tab(R.string.hot,R.drawable.selector_question, KnowledgeFragment.class);
        Tab tab_category = new Tab(R.string.catagory,R.drawable.selector_find, WechatFragment.class);
        Tab tab_mine = new Tab(R.string.mine,R.drawable.selector_myself, PersonFragment.class);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);


        for (Tab tab : mTabs){
            FragmentTabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(),null);
        }

        mTabhost.setCurrentTab(0);

    }

    private View buildIndicator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        ImageView img = view.findViewById(R.id.icon_tab);
        TextView text = view.findViewById(R.id.txt_indicator);
        img.setImageResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }

}