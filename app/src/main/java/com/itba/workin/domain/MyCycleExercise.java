package com.itba.workin.domain;

import com.itba.workin.backend.models.FullCycleExercise;

import java.util.Objects;

public class MyCycleExercise implements Comparable<MyCycleExercise> {
    private final int order;
    private final int duration;
    private final int repetitions;
    private final MyExercise exercise;
    private final int routineOrder;

    public MyCycleExercise(FullCycleExercise exercise, int routineOrder) {
        this.order = exercise.getOrder();
        this.duration = exercise.getDuration();
        this.repetitions = exercise.getRepetitions();
        this.exercise = new MyExercise(exercise.getExercise());
        this.routineOrder = routineOrder;
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

    public int getRoutineOrder() {
        return routineOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCycleExercise that = (MyCycleExercise) o;
        return Objects.equals(getExercise(), that.getExercise());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExercise());
    }

    @Override
    public int compareTo(MyCycleExercise o) {
        return Integer.compare(this.order, o.getOrder());
    }
}
