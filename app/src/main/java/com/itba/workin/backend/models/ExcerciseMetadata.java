package com.itba.workin.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExcerciseMetadata {
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("isRest")
    @Expose
    private Boolean isRest;

    public ExcerciseMetadata() {
    }

    public ExcerciseMetadata(String imgUrl, Boolean isRest) {
        super();
        this.imgUrl = imgUrl;
        this.isRest = isRest;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getRest() {
        return isRest;
    }

    public void setRest(Boolean rest) {
        isRest = rest;
    }
}
