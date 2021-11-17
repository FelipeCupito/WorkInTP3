package com.itba.workin.ui.main.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.viewmodel.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class RoutineViewModel extends RepositoryViewModel<RoutinesRepository> {

    public final static int PAGE_SIZE = 8;
    private boolean firstTime = true;
    private int routinePage = 0;
    private boolean isLastRoutinePage = false;
    private final List<MyRoutine> allRoutines = new ArrayList<>();
    private final MediatorLiveData<Resource<List<MyRoutine>>> routines = new MediatorLiveData<>();
    protected RoutineGetter routineGetter;
    private boolean called = false;
    private RoutinesRepository.SORT order = RoutinesRepository.SORT.DEFAULT;
    private String search = null;

    public RoutineViewModel(RoutinesRepository repository) {
        super(repository);
    }

    public LiveData<Resource<List<MyRoutine>>> getRoutines() {
        getMoreRoutines();
        return routines;
    }

    public List<MyRoutine> getPrevRoutines() {
        return allRoutines;
    }

    public void setOrder(RoutinesRepository.SORT order) {
        this.order = order;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public RoutinesRepository.SORT getOrder() {
        return order;
    }

    public String getSearch() {
        return search;
    }

    public void restart() {
        firstTime = true;
        routinePage = 0;
    }

    public void getMoreRoutines() {
        if ((isLastRoutinePage && !firstTime) || called) {
            return;
        }
        called = true;
        if (firstTime) {
            allRoutines.clear();
        }
        firstTime = false;

        routines.addSource(routineGetter.get(routinePage, PAGE_SIZE, search, order), resource -> {
            if (resource.getStatus() == Status.SUCCESS) {
                if ((resource.getData() == null) || (resource.getData().size() == 0) || (resource.getData().size() < PAGE_SIZE))
                    isLastRoutinePage = true;

                routinePage++;

                if (resource.getData() != null)
                    allRoutines.addAll(resource.getData());
                routines.setValue(Resource.success(allRoutines));
                called = false;
            } else if (resource.getStatus() == Status.LOADING) {
                routines.setValue(resource);
            } else if (resource.getStatus() == Status.ERROR) {
                routines.setValue(resource);
                called = false;
            }
        });
    }


}
