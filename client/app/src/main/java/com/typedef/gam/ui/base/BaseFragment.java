package com.typedef.gam.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mRoot;
    protected Unbinder unbinder;

    private boolean isLoaded = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,mRoot);
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isLoaded){
            onLazyInitView();
            isLoaded = true;
        }
    }


    // androidx的懒加载
    public void onLazyInitView() {
        initEventAndData();
    }

    @Override
    public void onDestroyView() {
        if(unbinder != Unbinder.EMPTY)
            unbinder.unbind();
        isLoaded =false;
        super.onDestroyView();
    }

    protected abstract void initEventAndData();

    protected abstract int getLayoutId();
}
