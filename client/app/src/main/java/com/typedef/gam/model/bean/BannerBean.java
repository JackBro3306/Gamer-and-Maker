package com.typedef.gam.model.bean;

public class BannerBean {
    private String title;
    private String imgUrl;
    private String articleUrl;

    public BannerBean(String title, String imgUrl, String articleUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.articleUrl = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                '}';
    }
}
