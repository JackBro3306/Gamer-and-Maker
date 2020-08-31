package com.typedef.gam.ui.tags;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.typedef.gam.R;
import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.presenter.tags.TagsContract;
import com.typedef.gam.presenter.tags.TagsPresenter;
import com.typedef.gam.ui.base.RootFragment;
import com.typedef.gam.ui.base.recycler.RecyclerAdapter;
import com.typedef.gam.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagsFragment extends RootFragment<TagsPresenter> implements TagsContract.View {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout tabLayout;

    private NavTagRecyclerAdapter mAdapter;

    private boolean needScroll;
    private int index;
    private boolean isClickTab;
    private LinearLayoutManager mLinearLayoutManager;

    public TagsFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tags;
    }

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mAdapter = new NavTagRecyclerAdapter();
        mAdapter.setTagsListener(new RecyclerAdapter.OnClickListener<TagsBean>() {
            @Override
            public void onItemClick(TagsBean data, int position) {
                CommonUtils.startArticleListActivity(getContext(),data.getType(),data.getTid());
            }
        });
        mRecycler.setAdapter(mAdapter);
        mPresenter.loadNavTags();
    }

    @Override
    public void jumpToTheTop() {
        if (tabLayout != null) {
            tabLayout.setTabSelected(0);
        }
    }

    public void initVertical(){
        tabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return mAdapter.getItemCount();
            }

            @Override
            public ITabView.TabBadge getBadge(int i) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int i) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int i) {
                return new TabView.TabTitle.Builder()
                        .setContent(mAdapter.getItems().get(i).getTitle())
                        .build();
            }

            @Override
            public int getBackground(int i) {
                return -1;
            }
        });
        leftRightLinkage();
    }

    private void leftRightLinkage() {
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tabView, int i) {
                isClickTab = true;
                selectTag(i);
            }

            @Override
            public void onTabReselected(TabView tabView, int i) {
            }
        });
    }

    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (index != firstPosition) {
                index = firstPosition;
                setChecked(index);
            }
        }
    }

    private void selectTag(int i) {
        index = i;
        mRecycler.stopScroll();
        smoothScrollToPosition(i);
    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            mRecycler.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = mRecycler.getChildAt(currentPosition - firstPosition).getTop();
            mRecycler.smoothScrollBy(0, top);
        } else {
            mRecycler.smoothScrollToPosition(currentPosition);
            needScroll = true;
        }
    }

    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = index - mLinearLayoutManager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < mRecycler.getChildCount()) {
            int top = mRecycler.getChildAt(indexDistance).getTop();
            mRecycler.smoothScrollBy(0, top);
        }
    }

    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (tabLayout == null) {
                return;
            }
            tabLayout.setTabSelected(index);
        }
        index = position;
    }

    @Override
    public void showNavTags(List<NavTagBean> navTagList) {
        mAdapter.clear();
        mAdapter.add(navTagList);
        tabLayout.removeAllTabs();
        initVertical();
        showContentView();
    }
}
