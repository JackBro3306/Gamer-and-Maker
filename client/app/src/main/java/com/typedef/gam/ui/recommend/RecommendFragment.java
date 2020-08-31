package com.typedef.gam.ui.recommend;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.BannerBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.presenter.recommend.RecommendContract;
import com.typedef.gam.presenter.recommend.RecommendPresenter;
import com.typedef.gam.ui.article.ArticleListFragment;
import com.typedef.gam.ui.base.RootFragment;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;
import com.typedef.gam.ui.recommend.adapter.RecommendRecyclerAdapter;
import com.typedef.gam.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends RootFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.view_main)
    SmartRefreshLayout mRefresh;
    private RecommendRecyclerAdapter mAdapter;

    private boolean dataReady = false;
    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public void showRecommend(RecommendBean recommendBean) {
        showContentView();
        dataReady = true;
        if(recommendBean.getBanners() != null&&recommendBean.getBanners().size()>0){
            mAdapter.setBannerList(recommendBean.getBanners());
        }
        mAdapter.clear();
        mAdapter.add(recommendBean.getArticles());
    }

    @Override
    public void showMore(List<ArticleBean> articleBeans) {
        dataReady = true;
        mAdapter.add(articleBeans);
    }

    @Override
    public void showErrorMsg(String msg) {
        dataReady = true;
        super.showErrorMsg(msg);
        if(mRefresh.isRefreshing()){
            mRefresh.finishLoadMore(false);
        }
        if(mRefresh.isLoading()){
            mRefresh.finishLoadMore(false);
        }
    }

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        // 初始化布局和加载数据
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

    private void initRefresh() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(3000/*,false*/);//传入false表示刷新失败
                if(dataReady){
                    dataReady = false;
                    mPresenter.loadData();
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
    private void initRecycler() {
        mAdapter = new RecommendRecyclerAdapter();
        mAdapter.setItemListener(new RecyclerAdapter.OnClickListener<ArticleBean>() {
            @Override
            public void onItemClick(ArticleBean data, int position) {
                // 跳转到webView
                CommonUtils.startArticleDetailActivity(getContext(),
                        data.getTitle(),data.getThumbUrl(),data.getArticleUrl());
            }
        });
        mAdapter.setBannerListener(new RecyclerAdapter.OnClickListener<BannerBean>() {
            @Override
            public void onItemClick(BannerBean data, int position) {
                // 跳转到webView
                CommonUtils.startArticleDetailActivity(getContext(),
                        data.getTitle(),data.getImgUrl(),data.getArticleUrl());
            }
        });
        mAdapter.setTagsClickListener(new RecyclerAdapter.OnClickListener<TagsBean>(){

            @Override
            public void onItemClick(TagsBean data, int position) {
                // TODO 显示文章列表
                CommonUtils.startArticleListActivity(getContext(),data.getType(),data.getTid());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mAdapter);
        dataReady = false;
        mPresenter.loadData();
    }

}
