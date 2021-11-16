package com.itba.workin.domain;

import com.itba.workin.backend.models.FullCycleExercise;

import java.util.Objects;

public class MyCycleExcercise implements Comparable<MyCycleExcercise> {
    private final int order;
    private final int duration;
    private final int repetitions;
    private final MyExcercise excercise;

    public MyCycleExcercise(FullCycleExercise exercise) {
        this.order = exercise.getOrder();
        this.duration = exercise.getDuration();
        this.repetitions = exercise.getRepetitions();
        this.excercise = new MyExcercise(exercise.getExercise());
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

    public MyExcercise getExcercise() {
        return excercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCycleExcercise that = (MyCycleExcercise) o;
        return Objects.equals(getExcercise(), that.getExcercise());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExcercise());
    }

    @Override
    public int compareTo(MyCycleExcercise o) {
        return Integer.compare(this.order, o.getOrder());
    }
}
