package com.itba.workin.ui.myRoutines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.itba.workin.databinding.FragmentMyRoutinesBinding;
import com.itba.workin.ui.RecycleViewAdapter;

import java.util.ArrayList;

public class MyRoutinesFragment extends Fragment {

    private FragmentMyRoutinesBinding binding;
    private final ArrayList<RecycleViewAdapter.RoutineWrapper> dataSet = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyRoutinesViewModel myRoutinesViewModel = new ViewModelProvider(this).get(MyRoutinesViewModel.class);

        binding = FragmentMyRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        for (int i = 0; i < 50; i++) {
            dataSet.add(new RecycleViewAdapter.RoutineWrapper(
                    i,"Buenas soy routines " + i, "",0,0));
        }

        RecycleViewAdapter adapter = new RecycleViewAdapter(dataSet);

        binding.recyclerview.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        binding.recyclerview.setAdapter(adapter);


        final TextView textView = binding.textMyRoutines;
        myRoutinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}