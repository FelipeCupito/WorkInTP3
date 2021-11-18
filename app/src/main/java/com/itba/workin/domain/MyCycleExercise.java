package com.itba.workin.domain;

import com.itba.workin.backend.models.FullCycleExercise;

import java.util.Objects;

public class MyCycleExercise implements Comparable<MyCycleExercise> {
    private final int order;
    private final int duration;
    private final int repetitions;
    private final MyExercise excercise;
    private final int routineOrder;

    public MyCycleExercise(FullCycleExercise exercise, int routineOrder) {
        this.order = exercise.getOrder();
        this.duration = exercise.getDuration();
        this.repetitions = exercise.getRepetitions();
        this.excercise = new MyExercise(exercise.getExercise());
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

    public MyExercise getExcercise() {
        return excercise;
    }

    public int getRoutineOrder() {
        return routineOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCycleExercise that = (MyCycleExercise) o;
        return Objects.equals(getExcercise(), that.getExcercise());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExcercise());
    }

    @Override
    public int compareTo(MyCycleExercise o) {
        return Integer.compare(this.order, o.getOrder());
    }
}
