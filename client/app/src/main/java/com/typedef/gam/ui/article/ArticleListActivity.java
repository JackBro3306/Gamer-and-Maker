package com.typedef.gam.ui.article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.presenter.article.ArticleListContract;
import com.typedef.gam.presenter.article.ArticleListPresenter;
import com.typedef.gam.ui.base.RootActivity;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;
import com.typedef.gam.utils.CommonUtils;
import com.typedef.gam.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ArticleListActivity extends RootActivity<ArticleListPresenter> implements ArticleListContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.view_main)
    SmartRefreshLayout mRefresh;
    private ArticleListAdapter mAdapter;

    private boolean dataReady = true;
    private TagsBean tagsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initEventAndData();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        showLoadingView();
        tagsBean = (TagsBean) getIntent().getSerializableExtra(Constants.BUNDLE_ARTICLE_LIST);
        initRecycler();
        initRefresh();
    }

    @Override
    public void showData(List<ArticleBean> articles) {
        mAdapter.clear();
        mAdapter.add(articles);
        showContentView();
    }

    @Override
    public void showMore(List<ArticleBean> articles) {
        mAdapter.add(articles);
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
                CommonUtils.startArticleDetailActivity(ArticleListActivity.this,
                        data.getTitle(),data.getThumbUrl(),data.getArticleUrl());
            }
        });

        mAdapter.setTagsClickListener(new RecyclerAdapter.AdapterListenerImpl<TagsBean>(){
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, TagsBean data) {
                super.onItemClick(holder, data);
                CommonUtils.startArticleListActivity(ArticleListActivity.this,data.getType(),data.getTid());
            }
        });
        mRecycler.setAdapter(mAdapter);

        mPresenter.loadData(tagsBean.getTid());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
    }
    private void initRefresh() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(3000/*,false*/);//传入false表示刷新失败
                if(dataReady){
                    dataReady = false;
                    mPresenter.loadData(tagsBean.getTid());
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

    @OnClick({R.id.main_floating_action_btn})
    void onClick(View view) {
        mRecycler.smoothScrollToPosition(0);
    }
}
