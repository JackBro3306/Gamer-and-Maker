package com.typedef.gam.presenter.recommend;

import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.presenter.base.IPresenter;
import com.typedef.gam.presenter.base.IView;

import java.util.List;

public interface RecommendContract {
    interface View extends IView{
        void showRecommend(RecommendBean recommendBean);
        void showMore(List<ArticleBean> articleBeans);
    }

    interface Presenter extends IPresenter<View>{
        public void loadData();
        public void loadMoreData();
    }

}
