package com.typedef.gam.model.http;


import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.model.http.info.ResponseInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RetrofitHelper implements ApiHelper {
    private Api api;

    @Inject
    public RetrofitHelper(Api api){
        this.api = api;
    }

    @Override
    public Observable<ResponseInfo<RecommendBean>> loadRecommend() {
        return api.getRecommend();
    }

    @Override
    public Observable<ResponseInfo<List<ArticleBean>>> loadArticle(int tid, int page) {
        return api.getArticles(tid,page);
    }

    @Override
    public Observable<ResponseInfo<List<NavTagBean>>> loadNavTags() {
        return api.getNavTags();
    }
}
