package com.itba.workin.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.itba.workin.App;
import com.itba.workin.backend.ApiClient;
import com.itba.workin.backend.ApiResponse;
import com.itba.workin.backend.ApiUserService;
import com.itba.workin.backend.models.Credentials;
import com.itba.workin.backend.models.Token;
import com.itba.workin.backend.models.FullUser;

public class UserRepository {

    private final ApiUserService apiService;

    public UserRepository(App app) {
        this.apiService = ApiClient.create(app, ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullUser>> getCurrentUser() {
        return new NetworkBoundResource<FullUser, FullUser>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullUser>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }
}