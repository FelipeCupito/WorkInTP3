package com.itba.workin.backend;

import androidx.lifecycle.LiveData;

import com.itba.workin.backend.models.Review;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiReviewService {

    @POST("reviews/{routineId}/")
    LiveData<ApiResponse<Void>> addReview(@Path("routineId") int routineId, @Body Review review);
}
