package com.itba.workin.ui;

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

    public final static int PAGE_SIZE = 10;
    private boolean firstTime = true;
    private int routinePage = 0;
    private boolean isLastRoutinePage = false;
    private final List<MyRoutine> allRoutines = new ArrayList<>();
    private final MediatorLiveData<Resource<List<MyRoutine>>> routines = new MediatorLiveData<>();
    protected RoutineGetter routineGetter;

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

    public void setRoutineGetter(RoutineGetter routineGetter) {
        this.routineGetter = routineGetter;
    }

    public void restart() {
        firstTime = true;
        routinePage = 0;
    }

    public void getMoreRoutines() {
        if (isLastRoutinePage && !firstTime) {
            return;
        }
        if (firstTime) {
            allRoutines.clear();
        }
        firstTime = false;

        routines.addSource(routineGetter.get(routinePage, PAGE_SIZE), resource -> {
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
