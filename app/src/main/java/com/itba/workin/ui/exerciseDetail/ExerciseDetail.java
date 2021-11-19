package com.itba.workin.ui.exerciseDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.ExerciseDetailsBinding;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.domain.MyExercise;
import com.itba.workin.domain.MyRoutine;
import com.itba.workin.repository.ExerciseRepository;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.ui.login.LoginActivity;
import com.itba.workin.ui.routineDetail.DetailViewModel;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class ExerciseDetail extends AppCompatActivity {

    private ExerciseDetailsBinding binding;
    private int id;
    private ExerciseViewModel exerciseViewModel;
    private MyExercise exercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ExerciseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(binding.getRoot());
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.loading.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id");
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                id = intent.getIntExtra("id", -1);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        exerciseViewModel.restart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchExercises();
    }

    private void fetchExercises(){
        if (id == -1) {
            binding.loading.setVisibility(View.GONE);
            Toast.makeText(binding.getRoot().getContext(), getText(R.string.unexpected_error), Toast.LENGTH_LONG).show();
        } else {
            App app = (App) getApplication();
            ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(ExerciseRepository.class, app.getExerciseRepository());
            exerciseViewModel = new ViewModelProvider(this, viewModelFactory).get(ExerciseViewModel.class);
            exerciseViewModel.setExerciseId(id);

            exercise = exerciseViewModel.getOldExercise();

            exerciseViewModel.getExercise().observe(this, r -> {
                switch (r.getStatus()) {
                    case SUCCESS:
                        exercise = r.getData();
                        assert exercise != null;
                        setView();
                        break;
                    case ERROR:
                        binding.loading.setVisibility(View.GONE);
                        Toast.makeText(binding.getRoot().getContext(), getText(R.string.unexpected_error), Toast.LENGTH_LONG).show();
                        break;
                }
            });
        }
    }

    private void setView() {
        Picasso.get().load(exercise.getExcerciseUrl()).placeholder(binding.imageView.getDrawable()).resize(300,200).into(binding.imageView);
        binding.exerciseName.setText(exercise.getName());
        binding.descriptionText.setText(exercise.getDescription());

        binding.imageView.setVisibility(View.VISIBLE);
        binding.descriptionText.setVisibility(View.VISIBLE);
        binding.exerciseName.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.GONE);
    }

}
