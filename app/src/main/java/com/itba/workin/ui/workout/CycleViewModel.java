package com.itba.workin.ui.workout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.itba.workin.domain.MyCycle;
import com.itba.workin.domain.MyCycleExercise;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.viewmodel.RepositoryViewModel;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class CycleViewModel extends RepositoryViewModel<RoutinesRepository> {

    private int routineId;
    private final TreeSet<Object> allCycles = new TreeSet<>((o1, o2) -> {
        if (o1 instanceof MyCycle && o2 instanceof MyCycle) {
            return ((MyCycle) o1).compareTo((MyCycle) o2);
        } else if (o1 instanceof MyCycleExercise && o2 instanceof MyCycleExercise) {
            int result = Integer.compare(((MyCycleExercise) o1).getRoutineOrder(),((MyCycleExercise) o2).getRoutineOrder());
            if (result == 0) {
                return ((MyCycleExercise) o1).compareTo((MyCycleExercise) o2);
            }
            return result;
        } else if (o1 instanceof MyCycle && o2 instanceof MyCycleExercise) {
            int result = Integer.compare(((MyCycle) o1).getOrder(),((MyCycleExercise) o2).getRoutineOrder());
            if (result == 0) {
                return -1;
            }
            return result;
        } else if (o1 instanceof MyCycleExercise && o2 instanceof MyCycle) {
            int result = Integer.compare(((MyCycleExercise) o1).getRoutineOrder(),((MyCycle) o2).getOrder());
            if (result == 0) {
                return 1;
            }
            return result;
        }
        throw new IllegalStateException();
    });

    private final MediatorLiveData<Resource<TreeSet<Object>>> cycles = new MediatorLiveData<>();
    private boolean called = false;

    public CycleViewModel(RoutinesRepository repository) {
        super(repository);
    }

    public LiveData<Resource<TreeSet<Object>>> getCycles() {
        if (allCycles.isEmpty()) {
            getMoreCycles();
        }
        return cycles;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void getMoreCycles() {
        if (called) {
            return;
        }
        called = true;

        cycles.addSource(repository.getCycles(routineId), cycleResource -> {
            if (cycleResource.getStatus() == Status.SUCCESS) {
                if (cycleResource.getData() != null) {
                    if (cycleResource.getData().isEmpty()) {
                        cycles.setValue(Resource.error(cycleResource.getError(), null));
                        called = false;
                        return;
                    }
                    allCycles.addAll(cycleResource.getData());
                }
                AtomicInteger size = new AtomicInteger(allCycles.size());
                for (Object cycle : allCycles) {
                    cycles.addSource(repository.getCycleExercises(((MyCycle) cycle).getId(),((MyCycle) cycle).getOrder()), exerciseResource -> {
                        switch (exerciseResource.getStatus()) {
                            case SUCCESS:
                                if (exerciseResource.getData() != null) {
                                    allCycles.addAll(exerciseResource.getData());
                                }
                                if (size.decrementAndGet() == 0) {
                                    cycles.setValue(Resource.success(allCycles));
                                }
                                break;
                            case ERROR:
                                cycles.setValue(Resource.error(exerciseResource.getError(), null));
                                called = false;
                                break;
                        }
                    });
                }
            } else if (cycleResource.getStatus() == Status.LOADING) {
                cycles.setValue(Resource.loading(null));
            } else if (cycleResource.getStatus() == Status.ERROR) {
                cycles.setValue(Resource.error(cycleResource.getError(), null));
                called = false;
            }
        });
    }
}
