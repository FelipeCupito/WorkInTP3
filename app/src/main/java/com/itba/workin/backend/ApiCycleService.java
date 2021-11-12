package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import com.itba.workin.backend.models.FullCycle;
import com.itba.workin.backend.models.FullCycleExercise;
import com.itba.workin.backend.models.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCycleService {

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<FullCycle>>> getCycles(@Path("routineId") int routineId);

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<FullCycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId);


}
