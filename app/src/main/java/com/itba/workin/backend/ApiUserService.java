package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import com.itba.workin.backend.models.Credentials;
import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.backend.models.PagedList;
import com.itba.workin.backend.models.Token;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUserService {

    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current/routines/")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getUserRoutines(@Query("page") int page, @Query("size") int size, @Query("search") String search, @Query("orderBy") String order);

}
