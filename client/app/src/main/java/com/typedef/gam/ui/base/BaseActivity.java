package com.typedef.gam.ui.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.typedef.gam.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 最基本的Activity，进行butterknife绑定，view的初始化，activity的增加和删除
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onViewCreate();
        unbinder = ButterKnife.bind(this);
        mContext = this;
        App.getApplication().addActivity(this);
        initEventAndData();
    }

    protected void onViewCreate() {}

    protected abstract void initEventAndData();

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        App.getApplication().removeActivity(this);
    }
}
