package com.example.progresseveryday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.progresseveryday.R;
import com.example.progresseveryday.adapter.RvCollectAdapter;
import com.example.progresseveryday.model.CollectDatasBean;
import com.example.progresseveryday.model.CollectModel;
import com.example.progresseveryday.presenter.CollectPresenter;
import com.example.progresseveryday.utils.Utils;
import com.example.progresseveryday.view.CollectView;
import com.example.progresseveryday.views.IosLoadDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectActivity extends BaseActivity implements View.OnClickListener, CollectView {

    private LinearLayout linear_back;//返回
    private TextView tv_title;//标题

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.linear_no_income)
    LinearLayout linear_no_income;

    private IosLoadDialog dialog;

    private int page=0;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    //当前状态
    private int state = STATE_NORMAL;


    private String link;//url
    private String title;//标题
    private List<String> linkList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    CollectPresenter collectPresenter ;

    private RvCollectAdapter rvCollectAdapter;

    List<CollectDatasBean> collectDatasBeanList = new ArrayList<>();


    private int mColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        ButterKnife.bind(this);

        collectPresenter = new CollectPresenter(this);
        dialog = new IosLoadDialog(this);
        dialog.show();

        initView();

        collectPresenter.loadCollect(this, String.valueOf(page));

    }

    private void initView() {

        tv_title = findViewById(R.id.tv_title);

        linear_back.setOnClickListener(this);
        tv_title.setText("收藏");

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
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
        collectPresenter.loadCollect(this, String.valueOf(page));

    }

    private void refreshData() {

        page = 0;
        state = STATE_REFRESH;
        collectPresenter.loadCollect(this, String.valueOf(page));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    /**
     * 收藏文章列表
     * @param model
     */
    @Override
    public void getCollect(CollectModel model) {

        if (model.getErrorCode().equals("0")) {

            collectDatasBeanList = model.getData().getDatas();

            for (int i = 0; i < collectDatasBeanList.size(); i++) {
//            chapterId = wechartArticleDatasBeanList.get(i).getChapterId();
//            chapterIdList.add(chapterId);

                link = collectDatasBeanList.get(i).getLink();
                linkList.add(link);

                title = collectDatasBeanList.get(i).getTitle();
                titleList.add(title);

            }
            if (collectDatasBeanList.size() < 0) {

                linear_no_income.setVisibility(View.VISIBLE);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            } else
                showData();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });

        }else {
            Utils.showToast(this,model.getErrorMsg());
            linear_no_income.setVisibility(View.VISIBLE);
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }

    }


    private void showData() {

        switch (state) {
            case STATE_NORMAL:
                rvCollectAdapter = new RvCollectAdapter(collectDatasBeanList);
                //Recyclerview添加头部布局
//                rvHomeAdapter.setHeaderView(viewHeader);
                recyclerView.setAdapter(rvCollectAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                // 设置item及item中控件的点击事件
//                rvWechartArticleAdapter.setOnItemClickListener(MyItemClickListener);
//                item分割线
//                recyclerview_mycommunity.setItemAnimator(new DefaultItemAnimator());
//                recyclerview_mycommunity.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


                break;

            case STATE_REFRESH:
                if (rvCollectAdapter != null)
                    rvCollectAdapter.clearData();
                rvCollectAdapter.addData(collectDatasBeanList);
                recyclerView.scrollToPosition(0);
//                rvWechartArticleAdapter = new RvWechartArticleAdapter(wechartArticleDatasBeanList);
////                //Recyclerview添加头部布局
////                rvHomeAdapter.setHeaderView(viewHeader);
//                recyclerView.setAdapter(rvSystemArticleAdapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                refreshLayout.finishRefresh();
                rvCollectAdapter.notifyDataSetChanged();
                break;

            case STATE_MORE:
                rvCollectAdapter.addData(rvCollectAdapter.getDatas().size(), collectDatasBeanList);
                recyclerView.scrollToPosition(rvCollectAdapter.getDatas().size());
                refreshLayout.finishLoadMore();
                break;

        }
        // 设置item及item中控件的点击事件
        rvCollectAdapter.setOnItemClickListener(MyItemClickListener);

    }

    /**
     * item＋item里的控件点击监听事件
     */
    private RvCollectAdapter.OnItemClickListener MyItemClickListener = new RvCollectAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()) {



            }
            Log.e("click",position+"");
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

}