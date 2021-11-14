package com.itba.workin.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.itba.workin.backend.ApiResponse;

import java.util.function.Function;

public abstract class NetworkBoundResource<Model, Domain> {

    private final MediatorLiveData<Resource<Domain>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(Function<Model, Domain> mapModelToDomain) {

        result.setValue(Resource.loading(null));

        LiveData<ApiResponse<Model>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);

            if (response.getError() != null) {
                onFetchFailed();
                setValue(Resource.error(response.getError(), null));
            } else {
                Model model = processResponse(response);
                if (mapModelToDomain != null) {
                    Domain data = mapModelToDomain.apply(model);
                    setValue(Resource.success(data));
                } else {
                    setValue(Resource.success((Domain) model));
                }
            }
        });
    }

    @MainThread
    private void setValue(Resource<Domain> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    protected void onFetchFailed() {
    }

    @WorkerThread
    protected Model processResponse(ApiResponse<Model> response)
    {
        return response.getData();
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<Model>> createCall();

    public LiveData<Resource<Domain>> asLiveData() {
        return result;
    }
}
