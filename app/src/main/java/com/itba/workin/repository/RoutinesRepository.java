package com.itba.workin.repository;

import static java.util.stream.Collectors.toList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.itba.workin.App;
import com.itba.workin.backend.ApiClient;
import com.itba.workin.backend.ApiCycleService;
import com.itba.workin.backend.ApiFavouritesService;
import com.itba.workin.backend.ApiResponse;
import com.itba.workin.backend.ApiRoutineService;
import com.itba.workin.backend.ApiUserService;
import com.itba.workin.backend.models.FullCycle;
import com.itba.workin.backend.models.FullCycleExercise;
import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.backend.models.PagedList;
import com.itba.workin.domain.MyCycle;
import com.itba.workin.domain.MyCycleExercise;
import com.itba.workin.domain.MyRoutine;

import java.util.List;

public class RoutinesRepository {

    private final ApiRoutineService apiRoutineService;
    private final ApiFavouritesService apiFavouriteService;
    private final ApiUserService apiUserService;
    private final ApiCycleService apiCycleService;

    public RoutinesRepository(App application) {
        this.apiRoutineService = ApiClient.create(application, ApiRoutineService.class);
        this.apiFavouriteService = ApiClient.create(application, ApiFavouritesService.class);
        this.apiUserService = ApiClient.create(application, ApiUserService.class);
        this.apiCycleService = ApiClient.create(application, ApiCycleService.class);
    }

    public LiveData<Resource<List<MyRoutine>>> getRoutines(int page, int size, String search, SORT order) {
        return new NetworkBoundResource<PagedList<FullRoutine>, List<MyRoutine>>(model ->
                model.getContent().stream()
                .map(MyRoutine::new)
                .collect(toList()))
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiRoutineService.getRoutines(page, size, search, convertSort(order) );
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<MyCycle>>> getCycles(int routineId) {
        return new NetworkBoundResource<PagedList<FullCycle>, List<MyCycle>>(model ->
                model.getContent().stream()
                        .map(MyCycle::new)
                        .collect(toList()))
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycle>>> createCall() {
                return apiCycleService.getCycles(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<MyCycleExercise>>> getCycleExercises(int cycleId, int cycleOrder) {
        return new NetworkBoundResource<PagedList<FullCycleExercise>, List<MyCycleExercise>>(model ->
                model.getContent().stream()
                        .map(exercise -> new MyCycleExercise(exercise,cycleOrder))
                        .collect(toList()))
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycleExercise>>> createCall() {
                return apiCycleService.getCycleExercises(cycleId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<MyRoutine>>> getFavourites(int page, int size, String search, SORT order) {
        return new NetworkBoundResource<PagedList<FullRoutine>, List<MyRoutine>>(model ->
                model.getContent().stream()
                        .map(MyRoutine::new)
                        .collect(toList())) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiFavouriteService.getFavourites(page,size, search, convertSort(order) );
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<MyRoutine>>> getUserRoutines(int page, int size, String search, SORT order) {
        return new NetworkBoundResource<PagedList<FullRoutine>, List<MyRoutine>>(model ->
                model.getContent().stream()
                        .map(MyRoutine::new)
                        .collect(toList())) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiUserService.getUserRoutines(page,size, search, convertSort(order) );
            }
        }.asLiveData();
    }


    public LiveData<Resource<MyRoutine>> getRoutine(int routineId) {
        return new NetworkBoundResource<FullRoutine, MyRoutine>(MyRoutine::new)
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullRoutine>> createCall() {
                return apiRoutineService.getRoutine(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> addFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>(null)
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiFavouriteService.addFavourite(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>(null)
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiFavouriteService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }

    private String convertSort(SORT sort) {
        switch (sort) {
            case NAME:
                return "name";
            case DATE:
                return "date";
            case SCORE:
                return "score";
            case DIFFICULTY:
                return "difficulty";
            case CATEGORY:
                return "category";
        }
        return null;
    }

    public enum SORT {
        DEFAULT,
        NAME,
        DATE,
        SCORE,
        DIFFICULTY,
        CATEGORY,
    }
}
