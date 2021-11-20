package com.itba.workin.domain;

import androidx.annotation.NonNull;

import com.itba.workin.backend.models.FullCycleExercise;

import java.io.Serializable;
import java.util.Objects;

public class MyCycleExercise implements Comparable<MyCycleExercise>, Serializable {
    private final int order;
    private int duration;
    private int repetitions;
    private final MyExercise exercise;
    private final int cycleOrder;

    public MyCycleExercise(FullCycleExercise exercise, int cycleOrder) {
        this.order = exercise.getOrder();
        this.duration = exercise.getDuration();
        this.repetitions = exercise.getRepetitions();
        this.exercise = new MyExercise(exercise.getExercise());
        this.cycleOrder = cycleOrder;
    }

    public MyCycleExercise(MyCycleExercise exercise) {
        this.order = exercise.order;
        this.duration = exercise.duration;
        this.repetitions = exercise.repetitions;
        this.exercise = new MyExercise(exercise.getExercise());
        this.cycleOrder = exercise.cycleOrder;
    }

    public int getOrder() {
        return order;
    }

    public int getDuration() {
        return duration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public MyExercise getExercise() {
        return exercise;
    }

    public int getCycleOrder() {
        return cycleOrder;
    }

    public void decreaseDuration() {
        duration--;
    }

    public void decreaseRepetitions() {
        repetitions--;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCycleExercise exercise = (MyCycleExercise) o;
        return getOrder() == exercise.getOrder() && getCycleOrder() == exercise.getCycleOrder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getCycleOrder());
    }

    @Override
    public int compareTo(MyCycleExercise o) {
        return Integer.compare(this.order, o.getOrder());
    }

    @NonNull
    @Override
    public String toString() {
        return exercise.toString();
    }
}
