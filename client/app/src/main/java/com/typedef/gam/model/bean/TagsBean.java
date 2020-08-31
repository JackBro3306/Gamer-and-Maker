package com.typedef.gam.model.bean;

import java.io.Serializable;

public class TagsBean implements Serializable {
    private String type;
    private int tid;

    public TagsBean(String type, int tid) {
        this.type = type;
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "TagsModel{" +
                "type='" + type + '\'' +
                ", tid=" + tid +
                '}';
    }
}
