package com.itba.workin.ui.exerciseDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.itba.workin.domain.MyExercise;
import com.itba.workin.repository.ExerciseRepository;
import com.itba.workin.repository.Resource;
import com.itba.workin.viewmodel.RepositoryViewModel;

public class ExerciseViewModel extends RepositoryViewModel<ExerciseRepository> {

    private final MediatorLiveData<Resource<MyExercise>> exercise = new MediatorLiveData<>();
    private MyExercise oldExercise;
    private boolean called = false, firstTime = true;
    private int exerciseId;

    public ExerciseViewModel(ExerciseRepository repository) {
        super(repository);
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public MyExercise getOldExercise() {
        return oldExercise;
    }

    public LiveData<Resource<MyExercise>> getExercise() {
        getExerciseApi();
        return exercise;
    }

    public void restart() {
        firstTime = true;
    }

    public void getExerciseApi() {
        if (!firstTime || called) {
            return;
        }
        firstTime = false;
        called = true;

        exercise.addSource(repository.getExercise(exerciseId), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    exercise.setValue(r);
                case ERROR:
                    exercise.setValue(r);
                    called = false;
                case SUCCESS:
                    if (r.getData() != null) {
                        oldExercise = r.getData();
                        exercise.setValue(Resource.success(oldExercise));
                        called = false;
                    }
            }
        });
    }
}
