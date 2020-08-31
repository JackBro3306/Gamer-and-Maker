package com.typedef.gam.model.http;

import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.NavTagBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.model.http.info.ResponseInfo;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String HOST = "http://www.sjypr.com:8383/GAM/api/";
    int CODE_OK = 200;
    int CODE_ERROR_PARAMETERS = 501;
    /**
     * 获取推荐列表
     *
     * @return
     */
    @GET("YZInfo/getRecommend")
    Observable<ResponseInfo<RecommendBean>> getRecommend();

    /**
     * 获取标签
     * @return
     */
    @GET("YZInfo/getTags")
    Observable<ResponseInfo<List<NavTagBean>>> getNavTags();

    /**
     * 获取文章列表
     * @return
     */
    @GET("YZInfo/getNews/tid/{tid}/page/{page}")
    Observable<ResponseInfo<List<ArticleBean>>> getArticles(@Path("tid")int tid, @Path("page") int page);
}
