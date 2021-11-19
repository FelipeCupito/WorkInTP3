package com.itba.workin.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.itba.workin.App;
import com.itba.workin.backend.ApiClient;
import com.itba.workin.backend.ApiExerciseService;
import com.itba.workin.backend.ApiResponse;
import com.itba.workin.backend.models.FullExercise;
import com.itba.workin.domain.MyExercise;

public class ExerciseRepository {

    private final ApiExerciseService apiService;

    public ExerciseRepository(App app) {
        this.apiService = ApiClient.create(app, ApiExerciseService.class);
    }

    public LiveData<Resource<MyExercise>> getExercise(int exerciseId) {
        return new NetworkBoundResource<FullExercise, MyExercise>(MyExercise::new)
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullExercise>> createCall() {
                return apiService.getExercise(exerciseId);
            }
        }.asLiveData();
    }
}
