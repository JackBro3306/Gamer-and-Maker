package com.typedef.gam.presenter.main;


import com.typedef.gam.presenter.base.BaseRxPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class SplashPresenter extends BaseRxPresenter<SplashContract.View> implements SplashContract.Presenter {

    private static final int COUNT_DOWN_TIME = 2200;

    @Inject
    public SplashPresenter(){

    }

    @Override
    public void getData() {
        // 获得开屏图片数据
        // 1.请求网络图片
        // 2.没有网络数据使用本地数据
        mView.showSplashView("");
        startCountDowm();
    }

    private void startCountDowm() {
        addSubscribe(Flowable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mView.jumpToMain();
                    }
                }));
    }
}
