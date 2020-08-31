package com.typedef.gam.model;


import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.model.http.ApiHelper;
import com.typedef.gam.model.http.info.ResponseInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * datamanager，所有数据处理的统一管理类
 */
public class DataManager {
    ApiHelper mApiHelper;

    public DataManager(ApiHelper apiHelper){
        mApiHelper = apiHelper;
    }

    public Observable<ResponseInfo<RecommendBean>> loadRecommend(){
        return mApiHelper.loadRecommend();
    }

    public Observable<ResponseInfo<List<ArticleBean>>> loadArticle(int tid, int page){
        return mApiHelper.loadArticle(tid,page);
    }

    public Observable<ResponseInfo<List<NavTagBean>>> loadNavTags(){
        return mApiHelper.loadNavTags();
    }
}
