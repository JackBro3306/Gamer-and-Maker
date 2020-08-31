package net.typedef.gam.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

public class NavTagModel {
    private Long id;
    @Expose
    private String title;
    @Expose
    private List<TagsModel> tags;

    public NavTagModel(Long id, String title, List<TagsModel> tags) {
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

    public List<TagsModel> getTags() {
        return tags;
    }

    public void setTags(List<TagsModel> tags) {
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
