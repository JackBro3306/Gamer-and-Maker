package com.typedef.gam.model.bean;

import java.util.List;

public class RecommendBean {
    private List<BannerBean> banners;
    private List<ArticleBean> articles;

    public RecommendBean(List<BannerBean> banners, List<ArticleBean> articles) {
        this.banners = banners;
        this.articles = articles;
    }

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "RecommendModel{" +
                "banners=" + banners +
                ", news=" + articles +
                '}';
    }
}