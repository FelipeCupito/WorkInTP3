package com.itba.workin.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineMetadata {
    @SerializedName("routineUrl")
    @Expose
    private String routineUrl;

    public RoutineMetadata() {
    }

    public RoutineMetadata(String routineUrl) {
        super();
        this.routineUrl = routineUrl;
    }

    public String getRoutineUrl() {
        return routineUrl;
    }

    public void setRoutineUrl(String routineUrl) {
        this.routineUrl = routineUrl;
    }
}
