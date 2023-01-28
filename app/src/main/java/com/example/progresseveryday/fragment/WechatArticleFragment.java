package com.example.progresseveryday.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.progresseveryday.R;
import com.example.progresseveryday.adapter.RvWechatArticleAdapter;
import com.example.progresseveryday.model.WechatArticleModel;
import com.example.progresseveryday.model.WechatDataBean;
import com.example.progresseveryday.presenter.WechatArticlePresenter;
import com.example.progresseveryday.view.WechatArticleView;
import com.example.progresseveryday.views.IosLoadDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *      desc:微信公众号推文 Fragment
 * </pre>
 */
public class WechatArticleFragment extends LazyLoadFragment implements WechatArticleView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private IosLoadDialog dialog;

    private RvWechatArticleAdapter rvWechatArticleAdapter;

    private int page = 1;
    private WechatArticlePresenter wechatArticlePresenter;

    private static String chapterId;//公众号Id
    String id;

    private String link;//url
    private String title;//标题
    private List<String> linkList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    List<WechatDataBean> wechatArticleDatasBeanList = new ArrayList<>();
    List<String> chapterIdList = new ArrayList<>();//公众号Id集合

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    //当前状态
    private int state = STATE_NORMAL;


    Bundle bundle;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_wechat_article;
    }

    @Override
    protected void init() {

        ButterKnife.bind(this, rootView);

        bundle = this.getArguments();
        if (bundle != null){
            chapterId = bundle.getString("chapterId");
        }
        Log.e("chapterId",chapterId);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        wechatArticlePresenter = new WechatArticlePresenter(this);

        refreshLayout.setEnableLoadMore(true);



    }

    @Override
    protected void lazyLoad() {
//        Log.e("chapterId",chapterId);

        dialog = new IosLoadDialog(this.getContext());
        dialog.show();

        id = chapterId;

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("chapterIdrefre",id);
                refreshData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("chapterIdmore",chapterId);
                loadMoreData();
            }
        });

        wechatArticlePresenter.loadArticle(getActivity(),id,String.valueOf(page));
    }

    private void loadMoreData() {

        page = ++page;
        state = STATE_MORE;
        wechatArticlePresenter.loadArticle(getActivity(),id,String.valueOf(page));
        Log.e("wechartpage",page+"");

    }

    private void refreshData() {

        page = 1;
        state = STATE_REFRESH;
        wechatArticlePresenter.loadArticle(getActivity(),id ,String.valueOf(page));
//        Log.e("chapterIdrefre",chapterId);
    }

    /**
     * 微信公众号文章 数据
     * @param model
     */
    @Override
    public void getWechatArticleData(WechatArticleModel model) {
        wechatArticleDatasBeanList = model.getData().getDatas();

        for (int i = 0; i < wechatArticleDatasBeanList.size(); i++) {

            link = wechatArticleDatasBeanList.get(i).getLink();
            linkList.add(link);

            title = wechatArticleDatasBeanList.get(i).getTitle();
            titleList.add(title);

        }
        if (wechatArticleDatasBeanList.size()<0){

        }else
            showData();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

    private void showData() {

        switch (state) {
            case STATE_NORMAL:
                rvWechatArticleAdapter = new RvWechatArticleAdapter(wechatArticleDatasBeanList);
                //Recyclerview添加头部布局
//                rvHomeAdapter.setHeaderView(viewHeader);
                recyclerView.setAdapter(rvWechatArticleAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


                break;

            case STATE_REFRESH:
                if (rvWechatArticleAdapter != null)
                    rvWechatArticleAdapter.clearData();
                rvWechatArticleAdapter.addData(wechatArticleDatasBeanList);
                recyclerView.scrollToPosition(0);
                refreshLayout.finishRefresh();
                rvWechatArticleAdapter.notifyDataSetChanged();
                break;

            case STATE_MORE:
                rvWechatArticleAdapter.addData(rvWechatArticleAdapter.getDatas().size(), wechatArticleDatasBeanList);
                recyclerView.scrollToPosition(rvWechatArticleAdapter.getDatas().size());
                refreshLayout.finishLoadMore();
                break;

        }
        // 设置item及item中控件的点击事件
        rvWechatArticleAdapter.setOnItemClickListener(MyItemClickListener);

    }

    /**
     * item＋item里的控件点击监听事件
     */
    private RvWechatArticleAdapter.OnItemClickListener MyItemClickListener = new RvWechatArticleAdapter.OnItemClickListener() {

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