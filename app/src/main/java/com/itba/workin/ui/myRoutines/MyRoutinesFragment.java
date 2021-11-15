package com.itba.workin.ui.myRoutines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itba.workin.App;
import com.itba.workin.databinding.FragmentMyRoutinesBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.RoutineAdapter;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MyRoutinesFragment extends Fragment {

    private FragmentMyRoutinesBinding binding;
    private final List<MyRoutine> routines = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        App app = (App) requireActivity().getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        MyRoutinesViewModel myRoutinesViewModel = new ViewModelProvider(requireActivity(),viewModelFactory).get(MyRoutinesViewModel.class);

        binding = FragmentMyRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RoutineAdapter adapter = new RoutineAdapter(routines);

        myRoutinesViewModel.getRoutines().observe(getViewLifecycleOwner(), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    // TODO
                    break;
                case SUCCESS:
                    routines.clear();
                    if (r.getData() != null) {
                        routines.addAll(r.getData());
                        adapter.notifyDataSetChanged();
                        binding.recyclerview.scrollToPosition(routines.size() - 1);
                    }
                    break;
            }
        });

        binding.recyclerview.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!binding.recyclerview.canScrollVertically(1)) {
                    myRoutinesViewModel.getMoreRoutines();
                }
            }
        });
        binding.recyclerview.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}