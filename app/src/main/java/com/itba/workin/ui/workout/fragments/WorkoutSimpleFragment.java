package com.itba.workin.ui.workout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.WorkoutSimpleBinding;
import com.itba.workin.domain.MyCycle;
import com.itba.workin.domain.MyCycleExercise;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.ScoreActivity;
import com.itba.workin.ui.exerciseDetail.ExerciseDetail;
import com.itba.workin.ui.workout.CycleViewModel;
import com.itba.workin.ui.workout.WorkoutActivity;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSimpleFragment extends Fragment {
    private WorkoutSimpleBinding binding;
    private CycleViewModel cycleViewModel;
    private MyCycle currentCycle;
    private MyCycleExercise currentExercise;
    private ProgressBar loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WorkoutSimpleBinding.inflate(inflater, container, false);

        App app = (App) requireActivity().getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        cycleViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CycleViewModel.class);

        loading = requireActivity().findViewById(R.id.loading);

        cycleViewModel.getCycles().observe(getViewLifecycleOwner(), r -> {
            switch (r.getStatus()) {
                case LOADING:
                    loading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    requireActivity().findViewById(R.id.nav_host_fragment_activity_workout).setVisibility(View.VISIBLE);
                    requireActivity().findViewById(R.id.nextButton).setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    break;
                case ERROR:
                    loading.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(binding.getRoot().getContext(),getText(R.string.unexpected_error),Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                    toast.show();
                    break;
            }
        });

        currentCycle = cycleViewModel.getCurrentCycle();
        if (currentCycle != null) {
            setCycle(currentCycle);
        }
        currentExercise = cycleViewModel.getCurrentExercise();
        if (currentExercise != null) {
            setExercise(currentExercise);
        }

        cycleViewModel.getCurrent().observe(getViewLifecycleOwner(), r -> {
            if (r instanceof MyCycle) {
                currentCycle = (MyCycle) r;
                setCycle(currentCycle);
            } else if (r instanceof MyCycleExercise) {
                currentExercise = (MyCycleExercise) r;
                setExercise(currentExercise);
            } else if (r instanceof CycleViewModel.Finish) {
                Intent intent = new Intent(getContext(), ScoreActivity.class);
                intent.putExtra("id", cycleViewModel.getRoutineId());
                startActivity(intent);
                requireActivity().finish();
            }
        });

        binding.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(binding.getRoot().getContext(), ExerciseDetail.class);
            intent.putExtra("id", currentExercise.getExercise().getId());
            startActivity(intent);
        });

        return binding.getRoot();
    }

    private void setCycle(MyCycle cycle) {
        binding.cycleName.setText(cycle.getName());
        binding.cycleReps.setText(String.format(getString(R.string.cycleRepetitions), cycle.getRepetitions()));
    }

    private void setExercise(MyCycleExercise exercise) {
        Picasso.get().load(exercise.getExercise().getExcerciseUrl()).placeholder(binding.exerciseImg.getDrawable()).resize(300,200).into(binding.exerciseImg);
        binding.exerciseTitle.setText(exercise.getExercise().getName());
        binding.remainingRepetitions.setText(String.valueOf(exercise.getRepetitions()));
        if (cycleViewModel.getExerciseTime() == 0) {
            binding.progressBar.setVisibility(View.GONE);
            binding.textViewTime.setVisibility(View.GONE);
            requireActivity().findViewById(R.id.pauseButton).setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.textViewTime.setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.pauseButton).setVisibility(View.VISIBLE);
        }
        binding.textViewTime.setText(String.format(getString(R.string.num_with_seconds),exercise.getDuration()));
        binding.progressBar.setProgress(exercise.getDuration());
        binding.progressBar.setMax(cycleViewModel.getExerciseTime());
    }

    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem listItem = menu.findItem(R.id.workoutDetailedFragment);
        listItem.setVisible(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
