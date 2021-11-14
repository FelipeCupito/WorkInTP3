package com.itba.workin.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.itba.workin.App;
import com.itba.workin.backend.ApiClient;
import com.itba.workin.backend.ApiResponse;
import com.itba.workin.backend.ApiRoutineService;
import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.backend.models.PagedList;

public class RoutinesRepository {

    private final ApiRoutineService apiService;

    public RoutinesRepository(App application) {
        this.apiService = ApiClient.create(application, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<FullRoutine>>> getRoutines() {
        return new NetworkBoundResource<PagedList<FullRoutine>, PagedList<FullRoutine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullRoutine>> getRoutine(int routineId) {
        return new NetworkBoundResource<FullRoutine, FullRoutine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullRoutine>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }
}
