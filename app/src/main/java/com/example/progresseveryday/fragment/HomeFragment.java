package com.example.progresseveryday.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.progresseveryday.R;
import com.example.progresseveryday.adapter.RvHomeAdapter;
import com.example.progresseveryday.model.ArticleDatasBean;
import com.example.progresseveryday.model.ArticleModel;
import com.example.progresseveryday.presenter.ArticlePresenter;
import com.example.progresseveryday.view.ArticleView;
import com.example.progresseveryday.views.IosLoadDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ArticleView {

    FrameLayout framelayout;
    NestedScrollView scrollview;


    private RecyclerView recyclerView;
    private RvHomeAdapter rvHomeAdapter;
    View view;
    //定义头部view
    View viewHeader;
    private String link;
    private String title;
    private List<String> linkList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    ArticlePresenter articlePresenter;
    List<ArticleDatasBean> articleDatas;

    private SmartRefreshLayout refreshLayout;

    int page = 0;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    private IosLoadDialog dialog;

    //当前状态
    private int state = STATE_NORMAL;

    private ImageView item_home_love;

    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        dialog = new IosLoadDialog(this.getContext());
        dialog.show();

        scrollview = view.findViewById(R.id.scrollview);
        framelayout = view.findViewById(R.id.framelayout);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        articlePresenter = new ArticlePresenter(this);

        initRecyclerView();
        initRefreshLayout();


        articlePresenter.loadArticle(this.getContext(), String.valueOf(page));

        initTitleEvent();

        return view;
    }

    private void initTitleEvent() {

        framelayout.setBackgroundColor(Color.argb(0, 124, 199, 234));
        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                int scrollY = scrollview.getScrollY();
                if (scrollY <= 0) {
                    framelayout.setBackgroundColor(Color.argb(0, 124, 199, 234));
                } else if (scrollY > 0 && scrollY <= 255) {

                    float scale = (float) scrollY / 255;
                    float alpha = (float) 255 * scale;
                    framelayout.setBackgroundColor(Color.argb((int) alpha, 124, 199, 234));
                } else if (scrollY > 255) {
                    /**
                     * alpha 范围是0-255
                     */
                    framelayout.setBackgroundColor(Color.argb(255, 124, 199, 234));
                }

            }
        });

    }


    private void initRefreshLayout() {

        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }
        });

    }

    private void loadMoreData() {

        page = ++page;
        state = STATE_MORE;
        articlePresenter.loadArticle(this.getContext(), String.valueOf(page));

    }

    private void refreshData() {

        page = 0;
        state = STATE_REFRESH;
        articlePresenter.loadArticle(this.getContext(), String.valueOf(page));

    }


    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        viewHeader = LayoutInflater.from(this.getContext()).inflate(R.layout.item_header, (ViewGroup) recyclerView.getParent(), false);
    }





    private void showData() {

        switch (state) {
            case STATE_NORMAL:
                rvHomeAdapter = new RvHomeAdapter(articleDatas);
                //Recyclerview添加头部布局
                rvHomeAdapter.setHeaderView(viewHeader);
                recyclerView.setAdapter(rvHomeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                // 设置item及item中控件的点击事件
                rvHomeAdapter.setOnItemClickListener(MyItemClickListener);
                break;

            case STATE_REFRESH:
                if (rvHomeAdapter != null)
                    rvHomeAdapter.clearData();
                rvHomeAdapter = new RvHomeAdapter(articleDatas);
                //Recyclerview添加头部布局
                rvHomeAdapter.setHeaderView(viewHeader);
                recyclerView.setAdapter(rvHomeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                refreshLayout.finishRefresh();
                rvHomeAdapter.notifyDataSetChanged();
                break;

            case STATE_MORE:
                rvHomeAdapter.addData(rvHomeAdapter.getDatas().size() + 1, articleDatas);
                recyclerView.scrollToPosition(rvHomeAdapter.getDatas().size() + 1);
                refreshLayout.finishLoadMore();
                break;

        }
        // 设置item及item中控件的点击事件
        rvHomeAdapter.setOnItemClickListener(MyItemClickListener);

    }


    /**
     * item＋item里的控件点击监听事件
     */
    private RvHomeAdapter.OnItemClickListener MyItemClickListener = new RvHomeAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()) {


            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

    /**
     * 首页文章列表
     *
     * @param model
     */
    @Override
    public void getArticleData(ArticleModel model) {

        articleDatas = (List<ArticleDatasBean>) model.getData();

        for (int i = 0; i < articleDatas.size(); i++) {
            title = articleDatas.get(i).getTitle();
            titleList.add(title);
            link = articleDatas.get(i).getLink();
            linkList.add(link);

        }
        if (articleDatas.size() < 0) {

        } else
            showData();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });


    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        initRecyclerView();
        initRefreshLayout();


        if (articleDatas.size() >= 0) {

            linkList.clear();
            titleList.clear();

            articleDatas.clear();

            rvHomeAdapter.clearData();

        }

        articlePresenter.loadArticle(this.getContext(), String.valueOf(page));

        initTitleEvent();

    }
}
