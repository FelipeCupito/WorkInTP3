package com.itba.workin.domain;

import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.backend.models.RoutineMetadata;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MyRoutine implements Serializable {

    private final int id;
    private final Date date;
    private final String name;
    private final String detail;
    private final boolean isPublic;
    private final int difficulty;
    private final int score;
    private final String category;
    private final String routineUrl;
    private final String userName;

    public MyRoutine(FullRoutine routine) {
        this.id = routine.getId();
        this.date = routine.getDate();
        this.name = routine.getName();
        this.detail = routine.getDetail();
        this.isPublic = routine.isIsPublic();
        this.difficulty = getDifficulty(routine.getDifficulty());
        this.score = routine.getScore();
        this.category = routine.getCategory().getName();
        this.routineUrl = getUrl(routine.getMetadata());
        this.userName = routine.getUser() == null ? null : routine.getUser().getUsername();
    }

    private String getUrl(RoutineMetadata metadata) {
        String url;
        if (metadata != null) {
            url = metadata.getRoutineUrl();
            if (url != null && url.equals("")) {
                url = null;
            }
        } else {
            url = null;
        }
        return url;
    }

    private int getDifficulty(String difficulty) {
        switch (difficulty) {
            case "rookie":
                return 1;
            case "intermediate":
                return 2;
            case "expert":
                return 3;
            default:
                return 0;
        }
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }

    public String getRoutineUrl() {
        return routineUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyRoutine myRoutine = (MyRoutine) o;
        return getId() == myRoutine.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "MyRoutine{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", isPublic=" + isPublic +
                ", difficulty=" + difficulty +
                ", score=" + score +
                ", category='" + category + '\'' +
                ", routineUrl='" + routineUrl + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
