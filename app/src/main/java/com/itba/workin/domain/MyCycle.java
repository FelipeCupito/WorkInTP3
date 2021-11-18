package com.itba.workin.domain;

import com.itba.workin.backend.models.FullCycle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyCycle implements Comparable<MyCycle>, Serializable {
    private final int id;
    private final String name;
    private final String type;
    private final int order;
    private int repetitions;
    private final List<MyCycleExercise> exercises = new ArrayList<>();

    public MyCycle(FullCycle cycle) {
        this.id = cycle.getId();
        this.name = cycle.getName();
        this.type = cycle.getType();
        this.order = cycle.getOrder();
        this.repetitions = cycle.getRepetitions();
    }

    public MyCycle(MyCycle cycle) {
        this.id = cycle.getId();
        this.name = cycle.getName();
        this.type = cycle.getType();
        this.order = cycle.getOrder();
        this.repetitions = cycle.getRepetitions();
    }

    public List<MyCycleExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<MyCycleExercise> exercises) {
        for (MyCycleExercise exercise : exercises) {
            this.exercises.add(new MyCycleExercise(exercise));
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getOrder() {
        return order;
    }

    public void decreaseRepetitions() {
        repetitions--;
    }

    public int getRepetitions() {
        return repetitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCycle myCycle = (MyCycle) o;
        return getId() == myCycle.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public int compareTo(MyCycle o) {
        return Integer.compare(this.order, o.getOrder());
    }
}