package com.itba.workin.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.itba.workin.databinding.FragmentFavoriteBinding;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.ui.RoutineAdapter;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    private final ArrayList<MyRoutine> dataSet = new ArrayList<>();
    private FragmentFavoriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoriteViewModel favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        for (int i = 0; i < 50; i++) {
//            dataSet.add(new MyRoutine(
//                    i,"Buenas soy favorito " + i, "",0,0));
        }

        RoutineAdapter adapter = new RoutineAdapter(dataSet);

        binding.recyclerview.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        binding.recyclerview.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}