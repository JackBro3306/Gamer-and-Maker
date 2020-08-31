package com.typedef.gam.presenter.main;

import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

public interface SplashContract {
    interface View extends IView {
        void showSplashView(String imgUrl);
        void jumpToMain();
    }

    interface Presenter extends IPresenter<View> {
        void getData();
    }
}
