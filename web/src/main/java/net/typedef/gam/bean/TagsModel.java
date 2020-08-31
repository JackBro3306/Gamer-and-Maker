package net.typedef.gam.bean;

import com.google.gson.annotations.Expose;

public class TagsModel {
    @Expose
    private String type;
    @Expose
    private int tid;

    public TagsModel(String type, int tid) {
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
