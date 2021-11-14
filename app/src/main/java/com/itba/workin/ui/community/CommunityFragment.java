package com.itba.workin.ui.community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.itba.workin.App;
import com.itba.workin.backend.models.Credentials;
import com.itba.workin.backend.models.Error;
import com.itba.workin.backend.models.FullRoutine;
import com.itba.workin.databinding.FragmentCommunityBinding;
import com.itba.workin.models.MyRoutine;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.Status;
import com.itba.workin.ui.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class CommunityFragment extends Fragment {

    private final ArrayList<MyRoutine> dataSet = new ArrayList<>();
    private FragmentCommunityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CommunityViewModel communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);

        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecycleViewAdapter adapter = new RecycleViewAdapter(dataSet);

        binding.recyclerview.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        binding.recyclerview.setAdapter(adapter);

        App app = (App) requireActivity().getApplication();
        app.getRoutinesRepository().getRoutines().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                ArrayList<FullRoutine> routineList = (ArrayList<FullRoutine>) r.getData().getContent();
                for (FullRoutine routine : routineList) {
                    dataSet.add(new MyRoutine(routine));
                }
                adapter.setDataSet(dataSet);
            } else {
                defaultResourceHandler(r);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                break;
            case ERROR:
                Error error = resource.getError();
                String message = "Error: " + error.getDescription() + error.getCode();
                Log.d("UI", message);
                break;
        }
    }
}