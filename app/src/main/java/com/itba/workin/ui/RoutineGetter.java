package com.itba.workin.ui;

import androidx.lifecycle.LiveData;

import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.Resource;

import java.util.List;

public interface RoutineGetter {
    public LiveData<Resource<List<MyRoutine>>> get(int page, int size);
}
