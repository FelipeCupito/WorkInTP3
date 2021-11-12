package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import com.itba.workin.backend.models.FullImage;
import com.itba.workin.backend.models.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseImageService {
    @GET("exercises/{exerciseId}/images")
    LiveData<ApiResponse<PagedList<FullImage>>> getExerciseImages(@Path("exerciseId") int exerciseId);
}
