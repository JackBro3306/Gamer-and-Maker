package com.typedef.gam.presenter.main;


import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

public interface MainContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
        void checkPremission();
        void getSearchTags();
    }
}
