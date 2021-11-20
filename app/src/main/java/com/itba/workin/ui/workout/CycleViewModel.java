package com.itba.workin.ui.workout;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.itba.workin.domain.MyCycle;
import com.itba.workin.domain.MyCycleExercise;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.repository.Status;
import com.itba.workin.viewmodel.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class CycleViewModel extends RepositoryViewModel<RoutinesRepository> {

    private final MutableLiveData<Object> current = new MutableLiveData<>();
    private PauseCountdownTimer timer;
    private MyCycle currentCycle;
    private MyCycleExercise currentExercise;
    private int cyclePosition;
    private int position;
    private boolean userStop;
    private int routineId;
    private int exerciseTime;
    private boolean firstTime = true;
    private final TreeSet<Object> order = new TreeSet<>((o1, o2) -> {
        if (o1 instanceof MyCycle && o2 instanceof MyCycle) {
            return ((MyCycle) o1).compareTo((MyCycle) o2);
        } else if (o1 instanceof MyCycleExercise && o2 instanceof MyCycleExercise) {
            int result = Integer.compare(((MyCycleExercise) o1).getCycleOrder(),((MyCycleExercise) o2).getCycleOrder());
            if (result == 0) {
                return ((MyCycleExercise) o1).compareTo((MyCycleExercise) o2);
            }
            return result;
        } else if (o1 instanceof MyCycle && o2 instanceof MyCycleExercise) {
            int result = Integer.compare(((MyCycle) o1).getOrder(),((MyCycleExercise) o2).getCycleOrder());
            if (result == 0) {
                return -1;
            }
            return result;
        } else if (o1 instanceof MyCycleExercise && o2 instanceof MyCycle) {
            int result = Integer.compare(((MyCycleExercise) o1).getCycleOrder(),((MyCycle) o2).getOrder());
            if (result == 0) {
                return 1;
            }
            return result;
        }
        throw new IllegalStateException();
    });
    private final List<MyCycle> allCycles = new ArrayList<>();
    private List<Object> allObjects;
    private final MediatorLiveData<Resource<List<Object>>> cycles = new MediatorLiveData<>();
    private boolean called = false;

    public CycleViewModel(RoutinesRepository repository) {
        super(repository);
    }

    public MyCycle getCurrentCycle() {
        return currentCycle;
    }

    public MyCycleExercise getCurrentExercise() {
        return currentExercise;
    }

    public LiveData<Resource<List<Object>>> getCycles() {
        getMoreCycles();
        return cycles;
    }

    public int getExerciseTime() {
        return exerciseTime;
    }

    public void startTimer(long millis) {
        timer = new PauseCountdownTimer(millis, 1000);
        timer.start();
    }

    public void stopTimer() {
        pauseTimer();
        currentExercise.decreaseRepetitions();
    }

    public void pauseTimer() {
        if (timer != null) {
            timer.pause();
        }
    }

    public void userPauseTimer() {
        if (!userStop) {
            pauseTimer();
            userStop = true;
        }
    }

    public void userResumeTimer() {
        if (userStop) {
            if (timer != null) {
                timer = timer.resume();
            }
            userStop = false;
        }
    }

    public void resumeTimer() {
        if (userStop) {
            return;
        }
        if (timer != null) {
            timer = timer.resume();
        }
    }

    public int getRoutineId() {
        return routineId;
    }

    public LiveData<Object> getCurrent() {
        return current;
    }

    public void advanceCurrent() {
        Object currentValue = current.getValue();
        if (currentValue instanceof MyCycle) {
            currentCycle = (MyCycle) currentValue;
            cyclePosition = position;
            int repetitions = currentCycle.getRepetitions();
            if (repetitions == 0) {
                if (allCycles.size() == currentCycle.getOrder()) {
                    current.setValue(new Finish());
                    return;
                }
                position += allCycles.get(currentCycle.getOrder()-1).getExercises().size()+1;
                current.setValue(allObjects.get(position));
            } else {
                int j = position + 1;
                for (int i = 0; i < allCycles.get(currentCycle.getOrder()-1).getExercises().size(); i++, j++) {
                    ((MyCycleExercise) allObjects.get(j)).setRepetitions(allCycles.get(currentCycle.getOrder()-1).getExercises().get(i).getRepetitions());
                    ((MyCycleExercise) allObjects.get(j)).setDuration(allCycles.get(currentCycle.getOrder()-1).getExercises().get(i).getDuration());
                }
                if (allCycles.get(currentCycle.getOrder()-1).getExercises().size() != 0) {
                    position++;
                }
                current.setValue(allObjects.get(position));
                currentCycle.decreaseRepetitions();
            }
            advanceCurrent();
        } else if (currentValue instanceof MyCycleExercise) {
            currentExercise = (MyCycleExercise) currentValue;
            currentExercise.setDuration(allCycles.get(currentCycle.getOrder()-1).getExercises().get(currentExercise.getOrder()-1).getDuration());
            int repetitions = currentExercise.getRepetitions();
            int time = currentExercise.getDuration();
            if (repetitions == 0) {
                if (currentExercise.getOrder() == allCycles.get(currentCycle.getOrder()-1).getExercises().size()) {
                    position = cyclePosition;
                    current.setValue(allObjects.get(position));
                    advanceCurrent();
                    return;
                }
                position++;
                current.setValue(allObjects.get(position));
                advanceCurrent();
            } else {
                exerciseTime = time;
                if (time != 0) {
                    startTimer(time * 1000L);
                }
                current.setValue(currentExercise);
            }
        }
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void getMoreCycles() {
        if (called || !firstTime) {
            return;
        }
        firstTime = false;
        called = true;

        cycles.addSource(repository.getCycles(routineId), cycleResource -> {
            if (cycleResource.getStatus() == Status.SUCCESS) {
                if (cycleResource.getData() != null) {
                    if (cycleResource.getData().isEmpty()) {
                        cycles.setValue(Resource.error(cycleResource.getError(), null));
                        called = false;
                        return;
                    }
                    order.addAll(cycleResource.getData());
                    for (MyCycle cycle : cycleResource.getData()) {
                        allCycles.add(new MyCycle(cycle));
                    }
                }
                AtomicInteger size = new AtomicInteger(order.size());
                for (int i = 0; i < allCycles.size(); i++) {
                    int finalI = i;
                    cycles.addSource(repository.getCycleExercises(allCycles.get(i).getId(),allCycles.get(i).getOrder()), exerciseResource -> {
                        switch (exerciseResource.getStatus()) {
                            case SUCCESS:
                                if (exerciseResource.getData() != null) {
                                    order.addAll(exerciseResource.getData());
                                    allCycles.get(finalI).setExercises(exerciseResource.getData());
                                }
                                if (size.decrementAndGet() == 0) {
                                    allObjects = new ArrayList<>(order);
                                    current.setValue(allObjects.get(0));
                                    advanceCurrent();
                                    cycles.setValue(Resource.success(allObjects));
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

    @NonNull
    @Override
    public String toString() {
        if (allCycles.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (MyCycle cycle : allCycles) {
            sb.append(cycle.toString()).append("\n");
        }
        return sb.toString();
    }

    public static class Finish {
        public Finish() {
        }
    }


    public class PauseCountdownTimer extends CountDownTimer {

        private long remainingTime;
        private long interval;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public PauseCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.interval = countDownInterval;
            this.remainingTime = countDownInterval;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainingTime = millisUntilFinished;
            MyCycleExercise exercise = (MyCycleExercise) current.getValue();
            if (exercise != null) {
                exercise.setDuration((int) remainingTime / 1000);
                current.setValue(exercise);
            }
        }

        @Override
        public void onFinish() {
            remainingTime = 0;
            stopTimer();
            advanceCurrent();
        }


        public void pause(){
            this.cancel();
        }

        public PauseCountdownTimer resume() {
            // Create a counter with last saved data (just before pause)
            PauseCountdownTimer newTimer = new PauseCountdownTimer(remainingTime,interval);
            // Start this new timer that start where old one stop
            newTimer.start();
            // Return this new timer
            return newTimer;
        }
    }
}
