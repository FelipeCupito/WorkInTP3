
package com.itba.workin.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullCycleExercise {
    @SerializedName("exercise")
    @Expose
    private FullExercise exercise;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("repetitions")
    @Expose
    private int repetitions;

    /**
     * No args constructor for use in serialization
     */
    public FullCycleExercise() {
    }

    /**
     * @param duration
     * @param exercise
     * @param repetitions
     * @param order
     */
    public FullCycleExercise(FullExercise exercise, int order, int duration, int repetitions) {
        super();
        this.exercise = exercise;
        this.order = order;
        this.duration = duration;
        this.repetitions = repetitions;
    }

    public FullExercise getExercise() {
        return exercise;
    }

    public void setExercise(FullExercise exercise) {
        this.exercise = exercise;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
}