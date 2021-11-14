package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.backend.models.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getRoutines(@Query("page") int page, @Query("size") int size);

    @GET("routines?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getRoutinesByDiff(@Query("difficulty") String difficulty);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<FullRoutine>> getRoutine(@Path("routineId") int routineId);


}
