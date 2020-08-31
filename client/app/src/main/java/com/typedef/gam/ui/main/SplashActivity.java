package com.typedef.gam.ui.main;

import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.typedef.gam.R;
import com.typedef.gam.presenter.main.SplashContract;
import com.typedef.gam.presenter.main.SplashPresenter;
import com.typedef.gam.ui.base.BaseMvpActivity;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.View{

    @BindView(R.id.im_splash)
    ImageView im_splash;

    @Override
    protected void initInject() {
        // 进行dagger2注入
        getComponent().inject(this);      // inject
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void showSplashView(String imgUrl) {
        Glide.with(this).load(imgUrl)
                .placeholder(R.mipmap.welcome_bg)
                .into(im_splash);
        im_splash.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
    }

    @Override
    public void jumpToMain() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
