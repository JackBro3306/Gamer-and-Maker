package com.typedef.gam.presenter.dev;

import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

public interface DevContract {
    interface View extends IView{

    }

    interface Presenter extends IPresenter<View>{

    }
}
