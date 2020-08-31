package com.typedef.gam.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.typedef.gam.App;
import com.typedef.gam.R;
import com.typedef.gam.presenter.main.MainContract;
import com.typedef.gam.presenter.main.MainPresenter;
import com.typedef.gam.ui.base.BaseFragment;
import com.typedef.gam.ui.base.BaseMvpActivity;
import com.typedef.gam.ui.base.RootFragment;
import com.typedef.gam.ui.dev.DevFragment;
import com.typedef.gam.ui.my.MyFragment;
import com.typedef.gam.ui.recommend.RecommendFragment;
import com.typedef.gam.ui.tags.TagsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View{

    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    private List<Fragment> fragments = new ArrayList<>();
    String[] tabTitles = new String[]{"推荐", "研发", "专栏", "我"};

    int[] tabIcon = new int[]{
            R.mipmap.ic_nav_recommend, R.mipmap.ic_nav_dev,
            R.mipmap.ic_nav_tags, R.mipmap.ic_nav_my
    };
    int[] tabIconSelect = new int[]{
            R.mipmap.ic_nav_recommend_hint, R.mipmap.ic_nav_dev_hint,
            R.mipmap.ic_nav_tags_hint, R.mipmap.ic_nav_my_hint
    };

    private long exitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        fragments.add(new RecommendFragment());
        fragments.add(new DevFragment());
        fragments.add(new TagsFragment());
        fragments.add(new MyFragment());

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),
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
            tab.setCustomView(R.layout.item_tabtitle);
            TextView textView = tab.getCustomView().findViewById(R.id.tv_tab);
            textView.setText(tabTitles[i]);
            if (i == 0) textView.setSelected(true);
            ImageView imageView = tab.getCustomView().findViewById(R.id.iv_tab);
            if (i == 0) {
                imageView.setImageResource(tabIconSelect[i]);
            } else {
                imageView.setImageResource(tabIcon[i]);
            }
        }

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_tab);
                TextView textView = tab.getCustomView().findViewById(R.id.tv_tab);
                textView.setSelected(true);
                imageView.setImageResource(tabIconSelect[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_tab);
                TextView textView = tab.getCustomView().findViewById(R.id.tv_tab);
                textView.setSelected(false);
                imageView.setImageResource(tabIcon[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }
    @OnClick({R.id.main_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_floating_action_btn:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }

    private void jumpToTheTop() {
        if(mViewPager.getCurrentItem() != fragments.size()-1){
            ((RootFragment)fragments.get(mViewPager.getCurrentItem())).jumpToTheTop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp(){
        if(System.currentTimeMillis() - exitTime > 2000){
            Toast.makeText(mContext, "连按两次退出哦~", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else{
            finish();
            App.exitApp();
        }
    }
}
