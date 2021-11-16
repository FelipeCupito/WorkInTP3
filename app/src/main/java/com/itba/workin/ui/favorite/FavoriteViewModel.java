package com.itba.workin.ui.favorite;

import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.RoutineViewModel;

public class FavoriteViewModel extends RoutineViewModel {

    public FavoriteViewModel(RoutinesRepository repository) {
        super(repository);
        this.routineGetter = repository::getFavourites;
    }
}