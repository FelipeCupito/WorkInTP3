package com.itba.workin.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.databinding.ToolbarMainBinding;
import com.itba.workin.databinding.WorkoutActivityBinding;
import com.itba.workin.repository.RoutinesRepository;
import com.itba.workin.viewmodel.RepositoryViewModelFactory;

public class WorkoutActivity extends AppCompatActivity {

    NavController navController;
    WorkoutActivityBinding workoutActivitybinding;
    CycleViewModel cycleViewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutActivitybinding = WorkoutActivityBinding.inflate(getLayoutInflater());
        View root = workoutActivitybinding.getRoot();
        setContentView(root);

        ToolbarMainBinding toolbarBinding = ToolbarMainBinding.bind(root);
        toolbarBinding.toolbar.inflateMenu(R.menu.app_bar_menu);
        setSupportActionBar(toolbarBinding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_workout);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        App app = (App) getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutinesRepository.class, app.getRoutinesRepository());
        cycleViewModel = new ViewModelProvider(this, viewModelFactory).get(CycleViewModel.class);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id");
        } else {
            Intent intent = getIntent();
            id = intent.getIntExtra("id", -1);
        }
        cycleViewModel.setRoutineId(id);

        workoutActivitybinding.nextButton.setOnClickListener(v -> {
            cycleViewModel.stopTimer();
            cycleViewModel.advanceCurrent();
        });

        workoutActivitybinding.pauseButton.setOnClickListener(v -> {
            if (workoutActivitybinding.pauseButton.getText().equals(getText(R.string.pause))) {
                cycleViewModel.userPauseTimer();
                workoutActivitybinding.pauseButton.setText(R.string.resume);
            } else if (workoutActivitybinding.pauseButton.getText().equals(getText(R.string.resume))) {
                cycleViewModel.userResumeTimer();
                workoutActivitybinding.pauseButton.setText(R.string.pause);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cycleViewModel.resumeTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cycleViewModel.pauseTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",id);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        cycleViewModel.pauseTimer();
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.confirmed_exit_title);
        builder.setMessage(R.string.confirmed_exit_text);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.exit, (dialog, which) -> finish());
        builder.setPositiveButton(R.string.continue_dialog, (dialog, which) -> {
            dialog.dismiss();
            cycleViewModel.resumeTimer();});
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
