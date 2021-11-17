package com.itba.workin.ui.main.fragments.myRoutines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.FragmentMyRoutinesBinding;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.main.fragments.RoutineFragment;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class MyRoutinesFragment extends RoutineFragment {

    private FragmentMyRoutinesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyRoutinesBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerview;
        root = binding.getRoot();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}