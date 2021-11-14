package com.itba.workin.ui.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.AbsentLiveData;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.viewmodel.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommunityViewModel extends RepositoryViewModel<RoutinesRepository> {

    private final static int PAGE_SIZE = 10;
    private int routinePage = 0;
    private boolean isLastRoutinePage = false;
    private final List<MyRoutine> allRoutines = new ArrayList<>();
    private final MediatorLiveData<Resource<List<MyRoutine>>> routines = new MediatorLiveData<>();
    private final MutableLiveData<Integer> routineId = new MutableLiveData<>();
    private final LiveData<Resource<MyRoutine>> routine;

    public CommunityViewModel(RoutinesRepository repository) {
        super(repository);

        routine = Transformations.switchMap(routineId, routineId -> {
            if (routineId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getRoutine(routineId);
            }
        });
    }

    public LiveData<Resource<List<MyRoutine>>> getRoutines() {
        getMoreRoutines();
        return routines;
    }

    public void getMoreRoutines() {
        if (isLastRoutinePage)
            return;

        routines.addSource(repository.getRoutines(routinePage, PAGE_SIZE), resource -> {
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

    public LiveData<Resource<MyRoutine>> getRoutine() {
        return routine;
    }

    public void setRoutineId(int routineId) {
        if ((this.routineId.getValue() != null) &&
                (routineId == this.routineId.getValue())) {
            return;
        }

        this.routineId.setValue(routineId);
    }
}