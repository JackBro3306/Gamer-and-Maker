package com.typedef.gam.model.bean;

import java.io.Serializable;
import java.util.List;

public class ArticleBean implements Serializable {
    private Long id;
    private String title;
    private String desc;
    private String thumbUrl;
    private String articleUrl;
    private String pubTime;
    private List<TagsBean> tags;

    public ArticleBean() {
    }

    public ArticleBean(String title, String thumbUrl, String articleUrl) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.articleUrl = articleUrl;
    }

    public ArticleBean(String title, String desc, String thumbUrl, String articleUrl, String pubTime, List<TagsBean> tagsModels) {
        this.title = title;
        this.desc = desc;
        this.thumbUrl = thumbUrl;
        this.articleUrl = articleUrl;
        this.pubTime = pubTime;
        this.tags = tagsModels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", pubTime='" + pubTime + '\'' +
                ", tagsModels=" + tags +
                '}';
    }
}
