package com.itba.workin.repository;

import static com.itba.workin.repository.Status.ERROR;
import static com.itba.workin.repository.Status.LOADING;
import static com.itba.workin.repository.Status.SUCCESS;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itba.workin.backend.models.Error;

import org.jetbrains.annotations.NotNull;

public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final Error error;

    @Nullable
    private final T data;

    @NotNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public Error getError() {
        return error;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Error error, @Nullable T data) {
        return new Resource<>(ERROR, data, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public static void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                Log.d("LOGIN", "CARGANDO");
                break;
            case ERROR:
                Error error = resource.getError();
                assert error != null;
                Log.d("LOGIN", error.getDescription() + error.getCode() + "");
                break;
        }
    }

}
