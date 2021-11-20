package com.itba.workin.ui.main.fragments.myRoutines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.itba.workin.App;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.main.fragments.RoutineFragment;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class MyRoutinesFragment extends RoutineFragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App app = (App) requireActivity().getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        routineViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MyRoutinesViewModel.class);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        // se desactiva porque esta roto en la api
        super.onPrepareOptionsMenu(menu);
        categoryOpt.setVisible(false);
    }
}