package com.typedef.gam.ui.article;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.presenter.article.ArticleListContract;
import com.typedef.gam.presenter.article.ArticleListPresenter;
import com.typedef.gam.ui.base.RootFragment;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;
import com.typedef.gam.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleListFragment extends RootFragment<ArticleListPresenter>
        implements ArticleListContract.View {

    public static final String TYPE = "type";
    public static final String TID = "tid";

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.view_main)
    SmartRefreshLayout mRefresh;
    private int tid;
    private ArticleListAdapter mAdapter;

    private boolean dataReady = true;
    public static ArticleListFragment newInstance(String type,int tid){
        ArticleListFragment hotPageFragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString(TYPE,type);
        args.putInt(TID,tid);
        hotPageFragment.setArguments(args);
        return hotPageFragment;
    }

    private ArticleListFragment() {

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        showLoadingView();
        initRecycler();
        initRefresh();
    }

    @Override
    public void jumpToTheTop() {
        if (mRecycler != null) {
            mRecycler.smoothScrollToPosition(0);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @Override
    public void showData(List<ArticleBean> articles) {
        mAdapter.clear();
        mAdapter.add(articles);
        showContentView();
    }

    @Override
    public void showMore(List<ArticleBean> articles) {

    }

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    private void initRecycler(){
        mAdapter = new ArticleListAdapter();
        mAdapter.setItemListener(new RecyclerAdapter.AdapterListenerImpl<ArticleBean>(){
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, ArticleBean data) {
                super.onItemClick(holder, data);
                CommonUtils.startArticleDetailActivity(getContext(),data.getTitle(),data.getThumbUrl(),data.getArticleUrl());
            }
        });
        mAdapter.setTagsClickListener(new RecyclerAdapter.AdapterListenerImpl<TagsBean>(){
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, TagsBean data) {
                super.onItemClick(holder, data);
                CommonUtils.startArticleListActivity(getContext(),data.getType(),data.getTid());
            }
        });
        tid = getArguments().getInt(TID);
        mRecycler.setAdapter(mAdapter);
        mPresenter.loadData(tid);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(linearLayoutManager);
    }

    private void initRefresh() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(3000/*,false*/);//传入false表示刷新失败
                if(dataReady){
                    dataReady = false;
                    mPresenter.loadData(tid);
                }
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(3000/*,false*/);//传入false表示加载失败
                if(dataReady){
                    dataReady = false;
                    mPresenter.loadMoreData();
                }
            }
        });
    }
}
