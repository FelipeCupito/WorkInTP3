package com.itba.workin.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itba.workin.R;
import com.itba.workin.domain.MyRoutine;

import java.util.ArrayList;
import java.util.List;

public abstract class RoutineFragment extends Fragment {

    private final List<MyRoutine> routines = new ArrayList<>();
    protected RoutineViewModel routineViewModel;
    protected RecyclerView recyclerView;
    protected View root;
    private RoutineAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        routines.clear();
        List<MyRoutine> prevRoutines = routineViewModel.getPrevRoutines();
        for (int i = 0; i < RoutineViewModel.PAGE_SIZE && i < prevRoutines.size(); i++) {
            routines.add(prevRoutines.get(i));
        }
        adapter = new RoutineAdapter(routines);

        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!RoutineFragment.this.recyclerView.canScrollVertically(1)) {
                    routineViewModel.getMoreRoutines();
                }
            }
        });
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ProgressBar progressBar = getActivity().findViewById(R.id.loading);
        routineViewModel.getRoutines().observe(getViewLifecycleOwner(), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    routines.clear();
                    if (r.getData() != null) {
                        routines.addAll(r.getData());
                        adapter.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.GONE);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(root.getContext(),getText(R.string.unexpected_error),Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }
}
