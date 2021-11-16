package com.itba.workin.ui.community;

import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.RoutineViewModel;

public class CommunityViewModel extends RoutineViewModel {

    public CommunityViewModel(RoutinesRepository repository) {
        super(repository);
        this.routineGetter = repository::getRoutines;
    }
}