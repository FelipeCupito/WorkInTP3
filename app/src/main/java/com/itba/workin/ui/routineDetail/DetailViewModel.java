package com.itba.workin.ui.routineDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.viewmodel.RepositoryViewModel;

public class DetailViewModel extends RepositoryViewModel<RoutinesRepository> {

    private final MediatorLiveData<Resource<MyRoutine>> routine = new MediatorLiveData<>();
    private MyRoutine oldRoutine;
    private boolean called = false, firstTime = true;
    private int routineId;

    public DetailViewModel(RoutinesRepository repository) {
        super(repository);
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public MyRoutine getOldRoutine() {
        return oldRoutine;
    }

    public LiveData<Resource<MyRoutine>> getRoutine() {
        getRoutineApi();
        return routine;
    }

    public void restart() {
        firstTime = true;
    }

    public void getRoutineApi() {
        if (!firstTime || called) {
            return;
        }
        firstTime = false;
        called = true;

        routine.addSource(repository.getRoutine(routineId), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    routine.setValue(r);
                case ERROR:
                    routine.setValue(r);
                    called = false;
                case SUCCESS:
                    if (r.getData() != null) {
                        oldRoutine = r.getData();
                        routine.setValue(Resource.success(oldRoutine));
                        called = false;
                    }
            }
        });
    }
}
