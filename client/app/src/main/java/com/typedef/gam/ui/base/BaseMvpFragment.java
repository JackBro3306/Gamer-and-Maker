package com.typedef.gam.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.typedef.gam.App;
import com.typedef.gam.di.component.DaggerFragmentComponent;
import com.typedef.gam.di.component.FragmentComponent;
import com.typedef.gam.di.module.FragmentModule;
import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

import javax.inject.Inject;

public abstract class BaseMvpFragment<T extends IPresenter> extends BaseFragment implements IView {

    @Inject
    protected T mPresenter;

    // 获得FragmentComponent
    protected FragmentComponent getComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule()).build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInject();
        if(mPresenter!=null){
            mPresenter.attachView(this);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    protected abstract void initInject();

}
