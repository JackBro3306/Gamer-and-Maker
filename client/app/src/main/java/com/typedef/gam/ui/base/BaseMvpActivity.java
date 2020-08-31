package com.typedef.gam.ui.base;

import android.widget.Toast;


import com.typedef.gam.App;
import com.typedef.gam.di.component.ActivityComponent;
import com.typedef.gam.di.component.DaggerActivityComponent;
import com.typedef.gam.di.module.ActivityModule;
import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

import javax.inject.Inject;

//import dagger.android.support.DaggerAppCompatActivity;

/**
 * 基于BaseActivity，进行dagger2注入，与presenter的绑定
 */
public abstract class BaseMvpActivity<T extends IPresenter> extends BaseActivity implements IView {
    @Inject
    protected T mPresenter;

    protected ActivityComponent getComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getModule()).build();
    }

    protected ActivityModule getModule(){
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreate() {
        super.onViewCreate();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected abstract void initInject();

    @Override
    public void showErrorMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showErrorView() {}
    @Override
    public void showLoadingView() {}
    @Override
    public void showContentView() {}

}
