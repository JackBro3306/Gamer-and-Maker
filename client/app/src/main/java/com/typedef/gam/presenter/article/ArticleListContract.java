package com.typedef.gam.presenter.article;

import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

import java.util.List;

public interface ArticleListContract {
    interface View extends IView {
        void showData(List<ArticleBean> articles);
        void showMore(List<ArticleBean> articles);
    }

    interface Presenter extends IPresenter<View> {
        void loadData(int tid);
        void loadMoreData();
    }
}
