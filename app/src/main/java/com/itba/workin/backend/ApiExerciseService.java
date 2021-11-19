package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import com.itba.workin.backend.models.FullExercise;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseService {

    @GET("exercises/{exerciseId}")
    LiveData<ApiResponse<FullExercise>> getExercise(@Path("exerciseId") int exerciseId);
}
