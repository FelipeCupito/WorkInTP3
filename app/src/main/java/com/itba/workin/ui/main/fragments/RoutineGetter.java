package com.itba.workin.ui.main.fragments;

import androidx.lifecycle.LiveData;

import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.RoutinesRepository;

import java.util.List;

public interface RoutineGetter {
    LiveData<Resource<List<MyRoutine>>> get(int page, int size, String search, RoutinesRepository.SORT order);
}
