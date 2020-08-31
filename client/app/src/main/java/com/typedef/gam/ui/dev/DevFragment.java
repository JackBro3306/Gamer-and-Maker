package com.typedef.gam.ui.dev;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.presenter.dev.DevContract;
import com.typedef.gam.presenter.dev.DevPresenter;
import com.typedef.gam.ui.article.ArticleListFragment;
import com.typedef.gam.ui.base.BaseFragment;
import com.typedef.gam.ui.base.BaseMvpFragment;
import com.typedef.gam.ui.base.RootFragment;
import com.typedef.gam.ui.my.MyFragment;
import com.typedef.gam.ui.recommend.RecommendFragment;
import com.typedef.gam.ui.tags.TagsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevFragment extends RootFragment<DevPresenter> implements DevContract.View {

    private List<TagsBean> tagsBeans = new ArrayList<TagsBean>();

    private List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    public DevFragment() {
        // Required empty public constructor
        tagsBeans.add(new TagsBean("程序·引擎",26));
        tagsBeans.add(new TagsBean("美术",27));
        tagsBeans.add(new TagsBean("策划",25));
        tagsBeans.add(new TagsBean("拆解分析",24));
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        fragments.clear();
        for (TagsBean tagsBean : tagsBeans) {
            fragments.add(ArticleListFragment.newInstance(tagsBean.getType(),tagsBean.getTid()));
        }

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);

        // 自定义TabLayout.Tab
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            tab.setText(tagsBeans.get(i).getType());
        }
    }

    @Override
    public void jumpToTheTop() {
        ((RootFragment)fragments.get(mViewPager.getCurrentItem())).jumpToTheTop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dev;
    }

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

}
