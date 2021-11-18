package com.itba.workin.ui.main.fragments.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.itba.workin.App;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.main.fragments.RoutineFragment;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class CommunityFragment extends RoutineFragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App app = (App) requireActivity().getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        routineViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CommunityViewModel.class);

        return super.onCreateView(inflater,container,savedInstanceState);
    }
}