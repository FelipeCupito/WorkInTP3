package com.itba.workin.ui.workout.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.WorkoutDetailedBinding;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.utils.GridSpacingItemDecoration;
import com.itba.workin.ui.workout.CycleAdapter;
import com.itba.workin.ui.workout.CycleViewModel;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailedFragment extends Fragment {
    private WorkoutDetailedBinding binding;
    private CycleViewModel cycleViewModel;
    private final List<Object> cycles = new ArrayList<>();
    private CycleAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WorkoutDetailedBinding.inflate(inflater, container, false);

        App app = (App) requireActivity().getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        cycleViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CycleViewModel.class);

        adapter = new CycleAdapter(cycles);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.addItemDecoration(new GridSpacingItemDecoration(1,20,false, getContext()));

        cycleViewModel.getCycles().observe(getViewLifecycleOwner(), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    // TODO
//                    loading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    cycles.clear();
                    if (r.getData() != null) {
                        cycles.addAll(r.getData());
                        adapter.notifyDataSetChanged();
                    }
                    // TODO
//                    loading.setVisibility(View.GONE);
                    break;
                case ERROR:
                    // TODO
//                    loading.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(binding.getRoot().getContext(),getText(R.string.unexpected_error),Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                    toast.show();
                    break;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
