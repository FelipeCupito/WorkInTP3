package com.itba.workin.domain;

import com.itba.workin.backend.models.ExcerciseMetadata;
import com.itba.workin.backend.models.FullExercise;

import java.io.Serializable;
import java.util.Objects;

public class MyExercise implements Serializable {
    private final int id;
    private final String name;
    private final String description;
    private final String excerciseUrl;
    private final boolean isRest;

    public MyExercise(FullExercise excercise) {
        this.id = excercise.getId();
        this.name = excercise.getName();
        this.description = excercise.getDetail();
        this.excerciseUrl = getUrl(excercise.getMetadata());
        this.isRest = excercise.getMetadata() != null && excercise.getMetadata().getRest() != null && excercise.getMetadata().getRest();
    }

    private String getUrl(ExcerciseMetadata metadata) {
        String url;
        if (metadata != null) {
            url = metadata.getImgUrl();
            if (url != null && url.equals("")) {
                url = null;
            }
        } else {
            url = null;
        }
        return url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getExcerciseUrl() {
        return excerciseUrl;
    }

    public boolean isRest() {
        return isRest;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyExercise that = (MyExercise) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
