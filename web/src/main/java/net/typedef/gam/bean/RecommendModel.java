package net.typedef.gam.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

public class RecommendModel {
    @Expose
    private List<BannerModel> banners;
    @Expose
    private List<ArticleModel> articles;

    public RecommendModel(List<BannerModel> banners, List<ArticleModel> articles) {
        this.banners = banners;
        this.articles = articles;
    }

    public List<BannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerModel> banners) {
        this.banners = banners;
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleModel> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "RecommendModel{" +
                "banners=" + banners +
                ", articles=" + articles +
                '}';
    }
}
