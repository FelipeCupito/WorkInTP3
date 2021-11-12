package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<com.itba.workin.backend.ApiResponse<R>>> {
    private final Type responseType;
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @NotNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NotNull
    @Override
    public LiveData<com.itba.workin.backend.ApiResponse<R>> adapt(@NotNull Call<R> call) {
        return new LiveData<com.itba.workin.backend.ApiResponse<R>>() {
            final AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();

                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NotNull Call<R> call, @NotNull Response<R> response) {
                            postValue(new com.itba.workin.backend.ApiResponse(response));
                        }

                        @Override
                        public void onFailure(@NotNull Call<R> call, @NotNull Throwable throwable) {
                            postValue(new com.itba.workin.backend.ApiResponse(throwable));
                        }
                    });
                }
            }
        };
    }
}