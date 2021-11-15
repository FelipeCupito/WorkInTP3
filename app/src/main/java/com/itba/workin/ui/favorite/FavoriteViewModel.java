package com.itba.workin.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.AbsentLiveData;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.viewmodel.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends RepositoryViewModel<RoutinesRepository> {

    private final static int PAGE_SIZE = 10;
    private int routinePage = 0;
    private boolean isLastRoutinePage = false;
    private final List<MyRoutine> allRoutines = new ArrayList<>();
    private final MediatorLiveData<Resource<List<MyRoutine>>> routines = new MediatorLiveData<>();

    public FavoriteViewModel(RoutinesRepository repository) {
        super(repository);
    }

    public LiveData<Resource<List<MyRoutine>>> getRoutines() {
        getMoreRoutines();
        return routines;
    }

    public void getMoreRoutines() {
        if (isLastRoutinePage)
            return;

        routines.addSource(repository.getFavourites(routinePage, PAGE_SIZE), resource -> {
            if (resource.getStatus() == Status.SUCCESS) {
                if ((resource.getData() == null) || (resource.getData().size() == 0) || (resource.getData().size() < PAGE_SIZE))
                    isLastRoutinePage = true;

                routinePage++;

                if (resource.getData() != null)
                    allRoutines.addAll(resource.getData());
                routines.setValue(Resource.success(allRoutines));
            } else if (resource.getStatus() == Status.LOADING) {
                routines.setValue(resource);
            }
        });
    }
}