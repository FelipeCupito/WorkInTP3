package com.itba.workin.ui.main.fragments.myRoutines;

import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.main.fragments.RoutineViewModel;

public class MyRoutinesViewModel extends RoutineViewModel {

    public MyRoutinesViewModel(RoutinesRepository repository) {
        super(repository);
        this.routineGetter = repository::getUserRoutines;
    }
}