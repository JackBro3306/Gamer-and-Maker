package com.typedef.gam.model.bean;

import java.util.List;

public class NavTagBean {
    private Long id;
    private String title;
    private List<TagsBean> tags;

    public NavTagBean(Long id, String title, List<TagsBean> tags) {
        this.id = id;
        this.title = title;
        this.tags = tags;
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

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "NavTagModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                '}';
    }
}
