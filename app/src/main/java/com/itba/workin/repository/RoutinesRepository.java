package com.itba.workin.repository;

import static java.util.stream.Collectors.toList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.itba.workin.App;
import com.itba.workin.backend.ApiClient;
import com.itba.workin.backend.ApiResponse;
import com.itba.workin.backend.ApiRoutineService;
import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.backend.models.PagedList;
import com.itba.workin.domain.MyRoutine;

import java.util.List;

public class RoutinesRepository {

    private final ApiRoutineService apiService;

    public RoutinesRepository(App application) {
        this.apiService = ApiClient.create(application, ApiRoutineService.class);
    }

    private MyRoutine mapRoutineModelToDomain(FullRoutine routine) {
        return new MyRoutine(routine);
    }

    public LiveData<Resource<List<MyRoutine>>> getRoutines(int page, int size) {
        return new NetworkBoundResource<PagedList<FullRoutine>, List<MyRoutine>>(model ->
                model.getContent().stream()
                .map(MyRoutine::new)
                .collect(toList()))
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getRoutines(page, size);
            }
        }.asLiveData();
    }

    public LiveData<Resource<MyRoutine>> getRoutine(int routineId) {
        return new NetworkBoundResource<FullRoutine, MyRoutine>(this::mapRoutineModelToDomain)
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullRoutine>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }
}
