package com.itba.workin.ui.main.fragments.favorite;

import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.main.fragments.RoutineViewModel;

public class FavoriteViewModel extends RoutineViewModel {

    public FavoriteViewModel(RoutinesRepository repository) {
        super(repository);
        this.routineGetter = repository::getFavourites;
    }
}