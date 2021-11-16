package com.itba.workin.ui.myRoutines;

import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.RoutineViewModel;

public class MyRoutinesViewModel extends RoutineViewModel {

    public MyRoutinesViewModel(RoutinesRepository repository) {
        super(repository);
        this.routineGetter = repository::getUserRoutines;
    }
}