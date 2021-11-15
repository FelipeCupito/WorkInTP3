package com.itba.workin.domain;

import com.itba.workin.backend.models.FullCycle;

import java.util.Objects;

public class MyCycle implements Comparable<MyCycle>{
    private final int id;
    private final String name;
    private final String type;
    private final int order;
    private final int repetitions;

    public MyCycle(FullCycle cycle) {
        this.id = cycle.getId();
        this.name = cycle.getName();
        this.type = cycle.getType();
        this.order = cycle.getOrder();
        this.repetitions = cycle.getRepetitions();
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