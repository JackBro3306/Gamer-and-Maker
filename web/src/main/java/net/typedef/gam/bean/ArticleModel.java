package net.typedef.gam.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ArticleModel {
    @Expose
    private String title;
    @Expose
    private String desc;
    @Expose
    private String thumbUrl;
    @Expose
    private String articleUrl;
    @Expose
    private String pubTime;
    @Expose
    private List<TagsModel> tags;

    public ArticleModel(String title, String desc, String thumbUrl, String articleUrl, String pubTime, List<TagsModel> tags) {
        this.title = title;
        this.desc = desc;
        this.thumbUrl = thumbUrl;
        this.articleUrl = articleUrl;
        this.pubTime = pubTime;
        this.tags = tags;
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

    public List<TagsModel> getTagsModels() {
        return tags;
    }

    public void setTagsModels(List<TagsModel> tags) {
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