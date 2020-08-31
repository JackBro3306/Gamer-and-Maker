package com.typedef.gam.presenter.tags;

import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

import java.util.List;

public interface TagsContract {
    interface View extends IView{
        void showNavTags(List<NavTagBean> navTagList);
    }

    interface Presenter extends IPresenter<View>{
        void loadNavTags();
    }
}
