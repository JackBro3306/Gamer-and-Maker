package com.typedef.gam.model.http;


import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.model.http.info.ResponseInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * api接口
 */
public interface ApiHelper {
    Observable<ResponseInfo<RecommendBean>> loadRecommend();
    Observable<ResponseInfo<List<ArticleBean>>> loadArticle(int tid, int page);
    Observable<ResponseInfo<List<NavTagBean>>> loadNavTags();
}
