
package com.itba.workin.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FullExercise {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("metadata")
    @Expose
    ExcerciseMetadata metadata;

    public FullExercise() {
    }

    public FullExercise(int id, String name, String detail, String type, Date date, int order, ExcerciseMetadata metadata) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.date = date;
        this.order = order;
        this.metadata = metadata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ExcerciseMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ExcerciseMetadata metadata) {
        this.metadata = metadata;
    }
}